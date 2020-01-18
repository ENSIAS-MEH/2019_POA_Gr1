package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dao.CSVEspeceDAO;
import dao.Espece;
import dao.FormeIncorrecteException;
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
 * @author Amine
 *
 */

public class TrucsUtiles {

	// Le fichier csv import� sera stock� ici
	private static File LeCsv = null;
	
	// Et les especes seront stock�es ici
	private static ArrayList<Espece> listEspeces = null;

	// L'objet d'explorateur de fichiers
	private static FileChooser fileChooser = new FileChooser();

	/**
	 * M�thode pour le fichier fichier
	 * @return
	 */
	public static File getCsv() {
		return LeCsv;
	}
	
	/**
	 * M�thode pour r�cuperer la liste des esp�ces
	 * @return
	 */
	public static ArrayList<Espece> getListEspeces(){
		return listEspeces;
	}

	/**
	 * M�thode pour mettre le fichier en null
	 * @return
	 */
	public static boolean setCsvNull() {
		LeCsv = null;
		return true;
	}

	/**
	 * M�thode pour ouvrir l'explorateur et choisir un fichier csv Puis charger une
	 * nouvelle scene
	 * @param source
	 * @param fxmlPath
	 * @param context
	 * @return
	 */
	public static boolean setCsv(Object source, String fxmlPath, Object context) {

		// On ajoute un filtre afin de ne prendre que les fichiers sous format .csv

		// TODO retirer le commentaire en dessous pour appliquer le filtre sur
		// l'explorateur de fichiers

//		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier .csv", "*.csv"));
		LeCsv = fileChooser.showOpenDialog(getStage(source));
		
		// V�rification si le csv est bel et bien selectionn�
		if (!(LeCsv == null)) {

			// TODO Ce qu'on devrait faire avec ce fichier, comment le g�rer et comment l'afficher
			
			try {
				listEspeces = (new CSVEspeceDAO(LeCsv)).recupererToutes();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FormeIncorrecteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Passage � la fenetre suivante
			changeStage(source, fxmlPath, context);
			return true;
		} else
			return false;
	}

	/**
	 * M�thode pour le changement de fenetres
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
}
