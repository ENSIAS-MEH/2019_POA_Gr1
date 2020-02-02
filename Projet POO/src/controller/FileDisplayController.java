package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.TrucsUtiles;

public class FileDisplayController implements Initializable {

	@FXML
	private MenuBar menuBar;

	@FXML
	private HBox hbox_0, hbox_1, hbox_2, hbox_3;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hbox_0.setId("hbox_0");
		hbox_1.setId("hbox_1");
		hbox_2.setId("hbox_2");
		hbox_3.setId("hbox_3");
		/*
		 * On récupère les espèces depuis le csv
		 */
		recupererResultat((TrucsUtiles.getDAO().recupererToutes()));

	}

	private ArrayList<CheckBox> CheckBoxList = new ArrayList<CheckBox>();

	/**
	 * Méthode pour l'option dans la barre de menu>Fichier>Ouvrir un fichier On
	 * change le fichier csv, et on réaffiche
	 * 
	 * @param event
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
			popUp.initModality(Modality.APPLICATION_MODAL);
			popUp.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse à la premiere fenetre de
	 * l'application
	 * 
	 * @param event
	 */
	@FXML
	private void fermerFichierHandler(ActionEvent event) {
		TrucsUtiles.setCsvNull();
		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this, true);
	}

	/**
	 * Méthode pour l'option dans la barre de menu>Help>Rechercher espèces On passe
	 * à la fenetre de filtrage
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this, true);
	}

	public void recupererResultat(ArrayList<Espece> especes) {

		hbox_0.getChildren().clear();
		hbox_1.getChildren().clear();
		hbox_2.getChildren().clear();
		hbox_3.getChildren().clear();

		for (Espece espece : especes) {
			if (espece != null) {
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
	}

	/**
	 * Méthode privée pour créer l'affichage d'une seule espèces, son image en haut
	 * et son nom en bas
	 * 
	 * @param espece
	 * @return
	 */
	private VBox especeVBox(Espece espece) {
		HBox hbox = new HBox();
		hbox.setCenterShape(true);
		hbox.getChildren().addAll(boutonEspece(espece), new CheckBox() {
			{
				CheckBoxList.add(this);
			}
		});

		VBox vbox = new VBox();
		vbox.getChildren().addAll(new ImageView() {
			{
				setFitWidth(60);
				setFitHeight(60);
				setImage(new Image((new File(espece.getCheminImageDisque())).toURI().toString()));
			}
		}, hbox);

		vbox.setCenterShape(true);

		return vbox;
	}

	/**
	 * Méthode privée pour créer un bouton qui pointe vers une espece
	 * 
	 * @param e
	 * @return
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

	/**
	 * Méthode pour passer à la fenetre d'ajout d'une espece dans le fichier
	 * 
	 * @param event
	 */
	@FXML
	private void ajouterEspeceHandler(ActionEvent event) {
		Stage ajoutStage = null;
		try {
			ajoutStage = new Stage() {
				{
					setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/AjouterEspece.fxml"))));
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
		}

		ajoutStage.initOwner(TrucsUtiles.getStage(event));
		ajoutStage.initModality(Modality.APPLICATION_MODAL);
		ajoutStage.setResizable(false);
		ajoutStage.showAndWait();

		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this, false);
	}

	/**
	 * Méthode pour supprimer des especes. Elle doivent être selectionnées deja par
	 * les checkbox
	 * 
	 * @param event
	 */
	@FXML
	private void supprimerEspecesHandler(ActionEvent event) {

		boolean selected = false;

		Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
		alertConfirmation.setHeaderText("Voulez vous supprimer les créatures selectionnées ?");
		Optional<ButtonType> result = alertConfirmation.showAndWait();

		if (result.get() == ButtonType.OK) {

			// On mémorisera ici les CheckBox à enlever plus tard
			ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

			// On sauvegarde la liste d'origine pour localiser les indices
			ArrayList<Espece> list = TrucsUtiles.getDAO().recupererToutes();

			for (CheckBox cb : CheckBoxList) {
				if (cb.isSelected()) {
					// On enlève l'espece du DAO

					TrucsUtiles.getDAO().supprimer(((list.get(CheckBoxList.indexOf(cb)))).getId());

					// On enregistre les index des CheckBox
					checkBoxes.add(cb);
					selected = true;

				}
			}
			// On retire les CheckBox enregistrés à la fin

			if (!checkBoxes.isEmpty()) {
				for (CheckBox cb : checkBoxes) {
					CheckBoxList.remove(cb);
				}
			}

			if (!selected) {
				Alert alertSucces = new Alert(AlertType.INFORMATION);
				alertSucces.setHeaderText("Aucune créature n'est choisie");
				alertSucces.showAndWait();
			} else {
				TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this, false);
			}
		}
	}

	@FXML
	private void enregistrerHandler(ActionEvent event) {
		Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
		alertConfirmation.setHeaderText("Voulez vous supprimer les créatures selectionnées ?");
		Optional<ButtonType> result = alertConfirmation.showAndWait();

		if (result.get() == ButtonType.OK) {
			TrucsUtiles.getDAO().enregistrerModifications();
		}

	}
}
