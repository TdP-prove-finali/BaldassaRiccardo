/**
 * Sample Skeleton for 'TurniInfermieri.fxml' Controller Class
 */

package it.polito.tdp.turniinfermieri.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.turniinfermieri.model.Ferie;
import it.polito.tdp.turniinfermieri.model.Infermiere;
import it.polito.tdp.turniinfermieri.model.InfermiereTurni;
import it.polito.tdp.turniinfermieri.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LocalDateStringConverter;

public class TurniInfermieriController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;   
    
    @FXML // fx:id="Tab_Richieste_Giorni_Ferie"
    private Tab Tab_Richieste_Giorni_Ferie; // Value injected by FXMLLoader
    
    @FXML // fx:id="Tab_Genera_Orario"
    private Tab Tab_Genera_Orario; // Value injected by FXMLLoader
    
    @FXML // fx:id="TextFieldOrario"
    private TextField TextFieldOrario; // Value injected by FXMLLoader

    @FXML // fx:id="Tab_Orario_Infermieri"
    private Tab Tab_Statistiche_Infermieri; // Value injected by FXMLLoader

    @FXML // fx:id="ComboBoxInfermieri"
    private ComboBox<Infermiere> ComboBoxInfermieri; // Value injected by FXMLLoader

    @FXML // fx:id="TextFieldFerie"
    private TextField TextFieldFerie; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerie1"
    private TableView<Ferie> TableViewFerie1; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerie1"
    private TableColumn<Ferie, LocalDate> TableColumnFerie1; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerie2"
    private TableView<Ferie> TableViewFerie2; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerie2"
    private TableColumn<Ferie, LocalDate> TableColumnFerie2; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerie3"
    private TableView<Ferie> TableViewFerie3; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerie3"
    private TableColumn<Ferie, LocalDate> TableColumnFerie3; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerie4"
    private TableView<Ferie> TableViewFerie4; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerie4"
    private TableColumn<Ferie, LocalDate> TableColumnFerie4; // Value injected by FXMLLoader
    
    @FXML // fx:id="TableViewOrarioGenerale"
    private TableView<InfermiereTurni> TableViewOrarioGenerale; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInfermiere"
    private TableColumn<InfermiereTurni, Infermiere> TableColumnInfermiere; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn01"
    private TableColumn<InfermiereTurni, String> TableColumn01; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn02"
    private TableColumn<InfermiereTurni, String> TableColumn02; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn03"
    private TableColumn<InfermiereTurni, String> TableColumn03; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn04"
    private TableColumn<InfermiereTurni, String> TableColumn04; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn05"
    private TableColumn<InfermiereTurni, String> TableColumn05; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn06"
    private TableColumn<InfermiereTurni, String> TableColumn06; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn07"
    private TableColumn<InfermiereTurni, String> TableColumn07; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn08"
    private TableColumn<InfermiereTurni, String> TableColumn08; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn09"
    private TableColumn<InfermiereTurni, String> TableColumn09; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn10"
    private TableColumn<InfermiereTurni, String> TableColumn10; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn11"
    private TableColumn<InfermiereTurni, String> TableColumn11; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn12"
    private TableColumn<InfermiereTurni, String> TableColumn12; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn13"
    private TableColumn<InfermiereTurni, String> TableColumn13; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn14"
    private TableColumn<InfermiereTurni, String> TableColumn14; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn15"
    private TableColumn<InfermiereTurni, String> TableColumn15; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn16"
    private TableColumn<InfermiereTurni, String> TableColumn16; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn17"
    private TableColumn<InfermiereTurni, String> TableColumn17; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn18"
    private TableColumn<InfermiereTurni, String> TableColumn18; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn19"
    private TableColumn<InfermiereTurni, String> TableColumn19; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn20"
    private TableColumn<InfermiereTurni, String> TableColumn20; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn21"
    private TableColumn<InfermiereTurni, String> TableColumn21; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn22"
    private TableColumn<InfermiereTurni, String> TableColumn22; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn23"
    private TableColumn<InfermiereTurni, String> TableColumn23; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn24"
    private TableColumn<InfermiereTurni, String> TableColumn24; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn25"
    private TableColumn<InfermiereTurni, String> TableColumn25; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn26"
    private TableColumn<InfermiereTurni, String> TableColumn26; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn27"
    private TableColumn<InfermiereTurni, String> TableColumn27; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn28"
    private TableColumn<InfermiereTurni, String> TableColumn28; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn29"
    private TableColumn<InfermiereTurni, String> TableColumn29; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn30"
    private TableColumn<InfermiereTurni, String> TableColumn30; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumn31"
    private TableColumn<InfermiereTurni, String> TableColumn31; // Value injected by FXMLLoader

    @FXML // fx:id="ComboBoxMesi"
    private ComboBox<String> ComboBoxMesi; // Value injected by FXMLLoader
    
    @FXML // fx:id="ComboBoxInfermieri2"
    private ComboBox<Infermiere> ComboBoxInfermieriStat; // Value injected by FXMLLoader

    @FXML // fx:id="PieChartInf"
    private PieChart PieChartInf; // Value injected by FXMLLoader

    @FXML // fx:id="PieChartMedia"
    private PieChart PieChartMedia; // Value injected by FXMLLoader
        
    @FXML
    void doModificaFerie(ActionEvent event) {
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
    	
    	TextFieldFerie.clear();
    	
    	if (infermiere != null) {
    		
    		List<LocalDate> ferieDuplicate = model.controlloFerieDuplicate(infermiere);
    		List<LocalDate> ferieMax = model.controlloFerieMax();
    		
    		if (ferieDuplicate.size() != 0 && ferieMax.size() != 0) {
    			TextFieldFerie.setText("Sono stati indicati più volte i giorni: " + ferieDuplicate + " ; Già altri due infermieri hanno indicato i giorni: " + ferieMax);
    		}
    		else if (ferieDuplicate.size() != 0) {
    			TextFieldFerie.setText("Sono stati indicati più volte i giorni: " + ferieDuplicate);
    		}
    		else if (ferieMax.size() != 0) {
    			TextFieldFerie.setText("Già altri due infermieri hanno indicato i giorni: " + ferieMax);
    		}
    		else if (ferieDuplicate.size() == 0 && ferieMax.size() == 0) {
    			TextFieldFerie.setText("ferie accettate");
    			Tab_Genera_Orario.setDisable(false);
    			model.modificaFerie(infermiere);
    	    	ComboBoxInfermieri.setDisable(false);
    		}
    	}
    	else
    		TextFieldFerie.setText("seleziona infermiere");


    }
    
    @FXML
    void onEditCommitFerie(TableColumn.CellEditEvent<Ferie, LocalDate> ferieCellEditEvent) {
    	ComboBoxInfermieri.setDisable(true);
    	Tab_Genera_Orario.setDisable(true);

    	//modifica tabella ferie 
    	//Ferie ferie = TableViewFerie4.getSelectionModel().getSelectedItem();   
    	Ferie ferie = ferieCellEditEvent.getTableView().getSelectionModel().getSelectedItem();
    	LocalDate newValue = ferieCellEditEvent.getNewValue();
    	
    		ferie.setData(newValue);
    }

    @FXML
    void doSelezionaInfermiere(ActionEvent event) {

    
    	
	Infermiere infermiere = ComboBoxInfermieri.getValue();
    	
    	if (infermiere != null) {
    		//popolazione tabelle ferie
    		List<Ferie> ferie = model.getFerieInfermiere(infermiere);

    		
    		ObservableList<Ferie> rows1 = FXCollections.observableArrayList();
    		ObservableList<Ferie> rows2 = FXCollections.observableArrayList();
    		ObservableList<Ferie> rows3 = FXCollections.observableArrayList();
    		ObservableList<Ferie> rows4 = FXCollections.observableArrayList();
    		
    		for (int i = 0 ; i < ferie.size() ; i++) {
    			
    			if (i < 8)
    				rows1.add(ferie.get(i));
    			else if (i < 16 && i > 7)
    				rows2.add(ferie.get(i));
    			else if (i < 24 && i > 15)
        			rows3.add(ferie.get(i));
        		else 
            		rows4.add(ferie.get(i));
    		}
    		
    		TableViewFerie1.setItems(rows1);
    		TableViewFerie2.setItems(rows2);
    		TableViewFerie3.setItems(rows3);
    		TableViewFerie4.setItems(rows4);

    	}
    }

    @FXML
    void doGeneraOrario(ActionEvent event) {
    	
    	TableViewOrarioGenerale.getItems().clear();
    	
    	ComboBoxMesi.setValue(null);
    	TextFieldOrario.setText("Premere sul bottone per generare l'orario!");
    	
    	 Map<LocalDate, Map<Infermiere, String>> soluzione = model.generaOrario();
    	 
    	 if (soluzione == null) {
    	    	TextFieldOrario.setText("Creazione orario non riuscita, modificare ferie e riprovare");
    	    	Tab_Statistiche_Infermieri.setDisable(true);
    	 }
    	 
    	 else {
 	    	Tab_Statistiche_Infermieri.setDisable(false);
    	
	    	TextFieldOrario.setText("Orario generato! Seleziona mese da visualizzare");
	    	
	    	ComboBoxMesi.getItems().clear();
	    	
	    	for (int i = 9; i <=12; i++) {
				ComboBoxMesi.getItems().add(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ITALY));
			}
			for (int i = 1; i <=8; i++) {
				ComboBoxMesi.getItems().add(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ITALY));
			}
		
    	}
    	
    	
    }

    @FXML
    void doSelezionaMese(ActionEvent event) {
    	
    	TableColumn29.setVisible(false);
    	TableColumn30.setVisible(false);
    	TableColumn31.setVisible(false);

    	
    	
    	String m = ComboBoxMesi.getValue();
    	
    	List<String> mesiIta= new ArrayList<>();
    	mesiIta.add("gennaio");
    	mesiIta.add("febbraio");
    	mesiIta.add("marzo");
    	mesiIta.add("aprile");
    	mesiIta.add("maggio");
    	mesiIta.add("giugno");
    	mesiIta.add("luglio");
    	mesiIta.add("agosto");
    	mesiIta.add("settembre");
    	mesiIta.add("ottobre");
    	mesiIta.add("novembre");
    	mesiIta.add("dicembre");

    	Month mese = null;
    	
    	for (int i = 0 ; i < 12 ; i++) {
    		if (mesiIta.get(i).equals(m))
    			mese = Month.of(i+1);
    	}
    	
    	
    	List<InfermiereTurni> inf = new ArrayList<InfermiereTurni>();

    	if (mese != null) {
    	
	    	for (Infermiere i : model.getInfermieri()) {
	    		
	    		inf.add(model.turniInfermiere(i, mese));
	    		
	    	}
	    	
	    	
	    	
	    	int anno;
			
			if (mese.equals(Month.SEPTEMBER) || mese.equals(Month.OCTOBER) || mese.equals(Month.NOVEMBER) || mese.equals(Month.DECEMBER))
				anno = 2019;
			else
				anno = 2020;
	    	
	    	InfermiereTurni giorni = new InfermiereTurni(null, LocalDate.of(anno, mese, 1).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 2).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 3).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 4).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 5).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 6).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 7).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 8).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 9).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 10).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 11).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 12).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 13).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 14).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 15).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 16).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 17).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 18).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 19).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 20).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 21).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 22).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 23).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 24).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 25).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 26).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 27).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY),
	    			LocalDate.of(anno, mese, 28).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY));
	    			
	    	
	    	if (inf.get(0).getGiorno29() != null) {
	        	TableColumn29.setVisible(true);
	        	giorni.setGiorno29(LocalDate.of(anno, mese, 29).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY));
	    	}
	    	if (inf.get(0).getGiorno30() != null) {
	        	TableColumn30.setVisible(true);
	        	giorni.setGiorno30(LocalDate.of(anno, mese, 30).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY));

	    	}
	    	if (inf.get(0).getGiorno31() != null) {
	        	TableColumn31.setVisible(true);
	        	giorni.setGiorno31(LocalDate.of(anno, mese, 31).getDayOfWeek().getDisplayName(TextStyle.SHORT,Locale.ITALY));
	    	}
	    	
	    	ObservableList<InfermiereTurni> rows = FXCollections.observableArrayList();
    		
	    	pulisciColonna(TableColumn01);
	    	pulisciColonna(TableColumn02);
	    	pulisciColonna(TableColumn03);
	    	pulisciColonna(TableColumn04);
	    	pulisciColonna(TableColumn05);
	    	pulisciColonna(TableColumn06);
	    	pulisciColonna(TableColumn07);
	    	pulisciColonna(TableColumn08);
	    	pulisciColonna(TableColumn09);
	    	pulisciColonna(TableColumn10);
	    	pulisciColonna(TableColumn11);
	    	pulisciColonna(TableColumn12);
	    	pulisciColonna(TableColumn13);
	    	pulisciColonna(TableColumn14);
	    	pulisciColonna(TableColumn15);
	    	pulisciColonna(TableColumn16);
	    	pulisciColonna(TableColumn17);
	    	pulisciColonna(TableColumn18);
	    	pulisciColonna(TableColumn19);
	    	pulisciColonna(TableColumn20);
	    	pulisciColonna(TableColumn21);
	    	pulisciColonna(TableColumn22);
	    	pulisciColonna(TableColumn23);
	    	pulisciColonna(TableColumn24);
	    	pulisciColonna(TableColumn25);
	    	pulisciColonna(TableColumn26);
	    	pulisciColonna(TableColumn27);
	    	pulisciColonna(TableColumn28);
	    	pulisciColonna(TableColumn29);
	    	pulisciColonna(TableColumn30);
	    	pulisciColonna(TableColumn31);
	    	
	    	List<LocalDate> festivita = new ArrayList<LocalDate>();
	    	
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


	    	
	    	if (giorni.getGiorno1().equals("dom") || giorni.getGiorno1().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 1))) {
	    		festivitaColonna(TableColumn01);
	    	}
	    	if (giorni.getGiorno2().equals("dom") || giorni.getGiorno2().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 2))) {
	    		festivitaColonna(TableColumn02);
	    	}
	    	if (giorni.getGiorno3().equals("dom") || giorni.getGiorno3().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 3))) {
	    		festivitaColonna(TableColumn03);
	    	}
	    	if (giorni.getGiorno4().equals("dom") || giorni.getGiorno4().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 4))) {
	    		festivitaColonna(TableColumn04);
	    	}
	    	if (giorni.getGiorno5().equals("dom") || giorni.getGiorno5().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 5))) {
	    		festivitaColonna(TableColumn05);
	    	}
	    	if (giorni.getGiorno6().equals("dom") || giorni.getGiorno6().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 6))) {
	    		festivitaColonna(TableColumn06);
	    	}
	    	if (giorni.getGiorno7().equals("dom") || giorni.getGiorno7().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 7))) {
	    		festivitaColonna(TableColumn07);
	    	}
	    	if (giorni.getGiorno8().equals("dom") || giorni.getGiorno8().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 8))) {
	    		festivitaColonna(TableColumn08);
	    	}
	    	if (giorni.getGiorno9().equals("dom") || giorni.getGiorno9().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 9))) {
	    		festivitaColonna(TableColumn09);
	    	}
	    	if (giorni.getGiorno10().equals("dom") || giorni.getGiorno10().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 10))) {
	    		festivitaColonna(TableColumn10);
	    	}
	    	if (giorni.getGiorno11().equals("dom") || giorni.getGiorno11().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 11))) {
	    		festivitaColonna(TableColumn11);
	    	}
	    	if (giorni.getGiorno12().equals("dom") || giorni.getGiorno12().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 12))) {
	    		festivitaColonna(TableColumn12);
	    	}
	    	if (giorni.getGiorno13().equals("dom") || giorni.getGiorno13().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 13))) {
	    		festivitaColonna(TableColumn13);
	    	}
	    	if (giorni.getGiorno14().equals("dom") || giorni.getGiorno14().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 14))) {
	    		festivitaColonna(TableColumn14);
	    	}
	    	if (giorni.getGiorno15().equals("dom") || giorni.getGiorno15().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 15))) {
	    		festivitaColonna(TableColumn15);
	    	}
	    	if (giorni.getGiorno16().equals("dom") || giorni.getGiorno16().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 16))) {
	    		festivitaColonna(TableColumn16);
	    	}
	    	if (giorni.getGiorno17().equals("dom") || giorni.getGiorno17().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 17))) {
	    		festivitaColonna(TableColumn17);
	    	}
	    	if (giorni.getGiorno18().equals("dom") || giorni.getGiorno18().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 18))) {
	    		festivitaColonna(TableColumn18);
	    	}
	    	if (giorni.getGiorno19().equals("dom") || giorni.getGiorno19().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 19))) {
	    		festivitaColonna(TableColumn19);
	    	}
	    	if (giorni.getGiorno20().equals("dom") || giorni.getGiorno20().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 20))) {
	    		festivitaColonna(TableColumn20);
	    	}
	    	if (giorni.getGiorno21().equals("dom") || giorni.getGiorno21().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 21))) {
	    		festivitaColonna(TableColumn21);
	    	}
	    	if (giorni.getGiorno22().equals("dom") || giorni.getGiorno22().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 22))) {
	    		festivitaColonna(TableColumn22);
	    	}
	    	if (giorni.getGiorno23().equals("dom") || giorni.getGiorno23().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 23))) {
	    		festivitaColonna(TableColumn23);
	    	}
	    	if (giorni.getGiorno24().equals("dom") || giorni.getGiorno24().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 24))) {
	    		festivitaColonna(TableColumn24);
	    	}
	    	if (giorni.getGiorno25().equals("dom") || giorni.getGiorno25().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 25))) {
	    		festivitaColonna(TableColumn25);
	    	}
	    	if (giorni.getGiorno26().equals("dom") || giorni.getGiorno26().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 26))) {
	    		festivitaColonna(TableColumn26);
	    	}
	    	if (giorni.getGiorno27().equals("dom") || giorni.getGiorno27().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 27))) {
	    		festivitaColonna(TableColumn27);
	    	}
	    	if (giorni.getGiorno28().equals("dom") || giorni.getGiorno28().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 28))) {
	    		festivitaColonna(TableColumn28);
	    	}
	    	if (giorni.getGiorno29() != null) {
	    		if (giorni.getGiorno29().equals("dom") || giorni.getGiorno29().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 29))) {
	    			festivitaColonna(TableColumn29);
	    		}
	    	}
	    	if (giorni.getGiorno30() != null) {
		    	if (giorni.getGiorno30().equals("dom") || giorni.getGiorno30().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 30))) {
		    		festivitaColonna(TableColumn30);
		    	}
	    	}
	    	if (giorni.getGiorno31() != null) {
		    	if (giorni.getGiorno31().equals("dom") || giorni.getGiorno31().equals("sab") || festivita.contains(LocalDate.of(anno, mese, 31))) {
		    		festivitaColonna(TableColumn31);
		    	}
	    	}
	    
	    	
	    	rows.add(giorni);
			rows.addAll(inf);
	
			
			TableViewOrarioGenerale.setItems(rows);
    	}

    }
    
    void festivitaColonna(TableColumn<InfermiereTurni, String> col) {
    	
    	col.setCellFactory(column ->  {
    	    return new TableCell<InfermiereTurni, String>() {
    	                @Override
    	                protected void updateItem(String item, boolean empty) {
    	                    super.updateItem(item, empty);
    	                     
    	                        setStyle("-fx-background-color: yellow ; -fx-text-background-color: black ; ");
    	                        setText(item);   
    	                }
    	            };
    	});
    	
    }
    
    
    void pulisciColonna(TableColumn<InfermiereTurni, String> col) {
    	
    	col.setCellFactory(column ->  {
    	    return new TableCell<InfermiereTurni, String>() {
    	                @Override
    	                protected void updateItem(String item, boolean empty) {
    	                    super.updateItem(item, empty);
    	                    
    	                    setStyle(null);
    	                    setText(item);   
    	                }
    	            };
    	});
    	
    }   
    
    @FXML
    void doSelezionaInfermiereStat(ActionEvent event) {
    	
    	Infermiere infermiere = ComboBoxInfermieriStat.getValue();

    	if (infermiere != null) {
    		
    		List<Integer> statInf = model.statInfermiere(infermiere);
    		List<Integer> statMedie = model.statMedie();
    		
    		ObservableList<PieChart.Data> pieChartInfData = FXCollections.observableArrayList(
    				new PieChart.Data("Mattino", statInf.get(0)),
    				new PieChart.Data("Pomeriggio", statInf.get(1)),
    				new PieChart.Data("Notte", statInf.get(2)));
    		
    		ObservableList<PieChart.Data> pieChartMedieData = FXCollections.observableArrayList(
    				new PieChart.Data("Mattino", statMedie.get(0)),
    				new PieChart.Data("Pomeriggio", statMedie.get(1)),
    				new PieChart.Data("Notte", statMedie.get(2)));
    		
    		PieChartInf.setData(pieChartInfData);
    		PieChartMedia.setData(pieChartMedieData);
    		PieChartInf.setStartAngle(90);
    		PieChartMedia.setStartAngle(90);

    		
    	}
    	

    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Tab_Richieste_Giorni_Ferie != null : "fx:id=\"Tab_Richieste_Giorni_Ferie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert ComboBoxInfermieri != null : "fx:id=\"ComboBoxInfermieri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TextFieldFerie != null : "fx:id=\"TextFieldFerie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerie1 != null : "fx:id=\"TableViewFerie1\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerie1 != null : "fx:id=\"TableColumnFerie1\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerie2 != null : "fx:id=\"TableViewFerie2\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerie2 != null : "fx:id=\"TableColumnFerie2\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerie3 != null : "fx:id=\"TableViewFerie3\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerie3 != null : "fx:id=\"TableColumnFerie3\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerie4 != null : "fx:id=\"TableViewFerie4\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerie4 != null : "fx:id=\"TableColumnFerie4\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert Tab_Genera_Orario != null : "fx:id=\"Tab_Genera_Orario\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TextFieldOrario != null : "fx:id=\"TextFieldOrario\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewOrarioGenerale != null : "fx:id=\"TableViewOrarioGenerale\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInfermiere != null : "fx:id=\"TableColumnInfermiere\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn01 != null : "fx:id=\"TableColumn01\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn02 != null : "fx:id=\"TableColumn02\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn03 != null : "fx:id=\"TableColumn03\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn04 != null : "fx:id=\"TableColumn04\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn05 != null : "fx:id=\"TableColumn05\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn06 != null : "fx:id=\"TableColumn06\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn07 != null : "fx:id=\"TableColumn07\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn08 != null : "fx:id=\"TableColumn08\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn09 != null : "fx:id=\"TableColumn09\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn10 != null : "fx:id=\"TableColumn10\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn11 != null : "fx:id=\"TableColumn11\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn12 != null : "fx:id=\"TableColumn12\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn13 != null : "fx:id=\"TableColumn13\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn14 != null : "fx:id=\"TableColumn14\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn15 != null : "fx:id=\"TableColumn15\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn16 != null : "fx:id=\"TableColumn16\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn17 != null : "fx:id=\"TableColumn17\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn18 != null : "fx:id=\"TableColumn18\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn19 != null : "fx:id=\"TableColumn19\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn20 != null : "fx:id=\"TableColumn20\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn21 != null : "fx:id=\"TableColumn21\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn22 != null : "fx:id=\"TableColumn22\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn23 != null : "fx:id=\"TableColumn23\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn24 != null : "fx:id=\"TableColumn24\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn25 != null : "fx:id=\"TableColumn25\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn26 != null : "fx:id=\"TableColumn26\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn27 != null : "fx:id=\"TableColumn27\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn28 != null : "fx:id=\"TableColumn28\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn29 != null : "fx:id=\"TableColumn29\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn30 != null : "fx:id=\"TableColumn30\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumn31 != null : "fx:id=\"TableColumn31\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert Tab_Statistiche_Infermieri != null : "fx:id=\"Tab_Orario_Infermieri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert ComboBoxInfermieriStat != null : "fx:id=\"ComboBoxInfermieri2\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert PieChartInf != null : "fx:id=\"PieChartInf\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert PieChartMedia != null : "fx:id=\"PieChartMedia\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;
		// popolazione combobox
		ComboBoxInfermieri.getItems().addAll(model.getInfermieri());
		ComboBoxInfermieriStat.getItems().addAll(model.getInfermieri());
		
		// tabelle ferie
    	TableColumnFerie1.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {
    		
    		@Override
    		public String toString(LocalDate value) {
    			
    			if (!model.controllaFerie(value))
    				return "valore non valido";
    			
    			return super.toString(value);
    		}
    		
    		@Override
    		public LocalDate fromString(String value) {
    			
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    			
    			//controllo che la nuova data inserita sia valida
    			
    			try {
        			LocalDate.parse(value, formatter);
				} catch (Exception DateTimeParseException) {
					return null;
				}
    			
    			return super.fromString(value);
    		}
    	}));
    	TableColumnFerie2.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {
    		
    		@Override
    		public String toString(LocalDate value) {
    			    			
    			if (!model.controllaFerie(value))
    				return "valore non valido";
    			
    			return super.toString(value);
    		}
    		
    		@Override
    		public LocalDate fromString(String value) {

    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    			
    			//controllo che la nuova data inserita sia valida
    			
    			try {
        			LocalDate.parse(value, formatter);
				} catch (Exception DateTimeParseException) {
					return null;
				}
    			
    			return super.fromString(value);
    		}
    	}));
    	
    	TableColumnFerie3.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {
    		
    		@Override
    		public String toString(LocalDate value) {
    			    			
    			if (!model.controllaFerie(value))
    				return "valore non valido";
    			
    			return super.toString(value);
    		}
    		
    		@Override
    		public LocalDate fromString(String value) {

    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    			
    			//controllo che la nuova data inserita sia valida
    			
    			try {
        			LocalDate.parse(value, formatter);
				} catch (Exception DateTimeParseException) {
					return null;
				}
    			
    			return super.fromString(value);
    		}
    	}));
    	
    	TableColumnFerie4.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {
    		
    		@Override
    		public String toString(LocalDate value) {
    			    			
    			if (!model.controllaFerie(value))
    				return "valore non valido";
    			
    			return super.toString(value);
    		}
    		
    		@Override
    		public LocalDate fromString(String value) {

    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    			
    			//controllo che la nuova data inserita sia valida
    			
    			try {
        			LocalDate.parse(value, formatter);
				} catch (Exception DateTimeParseException) {
					return null;
				}
    			
    			return super.fromString(value);
    		}
    	}));
    	
    	// tabelle ferie
    	TableColumnFerie1.setCellValueFactory(new PropertyValueFactory<Ferie, LocalDate>("data"));
		TableColumnFerie2.setCellValueFactory(new PropertyValueFactory<Ferie, LocalDate>("data"));
		TableColumnFerie3.setCellValueFactory(new PropertyValueFactory<Ferie, LocalDate>("data"));
		TableColumnFerie4.setCellValueFactory(new PropertyValueFactory<Ferie, LocalDate>("data"));
		
		// tabella generazione orario
		TableColumnInfermiere.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, Infermiere>("Infermiere"));
		TableColumn01.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno1"));
		TableColumn02.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno2"));
		TableColumn03.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno3"));
		TableColumn04.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno4"));
		TableColumn05.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno5"));
		TableColumn06.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno6"));
		TableColumn07.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno7"));
		TableColumn08.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno8"));
		TableColumn09.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno9"));
		TableColumn10.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno10"));
		TableColumn11.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno11"));
		TableColumn12.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno12"));
		TableColumn13.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno13"));
		TableColumn14.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno14"));
		TableColumn15.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno15"));
		TableColumn16.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno16"));
		TableColumn17.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno17"));
		TableColumn18.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno18"));
		TableColumn19.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno19"));
		TableColumn20.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno20"));
		TableColumn21.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno21"));
		TableColumn22.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno22"));
		TableColumn23.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno23"));
		TableColumn24.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno24"));
		TableColumn25.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno25"));
		TableColumn26.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno26"));
		TableColumn27.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno27"));
		TableColumn28.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno28"));
		TableColumn29.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno29"));
		TableColumn30.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno30"));
		TableColumn31.setCellValueFactory(new PropertyValueFactory<InfermiereTurni, String>("giorno31"));
    	
	}
}
