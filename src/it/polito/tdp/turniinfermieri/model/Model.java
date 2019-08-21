package it.polito.tdp.turniinfermieri.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
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

	private LocalDate inizio;
	private LocalDate fine;
	private List<Infermiere> infMat;
	private List<Infermiere> infPom;
	private List<Infermiere> infNot;
	private Map<LocalDate, List<Infermiere>> infermieriMattino;
	private Map<LocalDate, List<Infermiere>> infermieriPomeriggio;
	private Map<LocalDate, List<Infermiere>> infermieriNotte;
	private List<StatisticheInfermiere> stat;

	public Model() {
		dao = new TurniInfermieriDAO();
		infermieri = dao.getInfermieri();
		ferie = dao.getFerie();
		inizio = LocalDate.of(2019, Month.SEPTEMBER, 1);
		fine = LocalDate.of(2020, Month.SEPTEMBER, 1);
		infMat = new ArrayList<Infermiere>();
		infPom = new ArrayList<Infermiere>();
		infNot = new ArrayList<Infermiere>();
		infermieriMattino = new HashMap<LocalDate, List<Infermiere>>();
		infermieriPomeriggio = new HashMap<LocalDate, List<Infermiere>>();
		infermieriNotte = new HashMap<LocalDate, List<Infermiere>>();
		stat = new ArrayList<StatisticheInfermiere>();
	}

	public List<Infermiere> getInfermieri() {
		return infermieri;
	}

	// funzione che trova i giorni di ferie di un infermiere nel suo trimestre di
	// ferie lunghe
	public List<Ferie> getFerieLungheInfermiere(Infermiere infermiere) {

		List<Ferie> ferie_lunghe_infermiere = new ArrayList<Ferie>();

		for (Ferie f : ferie) {
			if (f.getId_infermiere() == infermiere.getId_infermiere()) {

				if (infermiere.getTrimestre_ferie_lunghe() == 1 && (f.getData().getMonth().equals(Month.SEPTEMBER)
						|| f.getData().getMonth().equals(Month.OCTOBER)
						|| f.getData().getMonth().equals(Month.NOVEMBER)))
					ferie_lunghe_infermiere.add(f);

				else if (infermiere.getTrimestre_ferie_lunghe() == 2 && (f.getData().getMonth().equals(Month.DECEMBER)
						|| f.getData().getMonth().equals(Month.JANUARY)
						|| f.getData().getMonth().equals(Month.FEBRUARY)))
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

	// funzione che trova i giorni di ferie di un infermiere al di fuori del suo
	// trimestre di ferie lughe
	public List<Ferie> getFerieBreviInfermiere(Infermiere infermiere) {

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

		for (int i = 0; i < infermieri.size(); i++) {
			dao.modificaInfermiere(infermieri.get(i));
		}
	}

	// controllo se la modifica delle ferie nel periodo di vacanze lungo sono
	// accettabili
	public boolean ferieLungheAccettabili(Infermiere infermiere) {

		List<Ferie> ferie = this.getFerieLungheInfermiere(infermiere);
		int cont = 0;

		Set<LocalDate> setDate = new HashSet<LocalDate>();

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

				if (f.getData().getMonth().equals(Month.DECEMBER) || f.getData().getMonth().equals(Month.JANUARY)
						|| f.getData().getMonth().equals(Month.FEBRUARY))
					cont++;

			}

		}

		else if (infermiere.getTrimestre_ferie_lunghe() == 3) {

			for (Ferie f : ferie) {

				if (f.getData().getMonth().equals(Month.MARCH) || f.getData().getMonth().equals(Month.APRIL)
						|| f.getData().getMonth().equals(Month.MAY))
					cont++;

			}

		}

		else if (infermiere.getTrimestre_ferie_lunghe() == 4) {

			for (Ferie f : ferie) {

				if (f.getData().getMonth().equals(Month.JUNE) || f.getData().getMonth().equals(Month.JULY)
						|| f.getData().getMonth().equals(Month.AUGUST))
					cont++;

			}

		}

		if (cont == 14)
			return true;

		return false;
	}

	// controllo se le modifiche delle vacanze nei mesi di vacanza brevi siano
	// accettabili
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

		Set<LocalDate> setDate = new HashSet<LocalDate>();

		for (Ferie f : ferie) {
			setDate.add(f.getData());
		}

		if (setDate.size() != ferie.size())
			return false;

		if (infermiere.getTrimestre_ferie_lunghe() == 1) {

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

			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2
					|| cont8 != 2 || cont9 != 2)
				return false;

			return true;
		}

		else if (infermiere.getTrimestre_ferie_lunghe() == 2) {

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

			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2
					|| cont8 != 2 || cont9 != 2)
				return false;

			return true;
		}

		else if (infermiere.getTrimestre_ferie_lunghe() == 3) {

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

			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2
					|| cont8 != 2 || cont9 != 2)
				return false;

			return true;
		}

		else if (infermiere.getTrimestre_ferie_lunghe() == 4) {

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

			if (cont1 != 2 || cont2 != 2 || cont3 != 2 || cont4 != 2 || cont5 != 2 || cont6 != 2 || cont7 != 2
					|| cont8 != 2 || cont9 != 2)
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
		// inizializzazione orario

		Map<Infermiere, String> turni = new HashMap<Infermiere, String>();

		for (Infermiere i : infermieri)
			turni.put(i, null);

		while (data.isBefore(fine)) {
			parziale.put(data, new HashMap<Infermiere, String>(turni));
			this.soluzione.put(data, new HashMap<Infermiere, String>());
			data = data.plusDays(1);
		}

		Map<Integer, Infermiere> infermieriMap = new HashMap<Integer, Infermiere>();

		for (Infermiere i : infermieri)
			infermieriMap.put(i.getId_infermiere(), i);

		// inserimento ferie
		for (Ferie f : ferie) {

			Map<Infermiere, String> map = parziale.get(f.getData());
			map.put(infermieriMap.get(f.getId_infermiere()), "Ferie");

		}

		data = LocalDate.of(2019, Month.SEPTEMBER, 1);

		while (data.isBefore(fine)) {
			infermieriMattino.put(data, new ArrayList<Infermiere>());
			infermieriPomeriggio.put(data, new ArrayList<Infermiere>());
			infermieriNotte.put(data, new ArrayList<Infermiere>());

			data = data.plusDays(1);
		}

		calcolaOrario(parziale, inizio);
		// System.out.println(soluzione);

		return this.soluzione;

	}

	// calcola orario attraverso la ricorsione
	private void calcolaOrario(Map<LocalDate, Map<Infermiere, String>> parziale, LocalDate data) {
		// System.out.println(data);

	//	System.out.println(data);
		
		if (trovata)
			return;
		
		

		if (data.isBefore(fine)) {
			
			List<Infermiere> candidatiMattino = this.trovaCandidatiMattino(data, parziale);
			Collections.sort(candidatiMattino);
			
			List<List<Infermiere>> combMattino = this.subsets(candidatiMattino, 3);

			if (combMattino.size() == 0)
				return;

			for (List<Infermiere> infMat : combMattino) {

				for (Infermiere i : infMat) {
					parziale.get(data).put(i, "Mattino");
					i.setNumero_mattine(i.getNumero_mattine() + 1);
				}

				List<Infermiere> candidatiPomeriggio = this.trovaCandidatiPomeriggio(data, parziale);
				Collections.sort(candidatiPomeriggio);
				
				List<List<Infermiere>> combPomeriggio = this.subsets(candidatiPomeriggio, 2);

				if (combPomeriggio.size() == 0)
					return;

				for (List<Infermiere> infPom : combPomeriggio) {

					for (Infermiere i : infPom) {
						parziale.get(data).put(i, "Pomeriggio");
						i.setNumero_pomeriggi(i.getNumero_pomeriggi() + 1);
					}

					List<Infermiere> candidatiNotte = this.trovaCandidatiNotte(data, parziale);
					Collections.sort(candidatiNotte);
					
					List<List<Infermiere>> combNotte = this.subsets(candidatiNotte, 1);

					if (combNotte.size() == 0)
						return;

					for (List<Infermiere> infNot : combNotte) {

						for (Infermiere i : infNot) {
							parziale.get(data).put(i, "Notte");
							i.setNumero_notti(i.getNumero_notti() + 1);
						}

						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == null) {
								parziale.get(data).put(i, "Riposo");
								i.setNumero_riposi(i.getNumero_riposi() + 1);
							}
						}
						
			//			if (data.getDayOfMonth() == data.lengthOfMonth()) {
				//			if (contaRiposi(data, parziale)) {
					//			System.out.println(contaRiposi(data, parziale));
					//			this.calcolaOrario(parziale, data.plusDays(1), fine);
				//			}
			//			}

				//		else
							this.calcolaOrario(parziale, data.plusDays(1));

						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == "Riposo") {
								parziale.get(data).put(i, null);
								i.setNumero_riposi(i.getNumero_riposi() - 1);
							}
						}

						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == "Notte") {
								parziale.get(data).put(i, null);
								i.setNumero_notti(i.getNumero_notti() - 1);
							}
						}

					}

					for (Infermiere i : infermieri) {
						if (parziale.get(data).get(i) == "Pomeriggio") {
							parziale.get(data).put(i, null);
							i.setNumero_pomeriggi(i.getNumero_pomeriggi() - 1);
						}
					}

				}

				for (Infermiere i : infermieri) {
					if (parziale.get(data).get(i) == "Mattino") {
						parziale.get(data).put(i, null);
						i.setNumero_mattine(i.getNumero_mattine() - 1);
					}
				}

			}

		}

		// vedere se ho completato tutto l'anno
		else if (data.isEqual(fine)) {
			// System.out.println(data);
			trovata = true;
			// this.soluzione = new HashMap<LocalDate, Map<Infermiere,String>>(parziale);
			LocalDate d = LocalDate.of(2019, Month.SEPTEMBER, 1);

			while (d.isBefore(fine)) {
				this.soluzione.put(d, new HashMap<Infermiere, String>(parziale.get(d)));
				d = d.plusDays(1);
			}
			
			for (Infermiere i : infermieri)
				stat.add(new StatisticheInfermiere(i, i.getNumero_riposi(), i.getNumero_mattine(), i.getNumero_pomeriggi(), i.getNumero_notti()));

			return;
		}

	}

	private List<Infermiere> trovaCandidatiMattino(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		infMat.clear();

		if (data.equals(this.inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infMat.add(i);
			}
		} else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte")
						&& !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio"))
					infMat.add(i);
			}
		} else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = 6; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont++;
				}

				if (cont < 6 && parziale.get(data).get(i) == null
						&& !parziale.get(data.minusDays(1)).get(i).equals("Notte")
						&& !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio"))
					infMat.add(i);
			}
		}
		return infMat;
	}

	private List<Infermiere> trovaCandidatiPomeriggio(LocalDate data,
			Map<LocalDate, Map<Infermiere, String>> parziale) {

		infPom.clear();

		if (data.equals(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infPom.add(i);
			}
		} else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte"))
					infPom.add(i);
			}
		} else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = 6; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont++;
				}

				if (cont < 6 && parziale.get(data).get(i) == null
						&& !parziale.get(data.minusDays(1)).get(i).equals("Notte"))
					infPom.add(i);
			}
		}
		return infPom;
	}

	private List<Infermiere> trovaCandidatiNotte(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		infNot.clear();
		if (data.equals(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infNot.add(i);
			}
		} else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(LocalDate.of(2019, Month.SEPTEMBER, 1))) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infNot.add(i);
			}
		} else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = 6; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont++;
				}

				if (cont < 6 && parziale.get(data).get(i) == null)
					infNot.add(i);
			}
		}
		return infNot;
	}

	// conteggio dei riposi in base alle festività mensili
	public boolean contaRiposi(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		int cont = 0;
		int riposi;

		for (int i = 1; i <= data.lengthOfMonth(); i++) {
			if (LocalDate.of(data.getYear(), data.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SATURDAY)
					|| LocalDate.of(data.getYear(), data.getMonth(), i).getDayOfWeek().equals(DayOfWeek.SUNDAY))
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
		// immacolata concezione (8 dicembre), natale (25 dicembre) e santo stefano (26
		// dicembre)
		else if (data.getMonth().equals(Month.DECEMBER))
			cont = cont + 3;

		for (Infermiere inf : infermieri) {
			riposi = 0;
			for (int i = 1; i <= data.lengthOfMonth(); i++) {
				if (parziale.get(LocalDate.of(data.getYear(), data.getMonth(), i)).get(inf).equals("Riposo"))
					riposi++;
			}
			if (riposi > cont + 4 || riposi < cont - 1)
				return false;

		}

		return true;
	}

	List<List<Infermiere>> subsets(List<Infermiere> input, int k) {

		// int[] input = {10, 20, 30, 40, 50}; // input array
		// int k = 3; // sequence length

		List<List<Infermiere>> subsets = new ArrayList<>();

		int[] s = new int[k]; // here we'll keep indices
								// pointing to elements in input array

		if (k <= input.size()) {
			// first index sequence: 0, 1, 2, ...
			for (int i = 0; (s[i] = i) < k - 1; i++)
				;
			subsets.add(getSubset(input, s));
			for (;;) {
				int i;
				// find position of item that can be incremented
				for (i = k - 1; i >= 0 && s[i] == input.size() - k + i; i--)
					;
				if (i < 0) {
					break;
				}
				s[i]++; // increment this item
				for (++i; i < k; i++) { // fill up remaining items
					s[i] = s[i - 1] + 1;
				}
				subsets.add(getSubset(input, s));
			}
		}
		return subsets;
	}

	// generate actual subset by index sequence
	List<Infermiere> getSubset(List<Infermiere> input, int[] subset) {
		List<Infermiere> result = new ArrayList<Infermiere>();
		for (int i = 0; i < subset.length; i++)
			result.add(input.get(subset[i]));
		return result;
	}

	public List<StatisticheInfermiere> getStat() {
		return stat;
	}
	
	public InfermiereTurni turniInfermiere(Infermiere infermiere, Month mese) {
		
		int anno;
		List<String> turni = new ArrayList<String>();
		
		if (mese.equals(Month.SEPTEMBER) || mese.equals(Month.OCTOBER) || mese.equals(Month.NOVEMBER) || mese.equals(Month.DECEMBER))
			anno = 2019;
		else
			anno = 2020;
		
		
		LocalDate d = LocalDate.of(anno, mese, 1);
		
		while (d.getMonth().equals(mese)) {
			turni.add(soluzione.get(d).get(infermiere).substring(0, 1));
			
			d = d.plusDays(1);
		}
		
		InfermiereTurni it = new InfermiereTurni(infermiere,turni.get(0),turni.get(1),turni.get(2),turni.get(3),turni.get(4),
				turni.get(5),turni.get(6),turni.get(7),turni.get(8),turni.get(9),turni.get(10),turni.get(11),turni.get(12),turni.get(13),
				turni.get(14),turni.get(15),turni.get(16),turni.get(17),turni.get(18),turni.get(19),turni.get(20),turni.get(21),
				turni.get(22),turni.get(23),turni.get(24),turni.get(25),turni.get(26),turni.get(27));
		
		if (turni.size() >= 29)
			it.setGiorno29(turni.get(28));
		if (turni.size() >= 30)
			it.setGiorno30(turni.get(29));
		if (turni.size() == 31)
			it.setGiorno31(turni.get(30));
		
		return it;
		
	}
	
	public List<Integer> statInfermiere(Infermiere infermiere){
		
		int mat = 0;
		int pome = 0;
		int notti = 0;
		
		LocalDate d = LocalDate.of(2019, Month.SEPTEMBER, 1);
		
		while (d.isBefore(fine)) {
			if (soluzione.get(d).get(infermiere).equals("Mattino"))
				mat ++;
			else if (soluzione.get(d).get(infermiere).equals("Pomeriggio"))
				pome ++;
			else if (soluzione.get(d).get(infermiere).equals("Notte"))
				notti ++;
			
			d = d.plusDays(1);
		}
		
		List<Integer> stat = new ArrayList<Integer>();
		stat.add(mat);
		stat.add(pome);
		stat.add(notti);
		
		return stat;
		
	}


	public List<Integer> statMedie(){
		
		List<Integer> stat = new ArrayList<Integer>();
		
		int mat = 0;
		int pome = 0;
		int notti = 0;
		
		for (Infermiere inf : this.getInfermieri()) {
			mat += this.statInfermiere(inf).get(0);
			pome += this.statInfermiere(inf).get(1);
			notti += this.statInfermiere(inf).get(2);

		}
		
		stat.add(mat/this.getInfermieri().size());
		stat.add(pome/this.getInfermieri().size());
		stat.add(notti/this.getInfermieri().size());

		return stat;
	}
	
}
