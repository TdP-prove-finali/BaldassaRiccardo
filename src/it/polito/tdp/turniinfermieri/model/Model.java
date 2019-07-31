package it.polito.tdp.turniinfermieri.model;

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
	private List<DataTurni> soluzione;
	
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
				
				if (infermiere.getTrimestre_ferie_lunghe() == 1 && (f.getData().getMonth() == Month.SEPTEMBER
						|| f.getData().getMonth() == Month.OCTOBER || f.getData().getMonth() == Month.NOVEMBER))
				ferie_lunghe_infermiere.add(f);
				
				else if (infermiere.getTrimestre_ferie_lunghe() == 2 && (f.getData().getMonth() == Month.DECEMBER
						|| f.getData().getMonth() == Month.JANUARY || f.getData().getMonth() == Month.FEBRUARY))
				ferie_lunghe_infermiere.add(f);
				
				else if (infermiere.getTrimestre_ferie_lunghe() == 3 && (f.getData().getMonth() == Month.MARCH
						|| f.getData().getMonth() == Month.APRIL || f.getData().getMonth() == Month.MAY))
				ferie_lunghe_infermiere.add(f);
				
				else if (infermiere.getTrimestre_ferie_lunghe() == 4 && (f.getData().getMonth() == Month.JUNE
						|| f.getData().getMonth() == Month.JULY || f.getData().getMonth() == Month.AUGUST))
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
				
				if (f.getData().getMonth() == Month.SEPTEMBER || f.getData().getMonth() == Month.OCTOBER
						|| f.getData().getMonth() == Month.NOVEMBER)
					cont++;
				
			}
	
				
		}
		
		else if (infermiere.getTrimestre_ferie_lunghe() == 2) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth() == Month.DECEMBER
						|| f.getData().getMonth() == Month.JANUARY || f.getData().getMonth() == Month.FEBRUARY)
					cont++;
				
			}
	
				
		}
		
		else if (infermiere.getTrimestre_ferie_lunghe() == 3) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth() == Month.MARCH
						|| f.getData().getMonth() == Month.APRIL || f.getData().getMonth() == Month.MAY)
					cont++;
				
			}
	
				
		}
		
		else if (infermiere.getTrimestre_ferie_lunghe() == 4) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth() == Month.JUNE
						|| f.getData().getMonth() == Month.JULY || f.getData().getMonth() == Month.AUGUST)
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
				
				if (f.getData().getMonth() == Month.DECEMBER)
					cont1++;
				else if (f.getData().getMonth() == Month.JANUARY)
					cont2++;
				else if (f.getData().getMonth() == Month.FEBRUARY)
					cont3++;
				else if (f.getData().getMonth() == Month.MARCH)
					cont4++;
				else if (f.getData().getMonth() == Month.APRIL)
					cont5++;
				else if (f.getData().getMonth() == Month.MAY)
					cont6++;
				else if (f.getData().getMonth() == Month.JUNE)
					cont7++;
				else if (f.getData().getMonth() == Month.JULY)
					cont8++;
				else if (f.getData().getMonth() == Month.AUGUST)
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		else if(infermiere.getTrimestre_ferie_lunghe() == 2) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth() == Month.SEPTEMBER)
					cont1++;
				else if (f.getData().getMonth() == Month.OCTOBER)
					cont2++;
				else if (f.getData().getMonth() == Month.NOVEMBER)
					cont3++;
				else if (f.getData().getMonth() == Month.MARCH)
					cont4++;
				else if (f.getData().getMonth() == Month.APRIL)
					cont5++;
				else if (f.getData().getMonth() == Month.MAY)
					cont6++;
				else if (f.getData().getMonth() == Month.JUNE)
					cont7++;
				else if (f.getData().getMonth() == Month.JULY)
					cont8++;
				else if (f.getData().getMonth() == Month.AUGUST)
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		else if(infermiere.getTrimestre_ferie_lunghe() == 3) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth() == Month.DECEMBER)
					cont1++;
				else if (f.getData().getMonth() == Month.JANUARY)
					cont2++;
				else if (f.getData().getMonth() == Month.FEBRUARY)
					cont3++;
				else if (f.getData().getMonth() == Month.SEPTEMBER)
					cont4++;
				else if (f.getData().getMonth() == Month.OCTOBER)
					cont5++;
				else if (f.getData().getMonth() == Month.NOVEMBER)
					cont6++;
				else if (f.getData().getMonth() == Month.JUNE)
					cont7++;
				else if (f.getData().getMonth() == Month.JULY)
					cont8++;
				else if (f.getData().getMonth() == Month.AUGUST)
					cont9++;
			}
			
			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2 || cont8 != 2 || cont9 != 2)
				return false;
			
			return true;
		}
		
		else if(infermiere.getTrimestre_ferie_lunghe() == 4) {
			
			for (Ferie f : ferie) {
				
				if (f.getData().getMonth() == Month.DECEMBER)
					cont1++;
				else if (f.getData().getMonth() == Month.JANUARY)
					cont2++;
				else if (f.getData().getMonth() == Month.FEBRUARY)
					cont3++;
				else if (f.getData().getMonth() == Month.MARCH)
					cont4++;
				else if (f.getData().getMonth() == Month.APRIL)
					cont5++;
				else if (f.getData().getMonth() == Month.MAY)
					cont6++;
				else if (f.getData().getMonth() == Month.SEPTEMBER)
					cont7++;
				else if (f.getData().getMonth() == Month.OCTOBER)
					cont8++;
				else if (f.getData().getMonth() == Month.NOVEMBER)
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
		
		if (trimestre_ferie_lunghe != 1 && (value.getMonth() == Month.SEPTEMBER
				|| value.getMonth() == Month.OCTOBER || value.getMonth() == Month.NOVEMBER)) 
			return false;
			
		else if (trimestre_ferie_lunghe != 2 && (value.getMonth() == Month.DECEMBER
				|| value.getMonth() == Month.JANUARY || value.getMonth() == Month.FEBRUARY))
			return false;
		
		else if (trimestre_ferie_lunghe != 3 && (value.getMonth() == Month.MARCH
				|| value.getMonth() == Month.APRIL || value.getMonth() == Month.MAY))
			return false;
		
		else if (trimestre_ferie_lunghe != 4 && (value.getMonth() == Month.JUNE
				|| value.getMonth() == Month.JULY || value.getMonth() == Month.AUGUST))
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
		
		if (trimestre_ferie_lunghe == 1 && (value.getMonth() == Month.SEPTEMBER
				|| value.getMonth() == Month.OCTOBER || value.getMonth() == Month.NOVEMBER)) 
			return false;
			
		else if (trimestre_ferie_lunghe == 2 && (value.getMonth() == Month.DECEMBER
				|| value.getMonth() == Month.JANUARY || value.getMonth() == Month.FEBRUARY))
			return false;
		
		else if (trimestre_ferie_lunghe == 3 && (value.getMonth() == Month.MARCH
				|| value.getMonth() == Month.APRIL || value.getMonth() == Month.MAY))
			return false;
		
		else if (trimestre_ferie_lunghe == 4 && (value.getMonth() == Month.JUNE
				|| value.getMonth() == Month.JULY || value.getMonth() == Month.AUGUST))
			return false;
		
		return true;
	}

	public Map<LocalDate, DataTurni> generaOrario() {

		this.soluzione = new ArrayList<DataTurni>();
		Map<LocalDate, DataTurni> parziale = new TreeMap<LocalDate, DataTurni>();
		
		LocalDate data = LocalDate.of(2019, Month.SEPTEMBER, 1);
		LocalDate fine = LocalDate.of(2020, Month.SEPTEMBER, 1);
		// inizializzazione orario
		
		Map <Infermiere, String> turni = new HashMap<Infermiere,String>();
		
		for (Infermiere i : infermieri)
			turni.put(i, null);
		
		while(data.isBefore(fine)){
			parziale.put(data, new DataTurni(data, new HashMap<Infermiere,String>(turni)));
			data = data.plusDays(1);
		}
		
		Map<Integer, Infermiere> infermieriMap = new HashMap<Integer,Infermiere>();
		
		for (Infermiere i : infermieri)
			infermieriMap.put(i.getId_infermiere(), i);
		
		// inserimento ferie
		for (Ferie f : ferie) {
			DataTurni dt = parziale.get(f.getData());
		
			dt.getTurni().put(infermieriMap.get(f.getId_infermiere()), "Ferie");
			
		}
		
		LocalDate d = LocalDate.of(2019, Month.SEPTEMBER, 1);
	
		calcolaOrario(parziale, d);
		
		return parziale;
		
	}
	// calcola orario attraverso la ricorsione
	/*private void calcolaOrario(Map<LocalDate, DataTurni> parziale, LocalDate data) {
		
		
		//ottengo tutti i candidati
		List<Infermiere> candidati = this.trovaCandidatiMattino(data);
		for(Infermiere candidato : candidati) {
			if(!parziale.contains(candidato)) {
				//è un candidato che non ho ancora considerato
				parziale.add(candidato);
				this.cercaPercorso(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
		
		//vedere se la soluzione corrente è migliore della ottima corrente
		if(parziale.size() > ottima.size()) {
			this.ottima = new LinkedList(parziale);
		}

	}*/

	private List<Infermiere> trovaCandidatiMattino(LocalDate data) {
		// TODO Auto-generated method stub
		return null;
	}
}
