package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


/**
 * Cette classe est le controlleur de la fen�tre d'affichage d'une cr�ature selectionn�e
 * par le biais de la fen�tre d'affichage ou par la fen�tre de recherche
 * @author Amine
 *
 */
public class CreatureDisplayController implements Initializable {

	Espece especeSelectionn�e;

	ImageView photo;
	Label nom_de_lespece, description_de_lespece, groupe_trophique_de_lespece, groupe_ecologique_de_lespece,
			categorie_dimportance_de_lespece;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// On initialise les composants de cette fenetre pour qu'on puisse les modifier suivant l'esp�ce selectionn�e
		photo.setId("photo");
		nom_de_lespece.setId("nom_de_lespece");
		description_de_lespece.setId("description_de_lespece");
		groupe_trophique_de_lespece.setId("groupe_trophique_de_lespece");
		groupe_ecologique_de_lespece.setId("groupe_ecologique_de_lespece");
		categorie_dimportance_de_lespece.setId("categorie_dimportance_de_lespece");
	}
	
	
	/**
	 * M�thode pour le bloc d'initialisation des informations relatives � la cr�ature
	 */
	private void initializeInformations() {
		// TODO r�cup�ration de l'image 
		photo.setImage(especeSelectionn�e.getCheminImage());
		nom_de_lespece.setText(especeSelectionn�e.getNom());
		description_de_lespece.setText(especeSelectionn�e.getDescription());
		groupe_trophique_de_lespece.setText(especeSelectionn�e.getGroupeTrophique());
		groupe_ecologique_de_lespece.setText(especeSelectionn�e.getGroupeEcologique());
		categorie_dimportance_de_lespece.setText(especeSelectionn�e.getCategorieImportance());
	}
}
