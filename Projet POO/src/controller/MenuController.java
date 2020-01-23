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
		try {
			// On charge le fichier csv et passe à la fenetre suivante ( affichage du
			// fichier )
			TrucsUtiles.setCsv(event, "/fxml/FileDisplay.fxml", this);
			
			/*
			 * On affiche un pop qui indique que le fichier a bien été récupéré Et affiche
			 * les erreurs dans ce fichier s'il y'en a
			 */
			Stage popUp = new Stage();
			popUp.setTitle("Succès");
			Parent popUpRoot = FXMLLoader.load(getClass().getResource("/fxml/ErrorsPopUp.fxml"));
			Scene scene = new Scene(popUpRoot);
			popUp.setScene(scene);
			popUp.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
