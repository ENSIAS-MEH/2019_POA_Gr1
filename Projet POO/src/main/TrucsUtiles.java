package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dao.CSVConnectionFactory;
import dao.CSVEspeceDAO;
import dao.ConnectionFactory;
import dao.Espece;
import dao.EspeceDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Cette classe regroupe un ensemble de méthodes qu'on utilisera dans différents
 * coins de l'application.
 * 
 * @author Amine
 *
 */

public class TrucsUtiles {

	/**
	 * Le fichier .csv importé sera stocké ici
	 */
	private static File LeCsv = null;

	/**
	 * On stocke le DAO ici
	 */
	private static EspeceDAO especeDAO = null;

	/**
	 * L'objet d'explorateur de fichiers
	 */
	private static FileChooser fileChooser = new FileChooser();

	/**
	 * Méthode pour récuperer l'objet fichier csv
	 * 
	 * @see {@link main.TrucsUtiles#LeCsv}
	 * @return L'objet File correspondant au .csv importé
	 */
	public static File getCsv() {
		return LeCsv;
	}

	/**
	 * Méthode pour mettre l'objet LeCsv en null
	 * 
	 * @see {@link main.TrucsUtiles#LeCsv}
	 */
	public static void setCsvNull() {
		LeCsv = null;
	}

	/**
	 * Méthode pour ouvrir l'explorateur et choisir un fichier csv.<br>
	 * Puis charger une nouvelle scene pour la fenêtre.
	 * 
	 * @param source   Le déclencheur du changement de fichier csv
	 * @param fxmlPath Le chemin de la fenêtre de redirection
	 * @param context  L'object controlleur actuel
	 */
	public static void setCsv(Object source, String fxmlPath, Object context) {

		// On ajoute un filtre afin de ne prendre que les fichiers sous format .csv
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier .csv", "*.csv"));
		LeCsv = fileChooser.showOpenDialog(getStage(source)); // Update du File csv

		// Vérification si le csv est bel et bien selectionné
		if (!(LeCsv == null)) {
			// Récuperation des espèces
			connexionCSV(LeCsv); // Update du DAO et de la liste des espèces
			// Passage à la fenetre d'affichage du fichier
			changeStage(source, fxmlPath, context, true);
		}
	}

	/**
	 * Méthode qui effectue la connexion avec le fichier .csv par le biais d'un
	 * ConnecionFactory ( voir {@link dao.ConnectionFactory} )<br>
	 * Le DAO est stocké dans l'object especeDAO ( voir
	 * {@link main.TrucsUtiles#especeDAO} )
	 */
	private static void connexionCSV(File csv) {
		ConnectionFactory cf = new CSVConnectionFactory(csv);
		especeDAO = cf.getEspeceDAO();

	}

	/**
	 * Méthode pour le changement de fenetres
	 * 
	 * @param source   Le déclencheur du changement de fenêtre
	 * @param fxmlPath Le chemin de la fenêtre de redirection
	 * @param context  L'object controlleur actuel
	 * @return
	 */
	public static void changeStage(Object source, String fxmlPath, Object context, boolean center) {
		// La fenetre courante
		Stage currentWindow = getStage(source);

		// Changement de la fenetre courante
		if (!(currentWindow == null)) {
			try {
				Scene sc = new Scene(FXMLLoader.load(context.getClass().getResource(fxmlPath)));
				currentWindow.setScene(sc);
				if (center)
					currentWindow.centerOnScreen();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Méthode pour récuperer le stage courant
	 * 
	 * @param source Le déclencheur qui indique la fenêtre actuelle
	 * @return La fenêtre courante
	 */
	public static Stage getStage(Object source) {
		// La fenetre courante
		Stage currentWindow = null;

		// On initialise la fenetre courante suivant la source qui a déclenché l'action
		if (source.getClass() == ActionEvent.class) {
			currentWindow = (Stage) ((Node) ((ActionEvent) source).getSource()).getScene().getWindow();
		} else if (source.getClass() == MenuBar.class) {
			currentWindow = (Stage) ((MenuBar) source).getScene().getWindow();
		}
		return currentWindow;
	}

	/**
	 * Getter pour le DAO
	 * 
	 * @return l'objet {@link main.TrucsUtiles#especeDAO}
	 */
	public static EspeceDAO getDAO() {
		return especeDAO;
	}
}
