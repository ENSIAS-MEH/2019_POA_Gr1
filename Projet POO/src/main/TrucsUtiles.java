package main;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Cette classe regroupe un ensemble de méthodes qu'on utilisera dans différents
 * coins de l'application
 */

public class TrucsUtiles {

	// Le fichier csv importé sera stocké ici
	private static File LeCsv = null;

	// L'objet d'explorateur de fichiers
	private static FileChooser fileChooser = new FileChooser();

	/**
	 * Méthode pour acceder au fichier
	 */
	public static File getCsv() {
		return LeCsv;
	}

	/**
	 * Méthode pour mettre le fichier en null
	 */
	public static boolean setCsvNull() {
		LeCsv = null;
		return true;
	}

	/**
	 * Méthode pour ouvrir l'explorateur et choisir un fichier csv Puis charger une
	 * nouvelle scene
	 */
	public static boolean setCsv(ActionEvent e, String fxmlPath, Object context) {

		// On ajoute un filtre afin de ne prendre que les fichiers sous format .csv

		// TODO retirer le commentaire en dessous pour appliquer le filtre sur
		// l'explorateur de fichiers

//		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier .csv", "*.csv"));
		LeCsv = fileChooser.showOpenDialog((Stage) (((Node) e.getSource()).getScene().getWindow()));
		// Vérification si le csv est bel et bien selectionné
		if (!(LeCsv == null)) {

			// TODO Ce qu'on devrait faire avec ce fichier, comment le gérer et comment
			// l'afficher

			// Passage à la fenetre suivante
			TrucsUtiles.changeStage(e, fxmlPath, context);
			return true;
		} else
			return false;
	}

	/**
	 * Méthode pour le changement de fenetres
	 */
	public static boolean changeStage(Object source, String fxmlPath, Object context) {
		// La fenetre courante
		Stage currentWindow = null;

		// On initialise la fenetre courante suivant la source qui a déclenché l'action
		if (source.getClass() == ActionEvent.class) {
			currentWindow = (Stage) ((Node) ((ActionEvent) source).getSource()).getScene().getWindow();
		} else if (source.getClass() == MenuBar.class) {
			currentWindow = (Stage) ((MenuBar) source).getScene().getWindow();
		}

		// Changement de la fenetre courante
		if (!(currentWindow == null)) {
			try {
				currentWindow.setScene(new Scene(FXMLLoader.load(context.getClass().getResource(fxmlPath))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}
}
