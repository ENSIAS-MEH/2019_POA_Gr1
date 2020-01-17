package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.TrucsUtiles;

/**
 * Cette classe est le controlleur de la fenetre d'importation du fichier CSV
 * Une fois le bouton cliqu�, une fenetre pop up de l'explorateur Windows
 * devrait apparaitre, � partir de laquelle on pourrait choisir le fichier �
 * importer
 */

public class MenuController implements Initializable {

	// Le fichier sera stock� ici
	private static File LeCsv = null;

	// L'objet d'explorateur de fichiers cr��
	private static FileChooser fileChooser = new FileChooser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Pas grand chose � faire ici pour le moment
	}

	/**
	 * M�thode pour le bouton pour ouvrir l'explorateur Windows, le fichier csv est
	 * stock� dans l'objet File leCsv
	 */
	@FXML
	public void importerCsvHandler(ActionEvent e) {
		// On charge le fichier csv et passe � la fenetre suivante ( affichage du
		// fichier )
		TrucsUtiles.setCsv(e, "/fxml/FileDisplay.fxml", this);
	}
}
