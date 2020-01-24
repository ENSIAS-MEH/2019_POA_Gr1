package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dao.CSVConnectionFactory;
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
 * Cette classe regroupe un ensemble de m�thodes qu'on utilisera dans diff�rents
 * coins de l'application
 * 
 * @author Amine
 *
 */

public class TrucsUtiles {

	// Le fichier csv import� sera stock� ici
	private static File LeCsv = null;

	// On stocke le DAO ici
	private static EspeceDAO especeDAO;

	// Et les especes seront stock�es ici
	private static ArrayList<Espece> listEspeces = null;

	// L'objet d'explorateur de fichiers
	private static FileChooser fileChooser = new FileChooser();

	/**
	 * M�thode pour r�cuperer l'objet fichier csv
	 * @return
	 */
	public static File getCsv() {
		return LeCsv;
	}

	/**
	 * M�thode pour r�cuperer la liste des esp�ces
	 * 
	 * @return
	 */
	public static ArrayList<Espece> getListEspeces() {
		return listEspeces;
	}

	/**
	 * M�thode pour mettre le fichier en null
	 * 
	 * @return
	 */
	public static boolean setCsvNull() {
		LeCsv = null;
		return true;
	}

	/**
	 * M�thode pour ouvrir l'explorateur et choisir un fichier csv Puis charger une
	 * nouvelle scene
	 * 
	 * @param source   Le d�clencheur du changement de fichier csv
	 * @param fxmlPath Le path de la fen�tre de redirection
	 * @param context
	 * @return
	 */
	public static boolean setCsv(Object source, String fxmlPath, Object context) {

		// On ajoute un filtre afin de ne prendre que les fichiers sous format .csv
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier .csv", "*.csv"));
		LeCsv = fileChooser.showOpenDialog(getStage(source));

		// V�rification si le csv est bel et bien selectionn�
		if (!(LeCsv == null)) {
			// R�cuperation des esp�ces
			connexionCSV();
			// Passage � la fenetre d'affichage du fichier
			changeStage(source, fxmlPath, context);
			
			return true;
		} else
			return false;
	}

	/**
	 * M�thode qui intervient pour lire les esp�ces dans le cas de selection d'un
	 * fichier .csv
	 */
	private static void connexionCSV() {
		ConnectionFactory cf = CSVConnectionFactory.getInstance(LeCsv);
		especeDAO = cf.getEspeceDAO();
		listEspeces = especeDAO.recupererToutes();
	}

	/**
	 * M�thode pour le changement de fenetres
	 * 
	 * @param source
	 * @param fxmlPath
	 * @param context
	 * @return
	 */
	public static boolean changeStage(Object source, String fxmlPath, Object context) {
		// La fenetre courante
		Stage currentWindow = getStage(source);

		// Changement de la fenetre courante
		if (!(currentWindow == null)) {
			try {
				currentWindow.setScene(new Scene(FXMLLoader.load(context.getClass().getResource(fxmlPath))));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return true;
		} else
			return false;
	}

	/**
	 * M�thode pour r�cuperer le stage courant
	 * @param source
	 * @return
	 */
	public static Stage getStage(Object source) {
		// La fenetre courante
		Stage currentWindow = null;

		// On initialise la fenetre courante suivant la source qui a d�clench� l'action
		if (source.getClass() == ActionEvent.class) {
			currentWindow = (Stage) ((Node) ((ActionEvent) source).getSource()).getScene().getWindow();
		} else if (source.getClass() == MenuBar.class) {
			currentWindow = (Stage) ((MenuBar) source).getScene().getWindow();
		}
		return currentWindow;
	}

	/**
	 * Getter pour le DAO
	 * @return
	 */
	public static EspeceDAO getDAO() {
		return especeDAO;
	}
}
