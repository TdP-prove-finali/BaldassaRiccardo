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
import java.util.ResourceBundle;

import it.polito.tdp.turniinfermieri.model.Ferie;
import it.polito.tdp.turniinfermieri.model.Infermiere;
import it.polito.tdp.turniinfermieri.model.InfermiereTurni;
import it.polito.tdp.turniinfermieri.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

public class TurniInfermieriController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;   

    @FXML // fx:id="Tab_Periodi_Ferie"
    private Tab Tab_Periodi_Ferie; // Value injected by FXMLLoader
    
    @FXML // fx:id="Tab_Richieste_Giorni_Ferie"
    private Tab Tab_Richieste_Giorni_Ferie; // Value injected by FXMLLoader
    
    @FXML // fx:id="Tab_Genera_Orario"
    private Tab Tab_Genera_Orario; // Value injected by FXMLLoader
    
    @FXML // fx:id="TextFieldOrario"
    private TextField TextFieldOrario; // Value injected by FXMLLoader

    @FXML // fx:id="Tab_Orario_Infermieri"
    private Tab Tab_Orario_Infermieri; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewTrimestri"
    private TableView<Infermiere> TableViewTrimestri; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnNome"
    private TableColumn<Infermiere, String> TableColumnNome; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnCognome"
    private TableColumn<Infermiere, String> TableColumnCognome; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnTrimestre"
    private TableColumn<Infermiere, Integer> TableColumnTrimestre; // Value injected by FXMLLoader

    @FXML // fx:id="TextAreaErrore"
    private TextArea TextAreaErrore; // Value injected by FXMLLoader

    @FXML // fx:id="ComboBoxInfermieri"
    private ComboBox<Infermiere> ComboBoxInfermieri; // Value injected by FXMLLoader

    @FXML // fx:id="TextFieldFerie"
    private TextField TextFieldFerie; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerieBrevi"
    private TableView<Ferie> TableViewFerieBrevi; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerieBrevi"
    private TableColumn<Ferie, LocalDate> TableColumnFerieBrevi; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerieLunghe"
    private TableView<Ferie> TableViewFerieLunghe; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerieLunghe"
    private TableColumn<Ferie, LocalDate> TableColumnFerieLunghe; // Value injected by FXMLLoader
    
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
    private ComboBox<Month> ComboBoxMesi; // Value injected by FXMLLoader
    
    @FXML
    void doModificaFerie(ActionEvent event) {
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
    	
    	TextFieldFerie.clear();
    	
    	if (infermiere != null) {
    		
    		if (!model.ferieLungheAccettabili(infermiere) && !model.ferieBreviAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie brevi e lunghe non accettabili");
    			Tab_Periodi_Ferie.setDisable(true);
    			Tab_Genera_Orario.setDisable(true);
    		}
    		else if (!model.ferieBreviAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie brevi non accettabili");
    			Tab_Periodi_Ferie.setDisable(true);
    			Tab_Genera_Orario.setDisable(true);
    		}
    		else if (!model.ferieLungheAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie lunghe non accettabili");
    			Tab_Periodi_Ferie.setDisable(true);
    			Tab_Genera_Orario.setDisable(true);
    		}
    		else if (model.ferieLungheAccettabili(infermiere) && model.ferieBreviAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie brevi e lunghe accettate");
    			Tab_Periodi_Ferie.setDisable(false);
    			Tab_Genera_Orario.setDisable(false);
    			model.modificaFerie(infermiere);
    		}
    	}
    	else
    		TextFieldFerie.setText("seleziona infermiere");


    }

    @FXML
    void doPeriodiFerie(ActionEvent event) {
    	
    	List<Infermiere> infermieri = model.getInfermieri();
    	
    	TextAreaErrore.clear();
    	// controllo se modifiche apportate ai valori della tabella siano accettabili
    	if (!model.trimestriAccettabili(infermieri)) {
	    	TextAreaErrore.setText("Regole non rispettate, modificare la \nscelta dei trimestri e confermare \nnuovamente");
	    	Tab_Richieste_Giorni_Ferie.setDisable(true);
	    	Tab_Genera_Orario.setDisable(true);
    	}
	    else {
    		TextAreaErrore.setText("Regole rispettate, ferie accettate");
	    	Tab_Richieste_Giorni_Ferie.setDisable(false);
	    	Tab_Genera_Orario.setDisable(false);
    		model.modificaTrimestri();
    	}
    }

    
    @FXML
    void onEditCommitTrimestre(TableColumn.CellEditEvent<Infermiere, Integer> trimestreCellEditEvent) {
    	
    	Tab_Richieste_Giorni_Ferie.setDisable(true);
    	Tab_Genera_Orario.setDisable(true);
    	
    	//modifica tabella  trimestre ferie
    	Infermiere infermiere = TableViewTrimestri.getSelectionModel().getSelectedItem();
    	Integer newValue = trimestreCellEditEvent.getNewValue();
    	if (newValue != null)
    		infermiere.setTrimestre_ferie_lunghe(newValue);

  

    }
    

    @FXML
    void onEditCommitFerieBrevi(TableColumn.CellEditEvent<Ferie, LocalDate> ferieBreviCellEditEvent) {
    	
    	Tab_Periodi_Ferie.setDisable(true);
    	Tab_Genera_Orario.setDisable(true);

    	//modifica tabella ferie brevi
    	Ferie ferie = TableViewFerieBrevi.getSelectionModel().getSelectedItem();    	
    	LocalDate newValue = ferieBreviCellEditEvent.getNewValue();
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
		
		if (model.controllaFerieBrevi(newValue, infermiere.getTrimestre_ferie_lunghe()))
    		ferie.setData(newValue);
    	
    }

    @FXML
    void onEditCommitFerieLunghe(TableColumn.CellEditEvent<Ferie, LocalDate> ferieLungheCellEditEvent) {

    	Tab_Periodi_Ferie.setDisable(true);
    	Tab_Genera_Orario.setDisable(true);
    	
    	//modifica tabella ferie lunghe
    	Ferie ferie = TableViewFerieLunghe.getSelectionModel().getSelectedItem();    	
    	LocalDate newValue = ferieLungheCellEditEvent.getNewValue();
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
    	
		if (model.controllaFerieLunghe(newValue, infermiere.getTrimestre_ferie_lunghe()))
    		ferie.setData(newValue);

    }
    


    @FXML
    void doSelezionaInfermiere(ActionEvent event) {

    
    	
	Infermiere infermiere = ComboBoxInfermieri.getValue();
    	
    	if (infermiere != null) {
    		//popolazione tabella seconda tab
    		List<Ferie> ferie_lunghe = model.getFerieLungheInfermiere(infermiere);
    		List<Ferie> ferie_brevi = model.getFerieBreviInfermiere(infermiere);

    		
    		ObservableList<Ferie> rows1 = FXCollections.observableArrayList();
    		ObservableList<Ferie> rows2 = FXCollections.observableArrayList();

    		rows1.addAll(ferie_brevi);
    		rows2.addAll(ferie_lunghe);
    		
    		TableViewFerieBrevi.setItems(rows1);
    		TableViewFerieLunghe.setItems(rows2);

    	}

    }

    @FXML
    void doGeneraOrario(ActionEvent event) {
    	
    	ComboBoxMesi.setValue(null);
    	TextFieldOrario.setText("Premere sul bottone per generare l'orario!");
    	
    	model.generaOrario();
    	
    	TextFieldOrario.setText("Orario generato! Seleziona mese da visualizzare");
    	
    	
    }

    @FXML
    void doSelezionaMese(ActionEvent event) {
    	
    	Month mese = ComboBoxMesi.getValue();
    	List<InfermiereTurni> inf = new ArrayList<InfermiereTurni>();

    	if (mese != null) {
    	
	    	for (Infermiere i : model.getInfermieri()) {
	    		
	    		inf.add(model.turniInfermiere(i, mese));
	    		
	    	}
	    	
	    	
	    	ObservableList<InfermiereTurni> rows = FXCollections.observableArrayList();
			rows.addAll(inf);
	
			
			TableViewOrarioGenerale.setItems(rows);
    	}

    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert Tab_Periodi_Ferie != null : "fx:id=\"Tab_Periodi_Ferie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewTrimestri != null : "fx:id=\"TableViewTrimestri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnNome != null : "fx:id=\"TableColumnNome\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnCognome != null : "fx:id=\"TableColumnCognome\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnTrimestre != null : "fx:id=\"TableColumnTrimestre\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TextAreaErrore != null : "fx:id=\"TextAreaErrore\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert Tab_Richieste_Giorni_Ferie != null : "fx:id=\"Tab_Richieste_Giorni_Ferie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert ComboBoxInfermieri != null : "fx:id=\"ComboBoxInfermieri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TextFieldFerie != null : "fx:id=\"TextFieldFerie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerieBrevi != null : "fx:id=\"TableViewFerieBrevi\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerieBrevi != null : "fx:id=\"TableColumnFerieBrevi\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerieLunghe != null : "fx:id=\"TableViewFerieLunghe\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerieLunghe != null : "fx:id=\"TableColumnFerieLunghe\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
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
        assert Tab_Orario_Infermieri != null : "fx:id=\"Tab_Orario_Infermieri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		// popolazione combobox
		ComboBoxInfermieri.getItems().addAll(model.getInfermieri());
		
		// popolazione tabella prima tab
    	TableColumnTrimestre.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
    		
    		@Override
    		public String toString(Integer value) {

    			if (value == null)
    				return "valore non valido";
    			
    			return super.toString(value);
    		}
    		
    		@Override
    		public Integer fromString(String value) {

    			// controllo che il nuovo numero inserito sia un numero valido
    			
    			try {
					Integer.parseInt(value);
				} catch (Exception NumberFormatException) {
					return null;
				}
    			
    			if (Integer.parseInt(value) > 4 || Integer.parseInt(value) < 1)
    				return null;
    			
    			return super.fromString(value);
    		}
    	}));
		
		TableColumnNome.setCellValueFactory(new PropertyValueFactory<Infermiere, String>("nome"));
		TableColumnCognome.setCellValueFactory(new PropertyValueFactory<Infermiere, String>("cognome"));
		TableColumnTrimestre.setCellValueFactory(new PropertyValueFactory<Infermiere, Integer>("trimestre_ferie_lunghe"));
		
		ObservableList<Infermiere> rows = FXCollections.observableArrayList();
		rows.addAll(model.getInfermieri());

		
		TableViewTrimestri.setItems(rows);
		
		// tabelle ferie brevi e lunghe seconda tab
    	TableColumnFerieBrevi.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {
    		
    		@Override
    		public String toString(LocalDate value) {

    			Infermiere infermiere = ComboBoxInfermieri.getValue();
    			
    			if (!model.controllaFerieBrevi(value, infermiere.getTrimestre_ferie_lunghe()))
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
    	TableColumnFerieLunghe.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {
    		
    		@Override
    		public String toString(LocalDate value) {
    			
    			Infermiere infermiere = ComboBoxInfermieri.getValue();
    			
    			if (!model.controllaFerieLunghe(value, infermiere.getTrimestre_ferie_lunghe()))
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
    	
    	TableColumnFerieBrevi.setCellValueFactory(new PropertyValueFactory<Ferie, LocalDate>("data"));
		TableColumnFerieLunghe.setCellValueFactory(new PropertyValueFactory<Ferie, LocalDate>("data"));
		
		// tabella orario generale terza tab
    
		
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



		 
		for (int i = 9; i <=12; i++) {
			ComboBoxMesi.getItems().add(Month.of(i));
		}
		for (int i = 1; i <=8; i++) {
			ComboBoxMesi.getItems().add(Month.of(i));
		}

    	
	}
}
