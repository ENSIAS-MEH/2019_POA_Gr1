package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ResultDisplayController implements Initializable {

	@FXML
	VBox vbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		vbox.setId("vbox");
		
		// TODO kml had l7za9
	}
	
	/**
	 * Méthode pour ajouter les especes d'une façon verticale
	 * @param especes
	 */
	public void recupererResultat(ArrayList<Espece> especes) {
		for(Espece espece : especes) {
			vbox.getChildren().add(especeRow(espece));
		}
	}
	
	/**
	 * Méthode pour generer un affichage horizontal pour une espece donnée
	 * Cet affichage sera ensuite mis dans une box verticale
	 * @param espece
	 * @return
	 */
	public HBox especeRow(Espece espece) {
		HBox row = new HBox();
		row.getChildren().addAll(new Button(espece.getNom()),new Label(espece.getDescription()));
		return row;
	}
}
