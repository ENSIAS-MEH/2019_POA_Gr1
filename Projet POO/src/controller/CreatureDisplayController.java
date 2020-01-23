package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Cette classe est le controlleur de la fenêtre d'affichage d'une créature
 * selectionnée par le biais de la fenêtre d'affichage ou par la fenêtre de
 * recherche
 * 
 * @author Amine
 *
 */
public class CreatureDisplayController implements Initializable {
	
	// L'espèces selectionnée d'après la fenetre d'affichage ou la fenetre de filtrage
	private static Espece especeSelectionnée;

	// Les éléments à afficher sur cette espèce
	@FXML
	ImageView photo;
	
	@FXML
	Label nom_de_lespece, description_de_lespece, groupe_trophique_de_lespece, groupe_ecologique_de_lespece,
			categorie_dimportance_de_lespece;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeInformations();
	}

	/**
	 * Méthode pour le bloc d'initialisation des informations relatives à la
	 * créature
	 */

	private void initializeInformations() {
		// TODO récupération de l'image

		photo.setImage(new Image((new File(especeSelectionnée.getCheminImageDisque())).toURI().toString())); // C'est pas le
																										// bon chemin ça
		nom_de_lespece.setText(especeSelectionnée.getNom());
		description_de_lespece.setText(especeSelectionnée.getDescription());
		groupe_trophique_de_lespece.setText(especeSelectionnée.getGroupeTrophique());
		groupe_ecologique_de_lespece.setText(especeSelectionnée.getGroupeEcologique());
		categorie_dimportance_de_lespece.setText(especeSelectionnée.getCategorieImportance());
	}
	
	public static void setEspece(Espece espece) {
		especeSelectionnée=espece;
	}
}
