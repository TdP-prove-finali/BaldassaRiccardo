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
	private List<StatisticheInfermiere> stat;
	private List<LocalDate> festivita;
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
		dao = new TurniInfermieriDAO(); // dao
		infermieri = dao.getInfermieri(); // ottengo tutti gli infermieri
		ferie = dao.getFerie(); // ottengo tutti i giorni di ferie di tutti gli infermieri
		inizio = LocalDate.of(2019, Month.SEPTEMBER, 1); // giorno di inizio periodo per la generazione dell'orario
		fine = LocalDate.of(2020, Month.SEPTEMBER, 1); // giorno di fine periodo per la generazione dell'orario
		stat = new ArrayList<StatisticheInfermiere>(); // lista in cui vengono salvate le statistiche di tutti gli infermieri 
		festivita = new ArrayList<LocalDate>(); // lista che contiene tutte le date delle festivita
    	// giorni festivita in italia
		festivita.add(LocalDate.of(2019, Month.NOVEMBER, 1)); // tutti i santi
    	festivita.add(LocalDate.of(2019, Month.DECEMBER, 8)); // immacolata
    	festivita.add(LocalDate.of(2019, Month.DECEMBER, 25)); // natale 
    	festivita.add(LocalDate.of(2019, Month.DECEMBER, 26)); // santo stefano
    	festivita.add(LocalDate.of(2020, Month.JANUARY, 1)); // capodanno
    	festivita.add(LocalDate.of(2020, Month.JANUARY, 6)); // epifania
    	festivita.add(LocalDate.of(2020, Month.APRIL, 12)); // pasqua
    	festivita.add(LocalDate.of(2020, Month.APRIL, 13)); // pasquetta
    	festivita.add(LocalDate.of(2020, Month.APRIL, 25)); // festa della liberazione
    	festivita.add(LocalDate.of(2020, Month.MAY, 1)); // festa del lavoro
    	festivita.add(LocalDate.of(2020, Month.JUNE, 2)); // festa della repubblica
    	festivita.add(LocalDate.of(2020, Month.AUGUST, 15)); // ferragosto
    	vincoli = dao.getVincoli(); // salvataggio vincoli
    	ore_lavorative_settimanali_contrattuali = vincoli.get(1); // ore lavorative settimanali definite da contratto
    	max_ore_lavorative_settimanali = vincoli.get(2); // ore massime lavorative in un periodo di sette giorni definite per legge
    	durata_turno_lavorativo = vincoli.get(7); // durata in ore di un singolo turno lavorativo
    	numero_infermieri_turno_mattino = vincoli.get(8); // infermieri necessari nel reparto per il turno del mattino
    	numero_infermieri_turno_pomeriggio = vincoli.get(9); // infermieri necessari nel reparto per il turno del pomeriggio
    	numero_infermieri_turno_notte = vincoli.get(10); // infermieri necessari nel reparto per il turno della notte
    	max_giorni_lavorativi_settimanali = max_ore_lavorative_settimanali / durata_turno_lavorativo; // numero massimo di giorni lavorativi in un periodo di sette giorni
    	debito_orario_teorico_annuale = ore_lavorative_settimanali_contrattuali * 52; // ore di lavoro teoriche che ogni infermiere dovrebbe fare all'anno (considerando annche i giorni di ferie)	
		debito_orario_massimo_annuale = debito_orario_teorico_annuale + (int) (0.02 * (double)debito_orario_teorico_annuale); // ore di lavoro annuali teoriche maggiorate del 2%
		debito_orario_minimo_annuale = debito_orario_teorico_annuale - (int) (0.02 * (double)debito_orario_teorico_annuale); // ore di lavoro annuali teoriche diminuite del 2%
	}

	// restituisco tutti gli infermieri
	public List<Infermiere> getInfermieri() {
		return infermieri;
	}

	// se la data modificata non rispetta il formato o è fuori dal periodo di gestione non viene accettata
	public boolean controllaFerie(LocalDate value) {

		if (value == null)
			return false;

		if (!value.isAfter(inizio.minusDays(1)) || !value.isBefore(fine.plusDays(1)))
			return false;
		
		return true;
	}
	
	// ottengo le ferie dell'infermiere passato come parametro, ordinate in ordine cronologico
	public List<Ferie> getFerieInfermiere(Infermiere infermiere) {

		List<Ferie> f =  new ArrayList<Ferie>();
		
		for (Ferie fe : ferie) {
			if (fe.getId_infermiere() == infermiere.getId_infermiere())
				f.add(fe);
		}
		
		Collections.sort(f);
		return f;
	}

	// apporto le modifiche al db per le ferie modificate correttamente attraverso le tabelle
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
	
	// controllo se dopo aver modificato le ferie non siano stati ripetuti più volte gli stessi giorni per lo stesso infermiere
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
	
	// controllo se già altri due infermieri hanno richiesto le ferie in un giorno appena modificato
	// ! C'È IL VINCOLO CHE AL MASSIMO DUE INFERMIERI POSSONO RICHIEDERE LE FERIE NELLO STESSO GIORNO
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

	// inizializzazione e richiamo della funzione ricorsiva per la generazione dell'orario
	public Map<LocalDate, Map<Infermiere, String>> generaOrario() {
		
		// inizializzazione delle statistiche di ogni infermiere
		for (Infermiere inf : infermieri) {
			inf.setNumero_mattine(0);
			inf.setNumero_pomeriggi(0);
			inf.setNumero_notti(0);
			inf.setNumero_riposi(0);
			inf.setNumero_riposi_festivita(0);
		}

		soluzione = new TreeMap<LocalDate, Map<Infermiere, String>>(); // mappa che conterrà la soluzione trovata
		trovata = false; // variabile booleana che indica se la soluzione è stata trovata o meno
		Map<LocalDate, Map<Infermiere, String>> parziale = new TreeMap<LocalDate, Map<Infermiere, String>>(); // soluzione parziale per la ricerca della soluzione completa

		// inizializzazione orario
		
		LocalDate data = inizio;

		Map<Infermiere, String> turni = new HashMap<Infermiere, String>();

		for (Infermiere i : infermieri)
			turni.put(i, null);

		// inizializzazione di parziale con tutte le date e tutti gli infermieri con i turni settati a null
		// inizializzazione di soluzione solo con tutte le date
		while (data.isBefore(fine)) {
			parziale.put(data, new HashMap<Infermiere, String>(turni));
			soluzione.put(data, new HashMap<Infermiere, String>());
			data = data.plusDays(1);
		}

		Map<Integer, Infermiere> infermieriMap = new HashMap<Integer, Infermiere>(); // identity map degli infermieri

		for (Infermiere i : infermieri)
			infermieriMap.put(i.getId_infermiere(), i);

		// inserimento di tutte le ferie degli infermieri nella soluzione parziale
		for (Ferie f : ferie) {
			Map<Infermiere, String> map = parziale.get(f.getData());
			map.put(infermieriMap.get(f.getId_infermiere()), "Ferie");
		}

		// chiamata della funzione ricorsiva
		calcolaOrario(parziale, inizio);
		
		if (!trovata)
			soluzione = null;

		return soluzione;

	}

	// calcola orario attraverso la ricorsione
	// i parametri sono la soluzione parziale e la data che rappresenta il livello della ricorsione
	private void calcolaOrario(Map<LocalDate, Map<Infermiere, String>> parziale, LocalDate data) {

		// se la soluzione è stata trovata esco dalla ricorsione
		if (trovata)
			return;	
		
		boolean dataFest; // varaiabile che indicica se il giorno corrente è un feriale o festivo (il sabato viene considerato come giorno festivo)
		
		if (data.getDayOfWeek().getValue() == 6 || data.getDayOfWeek().getValue() == 7 || festivita.contains(data))
			dataFest = true;
		else
			dataFest = false;

		// se non ho ancora completato tutto il calendario
		if (data.isBefore(fine)) {
			
			List<Infermiere> candidatiMattino = trovaCandidatiMattino(data, parziale); // ottengo tutti gli infermieri candidati a lavorare nel turno del mattino in questa giornata
			
			if (dataFest) {
				Collections.sort(candidatiMattino, Infermiere.riposiFestivitaComparator()); // ordino gli infermieri in base al numero di riposi nei giorni fetivi che hanno già fatto
			}
			else {
				Collections.sort(candidatiMattino,Infermiere.mattineComparator()); // ordino gli infermieri in base al numero di turni di mattina che hanno già fatto
			}
			
			// se c'è troppa differenza tra il numero di riposi tra due infermieri
			if (!diffRiposi()) {
				Collections.sort(candidatiMattino, Infermiere.riposiComparator()); // ordino gli infermieri in base al numero di riposi che hanno già fatto
			}
			
			List<List<Infermiere>> combMattino = this.subsets(candidatiMattino, numero_infermieri_turno_mattino); //ottengo tutte le possibili combinazioni di infermieri che possono lavorare nel turno del mattino 

			// se non esiste nemmeno una combinazione 
			if (combMattino.size() == 0)
				return;

			// per ogni combinazione di infermieri che possono lavorare nel turno del mattino
			for (List<Infermiere> infMat : combMattino) {

				// assegno agli infermieri il turno del mattino
				for (Infermiere i : infMat) {
					parziale.get(data).put(i, "Mattino");
					i.setNumero_mattine(i.getNumero_mattine() + 1);
				}

				List<Infermiere> candidatiPomeriggio = this.trovaCandidatiPomeriggio(data, parziale); // ottengo tutti gli infermieri candidati a lavorare nel turno del pomeriggio in questa giornata
				
				if (dataFest) {
					Collections.sort(candidatiPomeriggio, Infermiere.riposiFestivitaComparator()); // ordino gli infermieri in base al numero di riposi nei giorni fetivi che hanno già fatto
				}
				else {
					Collections.sort(candidatiPomeriggio, Infermiere.pomeriggiComparator()); // ordino gli infermieri in base al numero di turni di pomeriggio che hanno già fatto
				}
				
				// se c'è troppa differenza tra il numero di riposi tra due infermieri
				if (!diffRiposi()) {
					Collections.sort(candidatiPomeriggio, Infermiere.riposiComparator()); // ordino gli infermieri in base al numero di riposi che hanno già fatto
				}
				
				List<List<Infermiere>> combPomeriggio = this.subsets(candidatiPomeriggio, numero_infermieri_turno_pomeriggio); //ottengo tutte le possibili combinazioni di infermieri che possono lavorare nel turno del pomeriggio

				// se non esiste nemmeno una combinazione
				if (combPomeriggio.size() == 0)
					return;

				// per ogni combinazione di infermieri che possono lavorare nel turno del pomeriggio
				for (List<Infermiere> infPom : combPomeriggio) {

					// assegno agli infermieri il turno del pomeriggio
					for (Infermiere i : infPom) {
						parziale.get(data).put(i, "Pomeriggio");
						i.setNumero_pomeriggi(i.getNumero_pomeriggi() + 1);
					}

					List<Infermiere> candidatiNotte = this.trovaCandidatiNotte(data, parziale); // ottengo tutti gli infermieri candidati a lavorare nel turno della notte in questa giornata
					
					if (dataFest) {
						Collections.sort(candidatiNotte, Infermiere.riposiFestivitaComparator()); // ordino gli infermieri in base al numero di riposi nei giorni fetivi che hanno già fatto
					}
					else {
						Collections.sort(candidatiNotte, Infermiere.nottiComparator()); // ordino gli infermieri in base al numero di turni della notte che hanno già fatto
					}
					
					// se c'è troppa differenza tra il numero di riposi tra due infermieri
					if (!diffRiposi()) {
						Collections.sort(candidatiNotte, Infermiere.riposiComparator()); // ordino gli infermieri in base al numero di riposi che hanno già fatto
					}
					
					List<List<Infermiere>> combNotte = this.subsets(candidatiNotte, numero_infermieri_turno_notte); //ottengo tutte le possibili combinazioni di infermieri che possono lavorare nel turno della notte

					// se non esiste nemmeno una combinazione
					if (combNotte.size() == 0)
						return;

					// per ogni combinazione di infermieri che possono lavorare nel turno della notte
					for (List<Infermiere> infNot : combNotte) {

						// assegno agli infermieri il turno della notte
						for (Infermiere i : infNot) {
							parziale.get(data).put(i, "Notte");
							i.setNumero_notti(i.getNumero_notti() + 1);
						}

						// assegno il giorno di riposo a tutti gli infermieri a cui non è stato assegnato un turno lavorativo e che non sono in ferie
						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == null) {
								parziale.get(data).put(i, "Riposo");
								i.setNumero_riposi(i.getNumero_riposi() + 1);
								if (dataFest)
									i.setNumero_riposi_festivita(i.getNumero_riposi_festivita() + 1);
							}
						}
						
						// richiamo la funzione ricorsiva avendo assegnato tutti i turni in questa giornata e indicando di continuare per il giorno successivo
						this.calcolaOrario(parziale, data.plusDays(1));

						// rimuovo i giorni di riposo assegnati
						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == "Riposo") {
								parziale.get(data).put(i, null);
								i.setNumero_riposi(i.getNumero_riposi() - 1);
								if (dataFest)
									i.setNumero_riposi_festivita(i.getNumero_riposi_festivita() - 1);
							}
						}
						// rimuovo i turni della notte assegnati
						for (Infermiere i : infermieri) {
							if (parziale.get(data).get(i) == "Notte") {
								parziale.get(data).put(i, null);
								i.setNumero_notti(i.getNumero_notti() - 1);
							}
						}
					}					
					// rimuovo i turni del pomeriggio assegnati
					for (Infermiere i : infermieri) {
						if (parziale.get(data).get(i) == "Pomeriggio") {
							parziale.get(data).put(i, null);
							i.setNumero_pomeriggi(i.getNumero_pomeriggi() - 1);
						}
					}
				}				
				// rimuovo i turni del mattino assegnati
				for (Infermiere i : infermieri) {
					if (parziale.get(data).get(i) == "Mattino") {
						parziale.get(data).put(i, null);
						i.setNumero_mattine(i.getNumero_mattine() - 1);
					}
				}
			}
		}

		// quando completo tutto il calendario
		else if (data.isEqual(fine)) {
						
			if (!controlloSoluzione()) // controllo che la soluzione trovata sia accettabile
				return;
						
			trovata = true; // trovata soluzione che rispetti i vincoli di controllo
			LocalDate d = inizio;
			// salvo la soluzione finale
			while (d.isBefore(fine)) {
				soluzione.put(d, new HashMap<Infermiere, String>(parziale.get(d)));
				d = d.plusDays(1);
			}		
			// salvo le statistiche degli infermieri
			for (Infermiere i : infermieri)
				stat.add(new StatisticheInfermiere(i, i.getNumero_riposi(), i.getNumero_mattine(), i.getNumero_pomeriggi(), i.getNumero_notti(), i.getNumero_riposi_festivita()));

			return;
		}
	}
	
	private boolean controlloSoluzione() {
		
		// variaibli per la ricerca del numero minimo e massimo di turni e riposi per gli infermieri
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
			// ore totali lavorative annuali per infermiere (vengono considerati anche i 32 giorni di ferie annuali in questo conteggio)
			oreTot = (inf.getNumero_mattine() + inf.getNumero_pomeriggi() + inf.getNumero_notti() + 32) * durata_turno_lavorativo; 
			// controllo se l'infermiere ha lavorato per il numero giusto di ore durante l'anno (con uno scarto del 2%) 
			if (oreTot > debito_orario_massimo_annuale || oreTot < debito_orario_minimo_annuale) 
				return false;

			if (inf.getNumero_mattine() < minMat)
				minMat = inf.getNumero_mattine(); // numero minimo di mattine assegnate ad un infermiere
			if (inf.getNumero_mattine() > maxMat)
				maxMat = inf.getNumero_mattine(); // numero massimo di mattine assegnate ad un infermiere
			if (inf.getNumero_pomeriggi() < minPom)
				minPom = inf.getNumero_pomeriggi(); // numero minimo di pomeriggi assegnati ad un infermiere
			if (inf.getNumero_pomeriggi() > maxPom) 
				maxPom = inf.getNumero_pomeriggi(); // numero massimo di pomeriggi assegnati ad un infermiere
			if (inf.getNumero_notti() < minNot)
				minNot = inf.getNumero_notti(); // numero minimo di notti assegnate ad un infermiere
			if (inf.getNumero_notti() > maxNot)
				maxNot = inf.getNumero_notti(); // numero massimo di notti assegnate ad un infermiere
			if (inf.getNumero_riposi() < minRip)
				minRip = inf.getNumero_riposi(); // numero minimo di risposi assegnati ad un infemiere
			if (inf.getNumero_riposi() > maxRip)
				maxRip = inf.getNumero_riposi(); // numero massimo di risposi assegnati ad un infermiere
			if (inf.getNumero_riposi_festivita() < minRipFest)
				minRipFest = inf.getNumero_riposi_festivita(); // numero minimo di riposi durante giorni festivi assegnati ad un infermiere
			if (inf.getNumero_riposi_festivita() > maxRipFest)
				maxRipFest = inf.getNumero_riposi_festivita(); // numero massimo di riposi durante giorni festivi assegnati ad un infermiere
		}  
		
		// vincoli che permettono di trovare una soluzione che sia molto equa tra gli infermieri
		if (maxMat - minMat > 12) // controllo differenza massima del numero di assegnazioni del turno del mattina tra gli infermieri
			return false;
		if (maxPom - minPom > 8) // controllo differenza massima del numero di assegnazioni del turno del pomeriggio tra gli infermieri
			return false;
		if (maxNot - minNot > 4) // controllo differenza massima del numero di assegnazioni del turno della notte tra gli infermieri
			return false;
		if (maxRip - minRip > 5) // controllo differenza massima del numero di assegnazioni di riposi tra gli infermieri
			return false;
		if (maxRipFest - minRipFest > 3) // controllo differenza massima del numero di assegnazioni di riposi durante giorni festivi tra gli infermieri
			return false;
					
		return true;
	}

	// trovo quali infermieri possono essere assegnati al turno del mattino nella data passata come parametro andando a controllare la soluzione parziale fino a quel giorno 
	private List<Infermiere> trovaCandidatiMattino(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		List<Infermiere> infMat = new ArrayList<Infermiere>();
		
		// primo giorno
		if (data.equals(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null) // tutti gli infermieri a cui non è ancora stato assegnato un turno sono possibili candidati a parte quelli a cui sono state assegnate le ferie 
					infMat.add(i);
			}
		}
		// tra il primo e il settimo giorno (esclusi)
		else if (data.isBefore(inizio.plusDays(max_giorni_lavorativi_settimanali))
				&& data.isAfter(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte") // sono candidati gli infermieri a cui non è ancora stato assegnato il turno e 
						&& !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio")) // il cui giorno precedente non è stato assegnato il turno di pomeriggio, il turno di notte o le ferie
					infMat.add(i);
			}
		} 
		// in tutti gli altri giorni
		else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = max_giorni_lavorativi_settimanali; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie")) // conto quante volte hanno lavorato gli infermieri nei sei giorni precedenti
						cont++;
				}

				if (cont < max_giorni_lavorativi_settimanali && parziale.get(data).get(i) == null // sono candidati gli infermieri a cui non è ancora stato assegnato un turno e che nei sei giorni 
						&& !parziale.get(data.minusDays(1)).get(i).equals("Notte") // precedenti non hanno lavorato per almeno un giorno, che nel giorno precedente non sono stati assegnati 
						&& !parziale.get(data.minusDays(1)).get(i).equals("Pomeriggio")) // al turno del pomeriggio o della notte, e che in questo giorno non abbiano le ferie 
					infMat.add(i);
			}
		}
		return infMat;
	}

	// trovo quali infermieri possono essere assegnati al turno del pomeriggio nella data passata come parametro andando a controllare la soluzione parziale fino a quel giorno 
	private List<Infermiere> trovaCandidatiPomeriggio(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		List<Infermiere> infPom = new ArrayList<Infermiere>();

		// primo giorno
		if (data.equals(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null) // tutti gli infermieri a cui non è ancora stato assegnato un turno sono possibili candidati a parte quelli a cui sono state assegnate le ferie
					infPom.add(i);
			}
		} 
		
		// tra il primo e il settimo giorno (esclusi)
		else if (data.isBefore(inizio.plusDays(max_giorni_lavorativi_settimanali))
				&& data.isAfter(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null && !parziale.get(data.minusDays(1)).get(i).equals("Notte")) // sono candidati gli infermieri a cui non è ancora stato assegnato il turno il cui giorno
					infPom.add(i);																				  //  precendete non sono stati assegnati al turno della notte o le ferie
			}
		} 
		// in tutti gli altri giorni
		else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = max_giorni_lavorativi_settimanali; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie")) // conto quante volte hanno lavorato gli infermieri nei sei giorni precedenti
						cont++;
				}

				if (cont < max_giorni_lavorativi_settimanali && parziale.get(data).get(i) == null // sono candidati tutti gli infemieri a cui non è ancora stato assegnato un turno e che nei sei giorni precedenti non hanno
						&& !parziale.get(data.minusDays(1)).get(i).equals("Notte")) // lavorato per almeno un giorno, che nel giorno precedente non siano stati assegnati al turno di notte e che in questo giorno non abbiano le ferie
					infPom.add(i);
			}
		}
		return infPom;
	}

	// trovo quali infermieri possono essere assegnati al turno della notte nella data passata come parametro andando a controllare la soluzione parziale fino a quel giorno 
	private List<Infermiere> trovaCandidatiNotte(LocalDate data, Map<LocalDate, Map<Infermiere, String>> parziale) {

		List<Infermiere> infNot = new ArrayList<Infermiere>();
		
		// primo giorno
		if (data.equals(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null) // tutti gli infermieri a cui non è ancora stato assegnato un turno sono possibili candidati a parte quelli a cui sono state assegnate le ferie
					infNot.add(i);
			}
		} 
		// tra il primo e il settimo giorno (esclusi)
		else if (data.isBefore(LocalDate.of(2019, Month.SEPTEMBER, 7))
				&& data.isAfter(inizio)) {
			for (Infermiere i : infermieri) {
				if (parziale.get(data).get(i) == null) // tutti gli infermieri a cui non è ancora stato assegnato un turno sono possibili candidati a parte quelli a cui sono state assegnate le ferie
					infNot.add(i);
			}
		} 
		// in tutti gli altri giorni
		else {
			int cont;

			for (Infermiere i : infermieri) {
				cont = 0;

				for (int j = max_giorni_lavorativi_settimanali; j > 0; j--) {
					if (!parziale.get(data.minusDays(j)).get(i).equals("Riposo")
							&& !parziale.get(data.minusDays(j)).get(i).equals("Ferie")) // conto quante volte hanno lavorato gli infermieri nei sei giorni precedenti
						cont++;
				}

				if (cont < max_giorni_lavorativi_settimanali && parziale.get(data).get(i) == null) // sono candidati tutti gli infermieri a cui non è stato ancora assegnato un turno e che nei sei giorni 
					infNot.add(i);																   // precedenti non hanno lavorato per almeno un giorno e che in questo giorno non abbiano le ferie
			}
		}
		return infNot;
	}
	
	// controllo la differenza massima tra il numero di riposi degli infermieri
	public boolean diffRiposi() {
		
		int min = Integer.MAX_VALUE;
		int max = 0;
		
		for (Infermiere inf : infermieri) {
			if (inf.getNumero_riposi() > max)
				max = inf.getNumero_riposi();
			if (inf.getNumero_riposi() < min)
				min = inf.getNumero_riposi();
		}
		
		if (max - min > 3)
			return false;
		
		return true;
	}

	// trovo tutte le combinazioni formate da k infermieri attraverso l'uso degli indici
	List<List<Infermiere>> subsets(List<Infermiere> input, int k) {

		List<List<Infermiere>> subsets = new ArrayList<>();
		
		int[] s = new int[k];	// indici che puntano agli elementi nella lista
								
		if (k <= input.size()) {
			
			for (int i = 0; (s[i] = i) < k - 1; i++)
				;
			subsets.add(getSubset(input, s));
			for (;;) {
				int i;
				
				for (i = k - 1; i >= 0 && s[i] == input.size() - k + i; i--)
					;
				if (i < 0) {
					break;
				}
				s[i]++; 
				for (++i; i < k; i++) { 
					s[i] = s[i - 1] + 1;
				}
				subsets.add(getSubset(input, s));
			}
		}
		return subsets;
	}

	// genera la sotto-sequenza attraverso l'indice
	List<Infermiere> getSubset(List<Infermiere> input, int[] subset) {
		List<Infermiere> result = new ArrayList<Infermiere>();
		for (int i = 0; i < subset.length; i++)
			result.add(input.get(subset[i]));
		return result;
	}
	
	// restituisce tutti i turni dell'infermiere nel mese (passati come parametri)
	public InfermiereTurni turniInfermiere(Infermiere infermiere, Month mese) {
		
		int anno;
		List<String> turni = new ArrayList<String>();
		
		if (mese.equals(Month.SEPTEMBER) || mese.equals(Month.OCTOBER) || mese.equals(Month.NOVEMBER) || mese.equals(Month.DECEMBER))
			anno = 2019;
		else
			anno = 2020;
			
		LocalDate d = LocalDate.of(anno, mese, 1);
		
		while (d.getMonth().equals(mese)) {
			turni.add(soluzione.get(d).get(infermiere).substring(0, 1)); // salvo l'iniziale dei turni dell'infermiere
			
			d = d.plusDays(1);
		}
		
		// creo oggetto da visualizzare nella tabella dell'orario
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
	
	// restituisce statistiche infermiere
	public StatisticheInfermiere statInfermiere(Infermiere infermiere){
				
		for (StatisticheInfermiere si : stat) {
			if (si.getInfermiere().equals(infermiere)) {
				return si;
			}
		}		
		return null;
	}

	// salva l'orario generato in un file di testo
	public void salvaOrario(){
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("OrarioInfermieri.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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