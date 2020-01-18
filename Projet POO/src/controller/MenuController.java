package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.TrucsUtiles;

/**
 * Cette classe est le controlleur de la fenetre d'importation du fichier CSV
 * Une fois le bouton cliqu�, une fenetre pop up de l'explorateur Windows
 * devrait apparaitre, � partir de laquelle on pourrait choisir le fichier �
 * importer
 */

public class MenuController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Pas grand chose � faire ici pour le moment
	}

	/**
	 * M�thode pour le bouton pour ouvrir l'explorateur Windows, le fichier csv est
	 * stock� dans l'objet File leCsv
	 */
	@FXML
	public void importerCsvHandler(ActionEvent event) {
		// On charge le fichier csv et passe � la fenetre suivante ( affichage du
		// fichier )
		TrucsUtiles.setCsv(event, "/fxml/FileDisplay.fxml", this);
	}
}
