package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


/**
 * Cette classe est le controlleur de la fenêtre d'affichage d'une créature selectionnée
 * par le biais de la fenêtre d'affichage ou par la fenêtre de recherche
 * @author Amine
 *
 */
public class CreatureDisplayController implements Initializable {

	Espece especeSelectionnée;

	ImageView photo;
	Label nom_de_lespece, description_de_lespece, groupe_trophique_de_lespece, groupe_ecologique_de_lespece,
			categorie_dimportance_de_lespece;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// On initialise les composants de cette fenetre pour qu'on puisse les modifier suivant l'espèce selectionnée
		photo.setId("photo");
		nom_de_lespece.setId("nom_de_lespece");
		description_de_lespece.setId("description_de_lespece");
		groupe_trophique_de_lespece.setId("groupe_trophique_de_lespece");
		groupe_ecologique_de_lespece.setId("groupe_ecologique_de_lespece");
		categorie_dimportance_de_lespece.setId("categorie_dimportance_de_lespece");
	}
	
	
	/**
	 * Méthode pour le bloc d'initialisation des informations relatives à la créature
	 */
	private void initializeInformations() {
		// TODO récupération de l'image 
		photo.setImage(especeSelectionnée.getCheminImage());
		nom_de_lespece.setText(especeSelectionnée.getNom());
		description_de_lespece.setText(especeSelectionnée.getDescription());
		groupe_trophique_de_lespece.setText(especeSelectionnée.getGroupeTrophique());
		groupe_ecologique_de_lespece.setText(especeSelectionnée.getGroupeEcologique());
		categorie_dimportance_de_lespece.setText(especeSelectionnée.getCategorieImportance());
	}
}
