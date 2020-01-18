package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import main.TrucsUtiles;

public class FileDisplayController implements Initializable {

	// On retient la barre de menu dans cette fenetre
	@FXML
	private MenuBar menuBar = new MenuBar();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Initialisation de la barre de menu
		menuBar.setId("menuBar");
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Fichier>Ouvrir un fichier On
	 * change le fichier csv, et on r�affiche
	 * @param event
	 */
	@FXML
	private void ouvrirFichierHandler(ActionEvent event) {
		/*
		 * Possibilit� de choisir de choisir un autre fichier, On refresh la scene
		 * courante utilisant le m�me fichier fxml
		 */
		TrucsUtiles.setCsv(menuBar, "/fxml/FileDisplay.fxml", this);
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse � la premiere fenetre de
	 * l'application
	 * @param event
	 */
	@FXML
	private void fermerFichierHandler(ActionEvent event) {
		TrucsUtiles.setCsvNull();
		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this);
	}
}
