package it.polito.tdp.turniinfermieri.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

		this.soluzione = new TreeMap<LocalDate, Map<Infermiere, String>>();

		this.trovata = false;
		this.dataFest = false;
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

		calcolaOrario(parziale, inizio);

		return this.soluzione;

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
			
			List<Infermiere> candidatiMattino = this.trovaCandidatiMattino(data, parziale);
			
			if (dataFest) {
				Collections.sort(candidatiMattino, new Comparator<Infermiere>() {

					@Override
					public int compare(Infermiere inf1, Infermiere inf2) {
						return inf2.getNumero_riposi_festivita() - inf1.getNumero_riposi_festivita();
					}
				});
			}
			else
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
				
				if (dataFest) {
					Collections.sort(candidatiPomeriggio, new Comparator<Infermiere>() {

						@Override
						public int compare(Infermiere inf1, Infermiere inf2) {
							return inf2.getNumero_riposi_festivita() - inf1.getNumero_riposi_festivita();
						}
					});
				}
				else
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
					
					if (dataFest) {
						Collections.sort(candidatiNotte, new Comparator<Infermiere>() {

							@Override
							public int compare(Infermiere inf1, Infermiere inf2) {
								return inf2.getNumero_riposi_festivita() - inf1.getNumero_riposi_festivita();
							}
						});
					}
					else
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
								if (dataFest)
									i.setNumero_riposi_festivita(i.getNumero_riposi_festivita() + 1);
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
			trovata = true;
			LocalDate d = inizio;

			while (d.isBefore(fine)) {
				this.soluzione.put(d, new HashMap<Infermiere, String>(parziale.get(d)));
				d = d.plusDays(1);
			}
			
			for (Infermiere i : infermieri)
				stat.add(new StatisticheInfermiere(i, i.getNumero_riposi(), i.getNumero_mattine(), i.getNumero_pomeriggi(), i.getNumero_notti(), i.getNumero_riposi_festivita()));

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