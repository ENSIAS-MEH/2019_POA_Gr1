package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.TrucsUtiles;

/**
 * Cette classe est le controlleur de la fenetre d'importation du fichier CSV
 * Une fois le bouton cliqué, une fenetre pop up de l'explorateur Windows
 * devrait apparaitre, à partir de laquelle on pourrait choisir le fichier à
 * importer
 */

public class MenuController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Pas grand chose à faire ici pour le moment
	}

	/**
	 * Méthode pour le bouton pour ouvrir l'explorateur Windows, le fichier csv est
	 * stocké dans l'objet File leCsv
	 */
	@FXML
	public void importerCsvHandler(ActionEvent event) {
		// On charge le fichier csv et passe à la fenetre suivante ( affichage du
		// fichier )
		TrucsUtiles.setCsv(event, "/fxml/FileDisplay.fxml", this);
	}
}
