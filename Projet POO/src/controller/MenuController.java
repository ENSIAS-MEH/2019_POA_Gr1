package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
		try {
			// On charge le fichier csv et passe � la fenetre suivante ( affichage du
			// fichier )
			TrucsUtiles.setCsv(event, "/fxml/FileDisplay.fxml", this);
			
			/*
			 * On affiche un pop qui indique que le fichier a bien �t� r�cup�r� Et affiche
			 * les erreurs dans ce fichier s'il y'en a
			 */
			Stage popUp = new Stage();
			popUp.setTitle("Succ�s");
			Parent popUpRoot = FXMLLoader.load(getClass().getResource("/fxml/ErrorsPopUp.fxml"));
			Scene scene = new Scene(popUpRoot);
			popUp.setScene(scene);
			popUp.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
