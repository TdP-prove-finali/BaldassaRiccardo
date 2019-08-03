package it.polito.tdp.turniinfermieri.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import it.polito.tdp.turniinfermieri.db.TurniInfermieriDAO;

public class Model {
	
	private TurniInfermieriDAO dao;
	private List<Infermiere> infermieri;
	private List<Ferie> ferie;
	private Map<LocalDate, Map<Infermiere, String>> soluzione;
	private boolean trovata;
	private List<Infermiere> candidatiMattino;
	private List<Infermiere> candidatiPomeriggio;
	private List<Infermiere> candidatiNotte;

	
	public Model() {
		dao = new TurniInfermieriDAO();
		infermieri = dao.getInfermieri();
		ferie = dao.getFerie();
	}

	public List<Infermiere> getInfermieri() {
		return infermieri;
	}
	// funzione che trova i giorni di ferie di un infermiere nel suo trimestre di ferie lunghe
	public List<Ferie> getFerieLungheInfermiere(Infermiere infermiere){
		
		List<Ferie> ferie_lunghe_infermiere = new ArrayList<Ferie>();
		
		for (Ferie f : ferie) {
			if (f.getId_infermiere() == infermiere.getId_infermiere()) {
				
				if (infermiere.getTrimestre_ferie_lunghe() == 1 && (f.getData().getMonth().equals(Month.SEPTEMBER)
						|| f.getData().getMonth().equals(Month.OCTOBER) || f.getData().getMonth().equals(Month.NOVEMBER)))
				ferie_lunghe_infermiere.add(f);
				
				else if (infermiere.getTrimestre_ferie_lunghe() == 2 && (f.getData().getMonth().equals(Month.DECEMBER)
						|| f.getData().getMonth().equals(Month.JANUARY) || f.getData().getMonth().equals(Month.FEBRUARY)))
				ferie_lunghe_infermiere.add(f);
				
				else if (infermiere.getTrimestre_ferie_lunghe() == 3 && (f.getData().getMonth().equals(Month.MARCH)
						|| f.getData().getMonth().equals(Month.APRIL) || f.getData().getMonth().equals(Month.MAY)))
				ferie_lunghe_infermiere.add(f);
				
				else if (infermiere.getTrimestre_ferie_lunghe() == 4 && (f.getData().getMonth().equals(Month.JUNE)
						|| f.getData().getMonth().equals(Month.JULY) || f.getData().getMonth().equals(Month.AUGUST)))
				ferie_lunghe_infermiere.add(f);
			}
		}
		return ferie_lunghe_infermiere;
	}
	// funzione che trova i giorni di ferie di un infermiere al di fuori del suo trimestre di ferie lughe
	public List<Ferie> getFerieBreviInfermiere(Infermiere infermiere){
		
		List<Ferie> ferie_brevi_infermiere = new ArrayList<Ferie>();
		List<Ferie> ferie_lunghe_infermiere = this.getFerieLungheInfermiere(infermiere);

		for (Ferie f : ferie) {
			if (f.getId_infermiere() == infermiere.getId_infermiere()) {
				ferie_brevi_infermiere.add(f);
			}
			
		}
		
		ferie_brevi_infermiere.removeAll(ferie_lunghe_infermiere);
		
		return ferie_brevi_infermiere;
	}
	
	public boolean trimestriAccettabili(List<Infermiere> infermieri) {

		int cont_1 = 0;
		int cont_2 = 0;
		int cont_3 = 0;
		int cont_4 = 0;
		
		for (Infermiere i : infermieri) {
			if (i.getTrimestre_ferie_lunghe() == 1)
				cont_1++;
			else if (i.getTrimestre_ferie_lunghe() == 2)
				cont_2++;
			else if (i.getTrimestre_ferie_lunghe() == 3)
				cont_3++;
			else if (i.getTrimestre_ferie_lunghe() == 4)
				cont_4++;
			else
				return false;
		}

		if (cont_1 == 3 && cont_2 == 2 && cont_3 == 3 && cont_4 == 3)
			return true;
		
		return false;
	}

