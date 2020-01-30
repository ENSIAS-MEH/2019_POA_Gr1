package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.TrucsUtiles;

/**
 * Cette classe est le controlleur de la fen�tre d'affichage d'une cr�ature
 * selectionn�e par le biais de la fen�tre d'affichage ou par la fen�tre de
 * recherche
 * @author Amine
 *
 */
public class CreatureDisplayController implements Initializable {

	// L'esp�ce selectionn�e d'apr�s la fenetre d'affichage ou la fenetre de
	// filtrage
	private static Espece especeSelectionnee = null;

	// Les �l�ments � afficher sur cette esp�ce
	@FXML
	ImageView photo;

	@FXML
	MenuBar menuBar;

	@FXML
	Label nom_de_lespece, description_de_lespece, groupe_trophique_de_lespece, groupe_ecologique_de_lespece,
			categorie_dimportance_de_lespece;

	public void initialize(URL location, ResourceBundle resources) {
		initializeInformations();
	}

	/**
	 * M�thode pour le bloc d'initialisation des informations relatives � la
	 * cr�ature
	 */

	private void initializeInformations() {
		photo.setImage(new Image((new File(especeSelectionnee.getCheminImageDisque())).toURI().toString()));
		nom_de_lespece.setText(especeSelectionnee.getNom());
		description_de_lespece.setText(especeSelectionnee.getDescription());
		groupe_trophique_de_lespece.setText(especeSelectionnee.getGroupeTrophique());
		groupe_ecologique_de_lespece.setText(especeSelectionnee.getGroupeEcologique());
		categorie_dimportance_de_lespece.setText(especeSelectionnee.getCategorieImportance());
	}

	public static void setEspece(Espece espece) {
		especeSelectionnee = espece;
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Fichier>Ouvrir un fichier On
	 * change le fichier csv, et on r�affiche
	 * 
	 * @param event
	 */
	@FXML
	private void ouvrirFichierHandler(ActionEvent event) {
		try {
			/*
			 * Possibilit� de choisir de choisir un autre fichier, On refresh la scene
			 * courante utilisant le m�me fichier fxml
			 */
			TrucsUtiles.setCsv(menuBar, "/fxml/FileDisplay.fxml", this);

			/*
			 * On affiche un pop qui indique que le fichier a bien �t� r�cup�r� Et affiche
			 * les erreurs dans ce fichier s'il y'en a
			 */

			Stage popUp = new Stage();
			popUp.setTitle("Succ�s");
			Parent popUpRoot = FXMLLoader.load(getClass().getResource("/fxml/ErrorsPopUp.fxml"));
			Scene scene = new Scene(popUpRoot);
			popUp.setScene(scene);
			popUp.initOwner(TrucsUtiles.getStage(event));
			popUp.initModality(Modality.APPLICATION_MODAL);
			popUp.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse � la premiere fenetre de
	 * l'application
	 * 
	 * @param event
	 */
	@FXML
	private void fermerFichierHandler(ActionEvent event) {
		TrucsUtiles.setCsvNull();
		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this);
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Help>Rechercher esp�ces On passe
	 * � la fenetre de filtrage
	 * @param event
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this);
	}
	
	/**
	 * M�thode pour revenir � l'affichage premier du fichier
	 * @param event
	 */
	@FXML
	private void retourButtonHandler(ActionEvent event) {
		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this);
	}
}
