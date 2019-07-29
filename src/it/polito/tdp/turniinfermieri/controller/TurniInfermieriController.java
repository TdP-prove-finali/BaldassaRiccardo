/**
 * Sample Skeleton for 'TurniInfermieri.fxml' Controller Class
 */

package it.polito.tdp.turniinfermieri.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

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

    @FXML // fx:id="Tab_Orario_Reparto"
    private Tab Tab_Orario_Reparto; // Value injected by FXMLLoader

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

    @FXML
    void doModificaFerie(ActionEvent event) {
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
    	
    	TextFieldFerie.clear();
    	
    	if (infermiere != null) {
    		
    		if (!model.ferieLungheAccettabili(infermiere) && !model.ferieBreviAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie brevi e lunghe non accettabili");
    			Tab_Genera_Orario.setDisable(true);
    		}
    		else if (!model.ferieBreviAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie brevi non accettabili");
    			Tab_Genera_Orario.setDisable(true);
    		}
    		else if (!model.ferieLungheAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie lunghe non accettabili");
    			Tab_Genera_Orario.setDisable(true);
    		}
    		else if (model.ferieLungheAccettabili(infermiere) && model.ferieBreviAccettabili(infermiere)) {
    			TextFieldFerie.setText("ferie brevi e lunghe accettate");
    			Tab_Genera_Orario.setDisable(false);
    			model.modificaFerie();
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
    	}
	    else {
    		TextAreaErrore.setText("Regole rispettate, ferie accettate");
	    	Tab_Richieste_Giorni_Ferie.setDisable(false);
    		model.modificaTrimestri();
    	}
    }

    
    @FXML
    void onEditCommitTrimestre(TableColumn.CellEditEvent<Infermiere, Integer> trimestreCellEditEvent) {
    	
    	Tab_Richieste_Giorni_Ferie.setDisable(true);
    	
    	//modifica tabella  trimestre ferie
    	Infermiere infermiere = TableViewTrimestri.getSelectionModel().getSelectedItem();
    	Integer newValue = trimestreCellEditEvent.getNewValue();
    	if (newValue != null)
    		infermiere.setTrimestre_ferie_lunghe(newValue);

  

    }
    

    @FXML
    void onEditCommitFerieBrevi(TableColumn.CellEditEvent<Ferie, LocalDate> ferieBreviCellEditEvent) {

    	//modifica tabella ferie brevi
    	Ferie ferie = TableViewFerieBrevi.getSelectionModel().getSelectedItem();    	
    	LocalDate newValue = ferieBreviCellEditEvent.getNewValue();
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
		
    	if (newValue != null) {
    	
			if (infermiere.getTrimestre_ferie_lunghe() == 1 && (newValue.getMonth() == Month.SEPTEMBER
					|| newValue.getMonth() == Month.OCTOBER || newValue.getMonth() == Month.NOVEMBER)) 
				return;
				
			else if (infermiere.getTrimestre_ferie_lunghe() == 2 && (newValue.getMonth() == Month.DECEMBER
					|| newValue.getMonth() == Month.JANUARY || newValue.getMonth() == Month.FEBRUARY))
				return;
			
			else if (infermiere.getTrimestre_ferie_lunghe() == 3 && (newValue.getMonth() == Month.MARCH
					|| newValue.getMonth() == Month.APRIL || newValue.getMonth() == Month.MAY))
				return;
			
			else if (infermiere.getTrimestre_ferie_lunghe() == 4 && (newValue.getMonth() == Month.JUNE
					|| newValue.getMonth() == Month.JULY || newValue.getMonth() == Month.AUGUST))
				return;
	    	
	    		ferie.setData(newValue);
    	}
    	
    }

    @FXML
    void onEditCommitFerieLunghe(TableColumn.CellEditEvent<Ferie, LocalDate> ferieLungheCellEditEvent) {

    	//modifica tabella ferie lunghe
    	Ferie ferie = TableViewFerieLunghe.getSelectionModel().getSelectedItem();    	
    	LocalDate newValue = ferieLungheCellEditEvent.getNewValue();
    	
    	Infermiere infermiere = ComboBoxInfermieri.getValue();
		
    	if (newValue != null) {
    	
			if (infermiere.getTrimestre_ferie_lunghe() != 1 && (newValue.getMonth() == Month.SEPTEMBER
					|| newValue.getMonth() == Month.OCTOBER || newValue.getMonth() == Month.NOVEMBER)) 
				return;
				
			else if (infermiere.getTrimestre_ferie_lunghe() != 2 && (newValue.getMonth() == Month.DECEMBER
					|| newValue.getMonth() == Month.JANUARY || newValue.getMonth() == Month.FEBRUARY))
				return;
			
			else if (infermiere.getTrimestre_ferie_lunghe() != 3 && (newValue.getMonth() == Month.MARCH
					|| newValue.getMonth() == Month.APRIL || newValue.getMonth() == Month.MAY))
				return;
			
			else if (infermiere.getTrimestre_ferie_lunghe() != 4 && (newValue.getMonth() == Month.JUNE
					|| newValue.getMonth() == Month.JULY || newValue.getMonth() == Month.AUGUST))
				return;
	    	
	    		ferie.setData(newValue);
    	}

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
        assert Tab_Orario_Reparto != null : "fx:id=\"Tab_Orario_Reparto\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
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
    			
    			if (value == null)
    				return "valore non valido";
    			
    			if (infermiere.getTrimestre_ferie_lunghe() == 1 && (value.getMonth() == Month.SEPTEMBER
    					|| value.getMonth() == Month.OCTOBER || value.getMonth() == Month.NOVEMBER)) 
    				return "valore non valido";
    				
    			else if (infermiere.getTrimestre_ferie_lunghe() == 2 && (value.getMonth() == Month.DECEMBER
    					|| value.getMonth() == Month.JANUARY || value.getMonth() == Month.FEBRUARY))
    				return "valore non valido";
    			
    			else if (infermiere.getTrimestre_ferie_lunghe() == 3 && (value.getMonth() == Month.MARCH
    					|| value.getMonth() == Month.APRIL || value.getMonth() == Month.MAY))
    				return "valore non valido";
    			
    			else if (infermiere.getTrimestre_ferie_lunghe() == 4 && (value.getMonth() == Month.JUNE
    					|| value.getMonth() == Month.JULY || value.getMonth() == Month.AUGUST))
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
    			
    			if (value == null)
    				return "valore non valido";
    			
    			if (infermiere.getTrimestre_ferie_lunghe() != 1 && (value.getMonth() == Month.SEPTEMBER
    					|| value.getMonth() == Month.OCTOBER || value.getMonth() == Month.NOVEMBER)) 
    				return "valore non valido";
    				
    			else if (infermiere.getTrimestre_ferie_lunghe() != 2 && (value.getMonth() == Month.DECEMBER
    					|| value.getMonth() == Month.JANUARY || value.getMonth() == Month.FEBRUARY))
    				return "valore non valido";
    			
    			else if (infermiere.getTrimestre_ferie_lunghe() != 3 && (value.getMonth() == Month.MARCH
    					|| value.getMonth() == Month.APRIL || value.getMonth() == Month.MAY))
    				return "valore non valido";
    			
    			else if (infermiere.getTrimestre_ferie_lunghe() != 4 && (value.getMonth() == Month.JUNE
    					|| value.getMonth() == Month.JULY || value.getMonth() == Month.AUGUST))
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
    	
	}
}
