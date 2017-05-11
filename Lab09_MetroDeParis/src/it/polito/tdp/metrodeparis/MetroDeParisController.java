/**
 * Sample Skeleton for 'MetroDeParis.fxml' Controller Class
 */

package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.dao.MetroDAO;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataEnhanced;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbPartenza"
    private ComboBox<Fermata> cmbPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbArrivo"
    private ComboBox<Fermata> cmbArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="btbCalcolaPercorso"
    private Button btbCalcolaPercorso; // Value injected by FXMLLoader
    
    Model model;
    
    MetroDAO dao = new MetroDAO();
    
    
    public void setModel(Model model){
    	this.model=model;
    	cmbPartenza.getItems().addAll(dao.getAllFermate());
    	cmbArrivo.getItems().addAll(dao.getAllFermate());
    	//cmbPartenza.getItems().addAll(dao.getAllFermateEnhanced());
    	//cmbArrivo.getItems().addAll(dao.getAllFermateEnhanced());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	Fermata partenza = cmbPartenza.getValue() ;
    	Fermata arrivo = cmbArrivo.getValue() ;
    	
    	if(partenza==null || arrivo==null) {
    		txtResult.appendText("Errore: devi selezionare le fermate di partenza e arrivo!\n") ;
    		return ;
    	}
    	
    	String percorso = model.getPercorso(partenza, arrivo);
    	
    	txtResult.setText(percorso);

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btbCalcolaPercorso != null : "fx:id=\"btbCalcolaPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }
}
