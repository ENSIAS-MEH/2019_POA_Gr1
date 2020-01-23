package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import main.Display;

public class ResultDisplayController extends Display implements Initializable {

	@FXML
	VBox vbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		// On ajoute récupère les espèces filtrées
		recupererResultat(FilterController.resultRecherche,vbox);
	}
}
