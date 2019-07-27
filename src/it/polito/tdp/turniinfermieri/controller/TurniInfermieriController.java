/**
 * Sample Skeleton for 'TurniInfermieri.fxml' Controller Class
 */

package it.polito.tdp.turniinfermieri.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.turniinfermieri.model.Infermiere;
import it.polito.tdp.turniinfermieri.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;

public class TurniInfermieriController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

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

    @FXML // fx:id="DatePickerFerie"
    private DatePicker DatePickerFerie; // Value injected by FXMLLoader

    @FXML // fx:id="TableViewFerie"
    private TableView<?> TableViewFerie; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerieBrevi"
    private TableColumn<?, ?> TableColumnFerieBrevi; // Value injected by FXMLLoader

    @FXML // fx:id="TableColumnFerieLunghe"
    private TableColumn<?, ?> TableColumnFerieLunghe; // Value injected by FXMLLoader

    @FXML
    void doConfermaInfermiere(ActionEvent event) {

    }

    @FXML
    void doModificaFerie(ActionEvent event) {

    }

    @FXML
    void doPeriodiFerie(ActionEvent event) {
    	
    	TextAreaErrore.clear();
    	if (!model.trimestriAccettabili(TableViewTrimestri.getItems().subList(0, TableViewTrimestri.getItems().size())))
	    		TextAreaErrore.setText("Reegole non rispettate, modificare la \nscelta dei trimestri e confermare \nnuovamente");
    	else {
    		
    	}
    }

    @FXML
    void doSelectDay(MouseEvent event) {

    }
    
    @FXML
    void onEditCommitTrimestre(TableColumn.CellEditEvent<Infermiere, Integer> trimestreCellEditEvent) {

    	Infermiere infermiere = TableViewTrimestri.getSelectionModel().getSelectedItem();
    	infermiere.setTrimestre_ferie_lunghe(trimestreCellEditEvent.getNewValue());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert TableViewTrimestri != null : "fx:id=\"TableViewTrimestri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnNome != null : "fx:id=\"TableColumnNome\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnCognome != null : "fx:id=\"TableColumnCognome\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";        assert TableColumnTrimestre != null : "fx:id=\"TableColumnTrimestre\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TextAreaErrore != null : "fx:id=\"TextAreaErrore\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert ComboBoxInfermieri != null : "fx:id=\"ComboBoxInfermieri\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert DatePickerFerie != null : "fx:id=\"DatePickerFerie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableViewFerie != null : "fx:id=\"TableViewFerie\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerieBrevi != null : "fx:id=\"TableColumnFerieBrevi\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";
        assert TableColumnFerieLunghe != null : "fx:id=\"TableColumnFerieLunghe\" was not injected: check your FXML file 'TurniInfermieri.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		// popolazione combobox
		ComboBoxInfermieri.getItems().addAll(model.getInfermieri());
		
		// popolazione tabella prima tab
    	TableColumnTrimestre.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		TableColumnNome.setCellValueFactory(new PropertyValueFactory<Infermiere, String>("nome"));
		TableColumnCognome.setCellValueFactory(new PropertyValueFactory<Infermiere, String>("cognome"));
		TableColumnTrimestre.setCellValueFactory(new PropertyValueFactory<Infermiere, Integer>("trimestre_ferie_lunghe"));
		
		ObservableList<Infermiere> rows = FXCollections.observableArrayList();
		rows.addAll(model.getInfermieri());

		
		TableViewTrimestri.setItems(rows);
	}
}
