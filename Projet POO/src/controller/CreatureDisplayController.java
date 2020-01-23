package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Cette classe est le controlleur de la fen�tre d'affichage d'une cr�ature
 * selectionn�e par le biais de la fen�tre d'affichage ou par la fen�tre de
 * recherche
 * 
 * @author Amine
 *
 */
public class CreatureDisplayController implements Initializable {
	
	// L'esp�ces selectionn�e d'apr�s la fenetre d'affichage ou la fenetre de filtrage
	private static Espece especeSelectionn�e;

	// Les �l�ments � afficher sur cette esp�ce
	ImageView photo;
	Label nom_de_lespece, description_de_lespece, groupe_trophique_de_lespece, groupe_ecologique_de_lespece,
			categorie_dimportance_de_lespece;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeInformations();
	}

	/**
	 * M�thode pour le bloc d'initialisation des informations relatives � la
	 * cr�ature
	 */

	private void initializeInformations() {
		// On initialise les composants de cette fenetre pour qu'on puisse les modifier
		// suivant l'esp�ce selectionn�e
		photo.setId("photo");
		nom_de_lespece.setId("nom_de_lespece");
		description_de_lespece.setId("description_de_lespece");
		groupe_trophique_de_lespece.setId("groupe_trophique_de_lespece");
		groupe_ecologique_de_lespece.setId("groupe_ecologique_de_lespece");
		categorie_dimportance_de_lespece.setId("categorie_dimportance_de_lespece");

		// TODO r�cup�ration de l'image

		photo.setImage(new Image((new File(especeSelectionn�e.getCheminImageDisque())).toURI().toString())); // C'est pas le
																										// bon chemin �a
		nom_de_lespece.setText(especeSelectionn�e.getNom());
		description_de_lespece.setText(especeSelectionn�e.getDescription());
		groupe_trophique_de_lespece.setText(especeSelectionn�e.getGroupeTrophique());
		groupe_ecologique_de_lespece.setText(especeSelectionn�e.getGroupeEcologique());
		categorie_dimportance_de_lespece.setText(especeSelectionn�e.getCategorieImportance());
	}
	
	public static void setEspece(Espece espece) {
		especeSelectionn�e=espece;
	}
}
