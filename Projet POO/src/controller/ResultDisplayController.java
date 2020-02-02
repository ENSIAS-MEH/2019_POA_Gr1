package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.TrucsUtiles;

/**
 * Controlleur de la fenêtre de resultats du filtrage<br>
 * L'affichage est similaire à l'affichage de base du fichier, sans possibilité
 * d'ajout ou de suppression parcontre.<br>
 * On peut retourner à l'affichage de base du fichier par un bouton dédié.
 * 
 * @author Amine
 *
 */
public class ResultDisplayController implements Initializable {

	@FXML
	private HBox hbox_0, hbox_1, hbox_2, hbox_3;

	@FXML
	MenuBar menuBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hbox_0.setId("hbox_0");
		hbox_1.setId("hbox_1");
		hbox_2.setId("hbox_2");
		hbox_3.setId("hbox_3");
		// On ajoute récupère les espèces filtrées
		recupererResultat(FilterController.resultRecherche);
	}

	/**
	 * Méthode pour l'option dans la barre de menu > Fichier > Ouvrir un
	 * fichier.<br>
	 * On change le fichier csv, et on réaffiche.<br>
	 * Elle se déclenche en cliquant sur le bouton y associé.
	 * 
	 * @param event Evenement de clic sur le bouton
	 * @see {@link main.TrucsUtiles#setCsv(Object, String, Object)
	 */
	@FXML
	private void ouvrirFichierHandler(ActionEvent event) {
		try {
			/*
			 * Possibilité de choisir de choisir un autre fichier, On refresh la scene
			 * courante utilisant le même fichier fxml
			 */
			TrucsUtiles.setCsv(menuBar, "/fxml/FileDisplay.fxml", this);

			/*
			 * On affiche un pop qui indique que le fichier a bien été récupéré Et affiche
			 * les erreurs dans ce fichier s'il y'en a
			 */

			Stage popUp = new Stage();
			popUp.setTitle("Succès");
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
	 * Méthode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse à la premiere fenetre de
	 * l'application<br>
	 * Elle se déclenche en cliquant sur le bouton y associé.
	 * 
	 * @param event Evenement de clic sur le bouton
	 * @see {@link main.TrucsUtiles#setCsvNull()} {@link main.TrucsUtiles#getCsv()}
	 */
	@FXML
	private void fermerFichierHandler(ActionEvent event) {
		TrucsUtiles.setCsvNull();
		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this, true);
	}

	/**
	 * Méthode pour l'option dans la barre de menu > Help > Rechercher espèces On
	 * passe à la fenetre de filtrage<br>
	 * Elle se déclenche en cliquant sur le bouton y associé
	 * 
	 * @param event Evenement de clic sur le bouton
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this, true);
	}

	/**
	 * Méthode pour revenir à l'affichage du fichier.
	 * 
	 * @param event Evenement de clic sur le bouton
	 */
	@FXML
	private void retourButtonHandler(ActionEvent event) {
		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this, true);
	}

	/**
	 * Méthode pour initialiser afficher les espèces.<br>
	 * Elles seront reparties selon des niveaux de 0 à 3, chaque niveau correspond à
	 * un HBox.<br>
	 * Les espèces seront chargées à partir du DAO.
	 * 
	 * @param especes ArrayList d'espèces, il correspondra à la valeur retournée par
	 *                la méthode {@link dao.EspeceDAO#recupererToutes()}.
	 */
	private void recupererResultat(ArrayList<Espece> especes) {

		hbox_0.getChildren().clear();
		hbox_1.getChildren().clear();
		hbox_2.getChildren().clear();
		hbox_3.getChildren().clear();

		for (Espece espece : especes) {
			switch (espece.getZone()) {
			case 0:
				hbox_0.getChildren().add(especeVBox(espece));
				break;
			case 1:
				hbox_1.getChildren().add(especeVBox(espece));
				break;
			case 2:
				hbox_2.getChildren().add(especeVBox(espece));
				break;
			case 3:
				hbox_3.getChildren().add(especeVBox(espece));
				break;
			}
		}
	}

	/**
	 * Méthode privée pour créer l'affichage d'une seule espèce.
	 * 
	 * @param espece L'espèce pour laquelle on créée cet affichage
	 * @return Un VBox qui contient en ordre l'image de l'espece et un bouton qui
	 *         pointe vers cette espèce
	 */
	private VBox especeVBox(Espece espece) {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(new ImageView() {
			{
				setFitWidth(60);
				setFitHeight(60);
				setImage(new Image((new File(espece.getCheminImageDisque())).toURI().toString()));
			}
		}, boutonEspece(espece));
		vbox.setCenterShape(true);
		return vbox;
	}

	/**
	 * Méthode privée pour créer un bouton qui pointe vers une espece.
	 * 
	 * @param espece L'espèce en question
	 * @return Un Button qui pointe vers cette espèce et ouvre sa fenêtre de
	 *         description
	 */
	private Button boutonEspece(Espece espece) {
		Button b = new Button(espece.getNom());
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * On passe l'espèce du bouton à la fenetre d'affichage de cette espece Puis on
				 * passe à cette fenetre
				 */
				CreatureDisplayController.setEspece(espece);
				TrucsUtiles.changeStage(event, "/fxml/CreatureDisplay.fxml", this, true);
			}
		});
		return b;
	}
}
