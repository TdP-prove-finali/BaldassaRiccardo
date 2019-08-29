package it.polito.tdp.turniinfermieri.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
	private List<StatisticheInfermiere> stat;
	private List<LocalDate> festivita;
	private boolean dataFest;
	private List<Integer> vincoli;
	private int ore_lavorative_settimanali_contrattuali;
	private int max_ore_lavorative_settimanali;
	private int max_giorni_lavorativi_settimanali;
	private int debito_orario_teorico_annuale;
	private int durata_turno_lavorativo;
	private int numero_infermieri_turno_mattino;
	private int numero_infermieri_turno_pomeriggio;
	private int numero_infermieri_turno_notte;
	private int debito_orario_massimo_annuale;
	private int debito_orario_minimo_annuale;
	
	public Model() {
		dao = new TurniInfermieriDAO();
		infermieri = dao.getInfermieri();
		ferie = dao.getFerie();
		inizio = LocalDate.of(2019, Month.SEPTEMBER, 1);
		fine = LocalDate.of(2020, Month.SEPTEMBER, 1);
		infMat = new ArrayList<Infermiere>();
		infPom = new ArrayList<Infermiere>();
		infNot = new ArrayList<Infermiere>();
		stat = new ArrayList<StatisticheInfermiere>();
		festivita = new ArrayList<LocalDate>(); 	
    	// giorni festivita in italia
    	festivita.add(LocalDate.of(2020, Month.JANUARY, 1));
    	festivita.add(LocalDate.of(2020, Month.JANUARY, 6));
    	festivita.add(LocalDate.of(2020, Month.APRIL, 12));
    	festivita.add(LocalDate.of(2020, Month.APRIL, 13));
    	festivita.add(LocalDate.of(2020, Month.APRIL, 25));
    	festivita.add(LocalDate.of(2020, Month.MAY, 1));
    	festivita.add(LocalDate.of(2020, Month.JUNE, 2));
    	festivita.add(LocalDate.of(2020, Month.AUGUST, 15));
    	festivita.add(LocalDate.of(2019, Month.NOVEMBER, 1));
    	festivita.add(LocalDate.of(2019, Month.DECEMBER, 8));
    	festivita.add(LocalDate.of(2019, Month.DECEMBER, 25));
    	festivita.add(LocalDate.of(2019, Month.DECEMBER, 26));
    	// salvataggio vincoli
    	vincoli = dao.getVincoli();
    	ore_lavorative_settimanali_contrattuali = vincoli.get(1);
    	max_ore_lavorative_settimanali = vincoli.get(2);
    	durata_turno_lavorativo = vincoli.get(7);
    	numero_infermieri_turno_mattino = vincoli.get(8);
    	numero_infermieri_turno_pomeriggio = vincoli.get(9);
    	numero_infermieri_turno_notte = vincoli.get(10);
    	max_giorni_lavorativi_settimanali = max_ore_lavorative_settimanali / durata_turno_lavorativo;
    	debito_orario_teorico_annuale = ore_lavorative_settimanali_contrattuali * 52;   	
		debito_orario_massimo_annuale = debito_orario_teorico_annuale + (int) (0.02 * (double)debito_orario_teorico_annuale);
		debito_orario_minimo_annuale = debito_orario_teorico_annuale - (int) (0.02 * (double)debito_orario_teorico_annuale);
	}

	public List<Infermiere> getInfermieri() {
		return infermieri;
	}

	public boolean controllaFerie(LocalDate value) {

		if (value == null)
			return false;

		if (!value.isAfter(inizio.minusDays(1)) || !value.isBefore(fine.plusDays(1)))
			return false;
		
		return true;
	}
	
	public List<Ferie> getFerieInfermiere(Infermiere infermiere) {

		List<Ferie> f =  new ArrayList<Ferie>();
		
		for (Ferie fe : ferie) {
			if (fe.getId_infermiere() == infermiere.getId_infermiere())
				f.add(fe);
		}
		
		Collections.sort(f);
		return f;
	}

	public void modificaFerie(Infermiere infermiere) {
		
		for (Ferie f : ferie) {
			if (f.getId_infermiere() == infermiere.getId_infermiere())
				dao.modificaFerieInfermiere(new Ferie(f.getId_ferie(), f.getId_infermiere(), null));
		}
		
		for (Ferie f : ferie) {
			if (f.getId_infermiere() == infermiere.getId_infermiere())
				dao.modificaFerieInfermiere(f);
		}
	}
	
	public List<LocalDate> controlloFerieDuplicate(Infermiere infermiere) {
		List<LocalDate> giorni = new ArrayList<LocalDate>();
		int cont = 0 ;
		
		for (Ferie f1 : getFerieInfermiere(infermiere)) {
			cont = 0;
			for (Ferie f2 : getFerieInfermiere(infermiere)) {
				if (f1.getData().isEqual(f2.getData()))
					cont ++;
			}
			if (cont > 1)
				if (!giorni.contains(f1.getData()))
					giorni.add(f1.getData());
		}	
		return giorni;
	}
	
	public List<LocalDate> controlloFerieMax() {

		List<LocalDate> giorni = new ArrayList<LocalDate>();		
		int cont = 0 ;
		
		for (Ferie f1 : ferie) {
			cont = 0;
			for (Ferie f2 : ferie) {
				if (f1.getData().isEqual(f2.getData()))
					cont ++;
			}
			if (cont > 2)
				if (!giorni.contains(f1.getData()))
					giorni.add(f1.getData());
		}
		return giorni;	
	}

	public Map<LocalDate, Map<Infermiere, String>> generaOrario() {
		
		for (Infermiere inf : infermieri) {
			inf.setNumero_mattine(0);
			inf.setNumero_pomeriggi(0);
			inf.setNumero_notti(0);
			inf.setNumero_riposi(0);
			inf.setNumero_riposi_festivita(0);
		}

		soluzione = new TreeMap<LocalDate, Map<Infermiere, String>>();

		trovata = false;
		dataFest = false;
		Map<LocalDate, Map<Infermiere, String>> parziale = new TreeMap<LocalDate, Map<Infermiere, String>>();

		LocalDate data = inizio;
		// inizializzazione orario

		Map<Infermiere, String> turni = new HashMap<Infermiere, String>();

		for (Infermiere i : infermieri)
			turni.put(i, null);

		while (data.isBefore(fine)) {
			parziale.put(data, new HashMap<Infermiere, String>(turni));
			soluzione.put(data, new HashMap<Infermiere, String>());
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

		calcolaOrario(parziale, inizio);

		return soluzione;

	}

	// calcola orario attraverso la ricorsione
	private void calcolaOrario(Map<LocalDate, Map<Infermiere, String>> parziale, LocalDate data) {

		if (trovata)
			return;	
		
		if (data.getDayOfWeek().getValue() == 6 || data.getDayOfWeek().getValue() == 7 || festivita.contains(data))
			dataFest = true;
		else
			dataFest = false;

		if (data.isBefore(fine)) {
			
			List<Infermiere> candidatiMattino = trovaCandidatiMattino(data, parziale);
			
			if (dataFest) {
				Collections.sort(candidatiMattino, Infermiere.riposiFestivitaComparator());
			}
			else {
				Collections.sort(candidatiMattino,Infermiere.mattineComparator());
			}
			
			if (!diffRiposi()) {
				Collections.sort(candidatiMattino, Infermiere.riposiComparator());
			}
			
			List<List<Infermiere>> combMattino = this.subsets(candidatiMattino, numero_infermieri_turno_mattino);

			if (combMattino.size() == 0)
				return;

			for (List<Infermiere> infMat : combMattino) {

				for (Infermiere i : infMat) {
					parziale.get(data).put(i, "Mattino");
					i.setNumero_mattine(i.getNumero_mattine() + 1);
				}

				List<Infermiere> candidatiPomeriggio = this.trovaCandidatiPomeriggio(data, parziale);
				
				if (dataFest) {
					Collections.sort(candidatiPomeriggio, Infermiere.riposiFestivitaComparator());
				}
				else {
					Collections.sort(candidatiPomeriggio, Infermiere.pomeriggiComparator());
				}
				
				if (!diffRiposi()) {
					Collections.sort(candidatiPomeriggio, Infermiere.riposiComparator());
				}
				
				List<List<Infermiere>> combPomeriggio = this.subsets(candidatiPomeriggio, numero_infermieri_turno_pomeriggio);

				if (combPomeriggio.size() == 0)
					return;

				for (List<Infermiere> infPom : combPomeriggio) {

					for (Infermiere i : infPom) {
						parziale.get(data).put(i, "Pomeriggio");
						i.setNumero_pomeriggi(i.getNumero_pomeriggi() + 1);
					}

					List<Infermiere> candidatiNotte = this.trovaCandidatiNotte(data, parziale);
					
					if (dataFest) {
						Collections.sort(candidatiNotte, Infermiere.riposiFestivitaComparator());
					}
					else {
						Collections.sort(candidatiNotte, Infermiere.nottiComparator());
					}
					
					if (!diffRiposi()) {
						Collections.sort(candidatiNotte, Infermiere.riposiComparator());
					}
					
					List<List<Infermiere>> combNotte = this.subsets(candidatiNotte, numero_infermieri_turno_notte);

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
								if (dataFest)
									i.setNumero_riposi_festivita(i.getNumero_riposi_festivita() + 1);
							}
						}
						
							this.calcolaOrario(parziale, data.plusDays(1));

						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == "Riposo") {
								parziale.get(data).put(i, null);
								i.setNumero_riposi(i.getNumero_riposi() - 1);
								if (dataFest)
									i.setNumero_riposi_festivita(i.getNumero_riposi_festivita() - 1);
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
						System.out.println(debito_orario_massimo_annuale + " " + debito_orario_minimo_annuale);
			int oreTot = 0;
			int maxMat = 0;
			int minMat = Integer.MAX_VALUE;
			int maxPom = 0;
			int minPom = Integer.MAX_VALUE;
			int maxNot = 0;
			int minNot = Integer.MAX_VALUE;
			int maxRip = 0;
			int minRip = Integer.MAX_VALUE;
			int maxRipFest = 0;
			int minRipFest = Integer.MAX_VALUE;
			
			for (Infermiere inf : infermieri) {
				oreTot = (inf.getNumero_mattine() + inf.getNumero_pomeriggi() + inf.getNumero_notti() + 32) * durata_turno_lavorativo;
				System.out.println(oreTot);		
				if (oreTot > debito_orario_massimo_annuale || oreTot < debito_orario_minimo_annuale)
					return;
				
				if (inf.getNumero_mattine() < minMat)
					minMat = inf.getNumero_mattine();
				if (inf.getNumero_mattine() > maxMat)
					maxMat = inf.getNumero_mattine();
				if (inf.getNumero_pomeriggi() < minPom)
					minPom = inf.getNumero_pomeriggi();
				if (inf.getNumero_pomeriggi() > maxPom)
					maxPom = inf.getNumero_pomeriggi();
				if (inf.getNumero_notti() < minNot)
					minNot = inf.getNumero_notti();
				if (inf.getNumero_notti() > maxNot)
					maxNot = inf.getNumero_notti();
				if (inf.getNumero_riposi() < minRip)
					minRip = inf.getNumero_riposi();
				if (inf.getNumero_riposi() > maxRip)
					maxRip = inf.getNumero_riposi();
				if (inf.getNumero_riposi_festivita() < minRipFest)
					minRipFest = inf.getNumero_riposi_festivita();
				if (inf.getNumero_riposi_festivita() > maxRipFest)
					maxRipFest = inf.getNumero_riposi_festivita();
			}  
			
			
			
			System.out.println(minMat);
			System.out.println(maxMat);
			System.out.println(minPom);
			System.out.println(maxPom);
			System.out.println(minNot);
			System.out.println(maxNot);
			System.out.println(minRip);
			System.out.println(maxRip);
			System.out.println(minRipFest);
			System.out.println(maxRipFest);
			
			if (maxMat - minMat > 12)
				return;
			if (maxPom - minPom > 8)
				return;
			if (maxNot - minNot > 4)
				return;
			if (maxRip - minRip > 5)
				return;
			if (maxRipFest - minRipFest > 3)
				return;
			
			
			trovata = true;
			LocalDate d = inizio;

			while (d.isBefore(fine)) {
				soluzione.put(d, new HashMap<Infermiere, String>(parziale.get(d)));
				d = d.plusDays(1);
			}
			
			for (Infermiere i : infermieri)
				stat.add(new StatisticheInfermiere(i, i.getNumero_riposi(), i.getNumero_mattine(), i.getNumero_pomeriggi(), i.getNumero_notti(), i.getNumero_riposi_festivita()));

			return;
		}
	}

	private List<Infermiere> trovaCandidatiMattino(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		infMat.clear();
		
		if (data.equals(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infMat.add(i);
			}
		} else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte")
						&& !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio"))
					infMat.add(i);
			}
		} else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = max_giorni_lavorativi_settimanali; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont++;
				}

				if (cont < max_giorni_lavorativi_settimanali && parziale.get(data).get(i) == null
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

		if (data.equals(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infPom.add(i);
			}
		} else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte"))
					infPom.add(i);
			}
		} else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = max_giorni_lavorativi_settimanali; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont++;
				}

				if (cont < max_giorni_lavorativi_settimanali && parziale.get(data).get(i) == null
						&& !parziale.get(data.minusDays(1)).get(i).equals("Notte"))
					infPom.add(i);
			}
		}
		return infPom;
	}

	private List<Infermiere> trovaCandidatiNotte(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		infNot.clear();
		if (data.equals(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infNot.add(i);
			}
		} else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null)
					infNot.add(i);
			}
		} else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = max_giorni_lavorativi_settimanali; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie"))
						cont++;
				}

				if (cont < max_giorni_lavorativi_settimanali && parziale.get(data).get(i) == null)
					infNot.add(i);
			}
		}
		return infNot;
	}
	
	public boolean diffRiposi() {
		
		int min = Integer.MAX_VALUE;
		int max = 0;
		
		for (Infermiere inf : infermieri) {
			if (inf.getNumero_riposi() > max)
				max = inf.getNumero_riposi();
			if (inf.getNumero_riposi() < min)
				min = inf.getNumero_riposi();
		}
		
		if (max - min > 5)
			return false;
		
		return true;
	}

	List<List<Infermiere>> subsets(List<Infermiere> input, int k) {

		List<List<Infermiere>> subsets = new ArrayList<>();
		
		// input array
		// sequence length  		

		int[] s = new int[k];	// here we'll keep indices
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
	
	public StatisticheInfermiere statInfermiere(Infermiere infermiere){
		
		StatisticheInfermiere statInf = null;
		
		for (StatisticheInfermiere si : stat) {
			if (si.getInfermiere().equals(infermiere)) {
				statInf = si;
			}
		}
		
		return statInf;
	}

	public void salvaOrario(){
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("OrarioInfermieri.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println("ORARIO INFERMIERI");
		writer.println("");

		LocalDate d = inizio;
		LocalDate d2 = inizio;
		
		for (int i = 9 ; i < 13 ; i++) {
			
				Month mese = Month.of(i);
			
			writer.print(String.format("%12s", mese.getDisplayName(TextStyle.FULL, Locale.ITALY).toUpperCase()) + " " + d.getYear());
			while (d.getMonth().equals(mese)) {
				writer.print("\t" + d.getDayOfMonth());
				d = d.plusDays(1);
			}
			writer.println("");
			
			writer.print("\t\t");
			while (d2.getMonth().equals(mese)) {
				writer.print("\t" + d2.getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY));
				d2 = d2.plusDays(1);
			}			
			writer.println("");
			
			for (Infermiere inf : infermieri) {
	    		writer.println(String.format("%18s",this.turniInfermiere(inf, mese).getInfermiere()) + this.turniInfermiere(inf, mese).toString().replace("null", ""));
	    	}
			writer.println("");
			writer.println("");
		}
		
		for (int i = 1 ; i < 9 ; i++) {
			
			Month mese = Month.of(i);
		
		writer.print(String.format("%12s", mese.getDisplayName(TextStyle.FULL, Locale.ITALY).toUpperCase()) + " " + d.getYear());
		while (d.getMonth().equals(mese)) {
			writer.print("\t" + d.getDayOfMonth());
			d = d.plusDays(1);
		}
		writer.println("");
		
		writer.print("\t\t");
		while (d2.getMonth().equals(mese)) {
			writer.print("\t" + d2.getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY));
			d2 = d2.plusDays(1);
		}
		
		writer.println("");		
		for (Infermiere inf : infermieri) {
    		writer.println(String.format("%18s",this.turniInfermiere(inf, mese).getInfermiere()) + this.turniInfermiere(inf, mese).toString().replace("null", ""));
    	}
		writer.println("");
		writer.println("");
	}
		
		writer.close();
		
	}
	
}