	public void modificaTrimestri() {

		for (int i = 0; i <infermieri.size(); i++) {
				dao.modificaInfermiere(infermieri.get(i));
		}
	}
	// controllo se la modifica delle ferie nel periodo di vacanze lungo sono accettabili
	public boolean ferieLungheAccettabili(Infermiere infermiere) {
		
		List<Ferie> ferie = this.getFerieLungheInfermiere(infermiere);
		int cont = 0;
		
		Set<LocalDate> setDate= new HashSet<LocalDate>();
		
		for (Ferie f : ferie) {
			setDate.add(f.getData());
		}
		
		if (setDate.size() != ferie.size())
			return false;

			
		if (infermiere.getTrimestre_ferie_lunghe() == 1) {
				
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.SEPTEMBER) || f.getData().getMonth().equals(Month.OCTOBER)
						|| f.getData().getMonth().equals(Month.NOVEMBER))
					cont++;
				
			}
	
				
		}
		
		else if (infermiere.getTrimestre_ferie_lunghe() == 2) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.DECEMBER)
						|| f.getData().getMonth().equals(Month.JANUARY) || f.getData().getMonth().equals(Month.FEBRUARY))
					cont++;
				
			}
	
				
		}
		
		else if (infermiere.getTrimestre_ferie_lunghe() == 3) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.MARCH)
						|| f.getData().getMonth().equals(Month.APRIL) || f.getData().getMonth().equals(Month.MAY))
					cont++;
				
			}
	
				
		}
		
		else if (infermiere.getTrimestre_ferie_lunghe() == 4) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.JUNE)
						|| f.getData().getMonth().equals(Month.JULY) || f.getData().getMonth().equals(Month.AUGUST))
					cont++;
				
			}
	
				
		}
		
		if (cont == 14)
			return true;
		
		
		return false;
	}
	// controllo se le modifiche delle vacanze nei mesi di vacanza brevi siano accettabili
	public boolean ferieBreviAccettabili(Infermiere infermiere) {
		
		List<Ferie> ferie = this.getFerieBreviInfermiere(infermiere);
		int cont1 = 0;
		int cont2 = 0;
		int cont3 = 0;
		int cont4 = 0;
		int cont5 = 0;
		int cont6 = 0;
		int cont7 = 0;
		int cont8 = 0;
		int cont9 = 0;
		
		Set<LocalDate> setDate= new HashSet<LocalDate>();
		
		for (Ferie f : ferie) {
			setDate.add(f.getData());
		}
		
		if (setDate.size() != ferie.size())
			return false;

		if(infermiere.getTrimestre_ferie_lunghe() == 1) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.DECEMBER))
					cont1++;
				else if (f.getData().getMonth().equals(Month.JANUARY))
					cont2++;
				else if (f.getData().getMonth().equals(Month.FEBRUARY))
					cont3++;
				else if (f.getData().getMonth().equals(Month.MARCH))
					cont4++;
				else if (f.getData().getMonth().equals(Month.APRIL))
					cont5++;
				else if (f.getData().getMonth().equals(Month.MAY))
					cont6++;
				else if (f.getData().getMonth().equals(Month.JUNE))
					cont7++;
				else if (f.getData().getMonth().equals(Month.JULY))
					cont8++;
				else if (f.getData().getMonth().equals(Month.AUGUST))
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		else if(infermiere.getTrimestre_ferie_lunghe() == 2) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.SEPTEMBER))
					cont1++;
				else if (f.getData().getMonth().equals(Month.OCTOBER))
					cont2++;
				else if (f.getData().getMonth().equals(Month.NOVEMBER))
					cont3++;
				else if (f.getData().getMonth().equals(Month.MARCH))
					cont4++;
				else if (f.getData().getMonth().equals(Month.APRIL))
					cont5++;
				else if (f.getData().getMonth().equals(Month.MAY))
					cont6++;
				else if (f.getData().getMonth().equals(Month.JUNE))
					cont7++;
				else if (f.getData().getMonth().equals(Month.JULY))
					cont8++;
				else if (f.getData().getMonth().equals(Month.AUGUST))
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		else if(infermiere.getTrimestre_ferie_lunghe() == 3) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.DECEMBER))
					cont1++;
				else if (f.getData().getMonth().equals(Month.JANUARY))
					cont2++;
				else if (f.getData().getMonth().equals(Month.FEBRUARY))
					cont3++;
				else if (f.getData().getMonth().equals(Month.SEPTEMBER))
					cont4++;
				else if (f.getData().getMonth().equals(Month.OCTOBER))
					cont5++;
				else if (f.getData().getMonth().equals(Month.NOVEMBER))
					cont6++;
				else if (f.getData().getMonth().equals(Month.JUNE))
					cont7++;
				else if (f.getData().getMonth().equals(Month.JULY))
					cont8++;
				else if (f.getData().getMonth().equals(Month.AUGUST))
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		else if(infermiere.getTrimestre_ferie_lunghe() == 4) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth().equals(Month.DECEMBER))
					cont1++;
				else if (f.getData().getMonth().equals(Month.JANUARY))
					cont2++;
				else if (f.getData().getMonth().equals(Month.FEBRUARY))
					cont3++;
				else if (f.getData().getMonth().equals(Month.MARCH))
					cont4++;
				else if (f.getData().getMonth().equals(Month.APRIL))
					cont5++;
				else if (f.getData().getMonth().equals(Month.MAY))
					cont6++;
				else if (f.getData().getMonth().equals(Month.SEPTEMBER))
					cont7++;
				else if (f.getData().getMonth().equals(Month.OCTOBER))
					cont8++;
				else if (f.getData().getMonth().equals(Month.NOVEMBER))
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		
		
		return false;
	}

	public void modificaFerie(Infermiere infermiere) {
		
		List<Ferie> ferie = new ArrayList<Ferie>();
		ferie.addAll(this.getFerieBreviInfermiere(infermiere));
		ferie.addAll(this.getFerieLungheInfermiere(infermiere));

		for (int i = 0; i < ferie.size(); i++) {
			dao.modificaFerieInfermiere(ferie.get(i));
		}
		
		
	}
	// controllo se la nuova data inserita nelle ferie lunghe sia valida
	public boolean controllaFerieLunghe(LocalDate value, int trimestre_ferie_lunghe) {
		
		LocalDate inizio = LocalDate.of(2019, Month.AUGUST, 31);
		LocalDate fine = LocalDate.of(2020, Month.SEPTEMBER, 1);


		if (value == null)
			return false;
		
		if (!value.isAfter(inizio) || !value.isBefore(fine))
			return false;
		
		if (trimestre_ferie_lunghe != 1 && (value.getMonth().equals(Month.SEPTEMBER)
				|| value.getMonth().equals(Month.OCTOBER) || value.getMonth().equals(Month.NOVEMBER))) 
			return false;
			
		else if (trimestre_ferie_lunghe != 2 && (value.getMonth().equals(Month.DECEMBER)
				|| value.getMonth().equals(Month.JANUARY) || value.getMonth().equals(Month.FEBRUARY)))
			return false;
		
		else if (trimestre_ferie_lunghe != 3 && (value.getMonth().equals(Month.MARCH)
				|| value.getMonth().equals(Month.APRIL) || value.getMonth().equals(Month.MAY)))
			return false;
		
		else if (trimestre_ferie_lunghe != 4 && (value.getMonth().equals(Month.JUNE)
				|| value.getMonth().equals(Month.JULY) || value.getMonth().equals(Month.AUGUST)))
			return false;
		
		return true;
	}

	public boolean controllaFerieBrevi(LocalDate value, int trimestre_ferie_lunghe) {
		
		LocalDate inizio = LocalDate.of(2019, Month.AUGUST, 31);
		LocalDate fine = LocalDate.of(2020, Month.SEPTEMBER, 1);


		if (value == null)
			return false;
		
		if (!value.isAfter(inizio) || !value.isBefore(fine))
			return false;
		
		if (trimestre_ferie_lunghe == 1 && (value.getMonth().equals(Month.SEPTEMBER)
				|| value.getMonth().equals(Month.OCTOBER) || value.getMonth().equals(Month.NOVEMBER)))
			return false;
			
		else if (trimestre_ferie_lunghe == 2 && (value.getMonth().equals(Month.DECEMBER)
				|| value.getMonth().equals(Month.JANUARY) || value.getMonth().equals(Month.FEBRUARY)))
			return false;
		
		else if (trimestre_ferie_lunghe == 3 && (value.getMonth().equals(Month.MARCH)
				|| value.getMonth().equals(Month.APRIL) || value.getMonth().equals(Month.MAY)))
			return false;
		
		else if (trimestre_ferie_lunghe == 4 && (value.getMonth().equals(Month.JUNE)
				|| value.getMonth().equals(Month.JULY) || value.getMonth().equals(Month.AUGUST)))
			return false;
		
		return true;
	}

	public Map<LocalDate, Map<Infermiere, String>> generaOrario() {

		this.soluzione = new TreeMap<LocalDate, Map<Infermiere, String>>();

		this.trovata = false;
		Map<LocalDate, Map<Infermiere, String>> parziale = new TreeMap<LocalDate, Map<Infermiere, String>>();
		
		LocalDate data = LocalDate.of(2019, Month.SEPTEMBER, 1);
		LocalDate fine = LocalDate.of(2020, Month.SEPTEMBER, 1);
		// inizializzazione orario
		
		Map <Infermiere, String> turni = new HashMap<Infermiere,String>();
		
		for (Infermiere i : infermieri)
			turni.put(i, null);
		
		while(data.isBefore(fine)){
			parziale.put(data, new HashMap<Infermiere,String>(turni));
			this.soluzione.put(data, new HashMap<Infermiere,String>());
			data = data.plusDays(1);
		}
		
		Map<Integer, Infermiere> infermieriMap = new HashMap<Integer,Infermiere>();
		
		for (Infermiere i : infermieri)
			infermieriMap.put(i.getId_infermiere(), i);
		
		// inserimento ferie
		for (Ferie f : ferie) {
			
			Map<Infermiere, String> map = parziale.get(f.getData());
			map.put(infermieriMap.get(f.getId_infermiere()), "Ferie");
			
		}
		
		
		Map<LocalDate, List<Infermiere>> infermieriMattino = new HashMap<LocalDate, List<Infermiere>>(); 
		Map<LocalDate, List<Infermiere>> infermieriPomeriggio = new HashMap<LocalDate, List<Infermiere>>(); 
		Map<LocalDate, List<Infermiere>> infermieriNotte = new HashMap<LocalDate, List<Infermiere>>(); 
		
		data = LocalDate.of(2019, Month.SEPTEMBER, 1);
		
		while(data.isBefore(fine)){
			infermieriMattino.put(data, new ArrayList<Infermiere>());
			infermieriPomeriggio.put(data, new ArrayList<Infermiere>());
			infermieriNotte.put(data, new ArrayList<Infermiere>());

			data = data.plusDays(1);
		}

		data = LocalDate.of(2019, Month.SEPTEMBER, 1);

	
		calcolaOrario(parziale, data, fine, infermieriMattino, infermieriPomeriggio, infermieriNotte);
		//System.out.println(soluzione);
		
		return this.soluzione;
		
	}
	// calcola orario attraverso la ricorsione
	private void calcolaOrario(Map<LocalDate, Map<Infermiere, String>> parziale, LocalDate data, LocalDate fine,
			Map<LocalDate, List<Infermiere>> infermieriMattino, Map<LocalDate, List<Infermiere>> infermieriPomeriggio, Map<LocalDate, List<Infermiere>> infermieriNotte) {
			//System.out.println(trovata);

		if (trovata)
			return;
		
		//vedere se ho completato tutto l'anno		
  		if(data.isEqual(fine)) {
				System.out.println(data);
  			if (!trovata) {
  				trovata = true;
  				LocalDate d = LocalDate.of(2019, Month.SEPTEMBER, 1);
  				
  				while (d.isBefore(fine)) {
  					//this.soluzione.get(d).put(parziale.get(d).keySet(), new HashMap<Infermiere,String>(parziale.get(d).values()));
  					d = d.plusDays(1);
  				}
  			}
			return;
		}
  		
  		else {

		
			if (infermieriMattino.get(data).size() == 3 && infermieriPomeriggio.get(data).size() == 2 && infermieriNotte.get(data).size() == 1) {
				for (Infermiere i : infermieri) {
					if (parziale.get(data).get(i) == null)
						parziale.get(data).put(i, "Riposo");
				}
	
				//System.out.println(parziale);
				
				
		//		if (data.getDayOfMonth() == data.lengthOfMonth()) {
			//		System.out.println("3");
				//	if (!contaRiposi(data, parziale)) 
					//	return;
				//}
						
					if (data.isBefore(fine)) {
						//System.out.println(data);
						//System.out.println(parziale.get(data));
						this.calcolaOrario(parziale, data.plusDays(1), fine, infermieriMattino, infermieriPomeriggio, infermieriNotte);
						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i).equals("Riposo"))
								parziale.get(data).put(i, null);
						}
					}
					
					
				
				
				return;
			}
			
			else {
						
				candidatiMattino = this.trovaCandidatiMattino(data, parziale);
				
				if ((infermieriMattino.size() + candidatiMattino.size()) < 3)
					return;
		
				if (infermieriMattino.get(data).size() < 3) {
					for(Infermiere candidato : candidatiMattino) {
						if(parziale.get(data).get(candidato) == null && !infermieriMattino.get(data).contains(candidato)) {
							// candidato che non ho ancora considerato
							parziale.get(data).put(candidato, "Mattino");
							infermieriMattino.get(data).add(candidato);
							this.calcolaOrario(parziale, data, fine, infermieriMattino, infermieriPomeriggio, infermieriNotte);
								parziale.get(data).put(candidato, null);
								infermieriMattino.get(data).remove(infermieriMattino.get(data).size()-1);	
							
						}
					}
				
				}
				
				candidatiPomeriggio = this.trovaCandidatiPomeriggio(data, parziale);
				
				if ((infermieriPomeriggio.size() + candidatiPomeriggio.size()) < 2)
					return;
				
				if (infermieriPomeriggio.get(data).size() < 2) {
					for(Infermiere candidato : candidatiPomeriggio) {
						if(parziale.get(data).get(candidato) == null) {
							// candidato che non ho ancora considerato
							parziale.get(data).put(candidato, "Pomeriggio");
							infermieriPomeriggio.get(data).add(candidato);
							this.calcolaOrario(parziale, data, fine, infermieriMattino, infermieriPomeriggio, infermieriNotte);
								parziale.get(data).put(candidato, null);
								infermieriPomeriggio.get(data).remove(infermieriPomeriggio.get(data).size()-1);	
							
						}
					}
				
				}
				
				candidatiNotte = this.trovaCandidatiNotte(data, parziale);
		
				if ((infermieriNotte.size() + candidatiNotte.size()) < 1)
					return;
				
				if (infermieriNotte.get(data).size() < 1) {
					for(Infermiere candidato : candidatiNotte) {
						if(parziale.get(data).get(candidato) == null) {
							// candidato che non ho ancora considerato
							parziale.get(data).put(candidato, "Notte");
							infermieriNotte.get(data).add(candidato);
							this.calcolaOrario(parziale, data, fine, infermieriMattino, infermieriPomeriggio, infermieriNotte);
								parziale.get(data).put(candidato, null);
								infermieriNotte.get(data).remove(infermieriNotte.get(data).size()-1);	
							
						}
					}
				
				}
			
			}
			
  		}

	}

	private List<Infermiere> trovaCandidatiNotte(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {
		
		List<Infermiere> inf = new ArrayList<Infermiere>();

		if (data.equals(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					inf.add(i);
			}
		}
		else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null) 
					inf.add(i);
			}
		}
		else {
			int cont;
			
			
			for (Infermiere i : infermieri) {
				cont = 0;
				
				for (int j = 6; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo") && !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont ++;
				}
				
				if (cont < 6 && parziale.get(data).get(i) == null) 
					inf.add(i);
			}
		}
		return inf;
	}

	private List<Infermiere> trovaCandidatiPomeriggio(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {
		
		List<Infermiere> inf = new ArrayList<Infermiere>();

		if (data.equals(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					inf.add(i);
			}
		}
		else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte")) 
					inf.add(i);
			}
		}
		else {
			int cont;
			
			
			for (Infermiere i : infermieri) {
				cont = 0;
				
				for (int j = 6; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo") && !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont ++;
				}
				
				if (cont < 6 && parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte")) 
					inf.add(i);
			}
		}
		return inf;
	}

	private List<Infermiere> trovaCandidatiMattino(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {
		
		List<Infermiere> inf = new ArrayList<Infermiere>();

		if (data.equals(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					inf.add(i);
			}
		}
		else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte") && !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio")) 
					inf.add(i);
			}
		}
		else {
			int cont;
			
			
			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = 6; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo") && !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont ++;
				}
				
				if (cont < 6 && parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte") && !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio")) 
					inf.add(i);
			}
		}
		return inf;
	}
	// conteggio dei riposi in base alle festivitÓ mensili
	public boolean contaRiposi(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {
				
		int cont = 0;
		int riposi;
		
		for (int i = 1; i <= data.lengthOfMonth(); i++) {
			if (LocalDate.of(data.getYear(), data.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.of(data.getYear(), data.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SUNDAY))
				cont++;
		}
		
		// capodanno ed epifania
		if (data.getMonth().equals(Month.JANUARY))
			cont = cont + 2;
		// pasqua, pasquetta e festa della liberazione (25 aprile)
		else if (data.getMonth().equals(Month.APRIL))
			cont = cont + 3;
		// festa del lavoro (1 maggio)
		else if (data.getMonth().equals(Month.MAY))
			cont = cont + 1;
		// festa della repubblica (2 giugno)
		else if (data.getMonth().equals(Month.JUNE))
			cont = cont + 1;
		// ferragosto (15 agosto)
		else if (data.getMonth().equals(Month.AUGUST))
			cont = cont + 1;
		// tutti i santi (1 novembre)
		else if (data.getMonth().equals(Month.NOVEMBER))
			cont = cont + 1;
		// immacolata concezione (8 dicembre), natale (25 dicembre) e santo stefano (26 dicembre)
		else if (data.getMonth().equals(Month.DECEMBER))
			cont = cont + 3;
		
		
		for (Infermiere inf : infermieri) {
			riposi = 0;
			for (int i = 1; i <= data.lengthOfMonth(); i++) {
				if (parziale.get(LocalDate.of(data.getYear(), data.getMonth(), i)).get(inf).equals("Riposo"))
					riposi++;
			}
			if (riposi != cont)
				return false;
			
		}
			
		
		return true;
	}
}
