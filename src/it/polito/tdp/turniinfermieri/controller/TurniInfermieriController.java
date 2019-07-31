/**
 * Sample Skeleton for 'TurniInfermieri.fxml' Controller Class
 */

package it.polito.tdp.turniinfermieri.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import it.polito.tdp.turniinfermieri.model.DataTurni;
import it.polito.tdp.turniinfermieri.model.Ferie;
import it.polito.tdp.turniinfermieri.model.Infermiere;
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
    private TableView<DataTurni> TableViewOrarioGenerale; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnData"
    private TableColumn<DataTurni, LocalDate> TableColumnData; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf1"
    private TableColumn<DataTurni, String> TableColumnInf1; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf2"
    private TableColumn<DataTurni, String> TableColumnInf2; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf3"
    private TableColumn<DataTurni, String> TableColumnInf3; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf4"
    private TableColumn<DataTurni, String> TableColumnInf4; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf5"
    private TableColumn<DataTurni, String> TableColumnInf5; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf6"
    private TableColumn<DataTurni, String> TableColumnInf6; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf7"
    private TableColumn<DataTurni, String> TableColumnInf7; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf8"
    private TableColumn<DataTurni, String> TableColumnInf8; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf9"
    private TableColumn<DataTurni, String> TableColumnInf9; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf10"
    private TableColumn<DataTurni, String> TableColumnInf10; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnInf11"
    private TableColumn<DataTurni, String> TableColumnInf11; // Value injected by FXMLLoader

    @FXML // fx:id="ComboBoxMesi"
    private ComboBox<String> ComboBoxMesi; // Value injected by FXMLLoader
    
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
    	
    	//List<Data> orario = model.generaOrario();

    }

    @FXML
    void doSelezionaMese(ActionEvent event) {

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
        assert TableViewOrarioGenerale != null : "fx:id=\"TableViewOrarioGenerale\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnData != null : "fx:id=\"TableColumnData\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf1 != null : "fx:id=\"TableColumnInf1\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf2 != null : "fx:id=\"TableColumnInf2\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf3 != null : "fx:id=\"TableColumnInf3\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf4 != null : "fx:id=\"TableColumnInf4\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf5 != null : "fx:id=\"TableColumnInf5\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf6 != null : "fx:id=\"TableColumnInf6\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf7 != null : "fx:id=\"TableColumnInf7\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf8 != null : "fx:id=\"TableColumnInf8\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf9 != null : "fx:id=\"TableColumnInf9\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf10 != null : "fx:id=\"TableColumnInf10\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnInf11 != null : "fx:id=\"TableColumnInf11\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
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
    	TableColumnData.setCellValueFactory(new PropertyValueFactory<DataTurni, LocalDate>("data"));
		TableColumnInf1.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf1"));
		TableColumnInf2.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf2"));
		TableColumnInf3.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf3"));
		TableColumnInf4.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf4"));
		TableColumnInf5.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf5"));
		TableColumnInf6.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf6"));
		TableColumnInf7.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf7"));
		TableColumnInf8.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf8"));
		TableColumnInf9.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf9"));
		TableColumnInf10.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf10"));
		TableColumnInf11.setCellValueFactory(new PropertyValueFactory<DataTurni, String>("turno_inf11"));

		for (int i = 1; i <=12; i++) {
			ComboBoxMesi.getItems().add(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ITALY));
		}

    	
	}
}
