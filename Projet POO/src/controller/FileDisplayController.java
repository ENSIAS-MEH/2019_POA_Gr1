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

	@FXML
	private Button ajouterEspece;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hbox_0.setId("hbox_0");
		hbox_1.setId("hbox_1");
		hbox_2.setId("hbox_2");
		hbox_3.setId("hbox_3");
		/*
		 * On récupère les espèces depuis le csv
		 */
		recupererResultat(TrucsUtiles.getListEspeces());

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
			try {
				popUp.initOwner(TrucsUtiles.getStage(event));
				popUp.initModality(Modality.APPLICATION_MODAL);
				popUp.showAndWait();
			} catch (Exception e) {
				System.out.println("Une exception qui me résout un problème, pas mal");
			}
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
		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this);
	}

	/**
	 * Méthode pour l'option dans la barre de menu>Help>Rechercher espèces On passe
	 * à la fenetre de filtrage
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this);
	}

	public void recupererResultat(ArrayList<Espece> especes) {

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
				TrucsUtiles.changeStage(event, "/fxml/CreatureDisplay.fxml", this);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ajoutStage.initOwner(TrucsUtiles.getStage(event));
		ajoutStage.initModality(Modality.APPLICATION_MODAL);
		ajoutStage.setResizable(false);
		ajoutStage.showAndWait();
	}

	/**
	 * Méthode pour supprimer des especes. Elle doivent être selectionnées deja par
	 * les checkbox
	 * 
	 * @param event
	 */
	@FXML
	private void supprimerEspecesHandler(ActionEvent event) {
		for (CheckBox cb : CheckBoxList) {
			if (cb.isSelected()) {
				TrucsUtiles.getDAO()
						.supprimer((((TrucsUtiles.getListEspeces()).get(CheckBoxList.indexOf(cb)))).getId());
			}
		}
		TrucsUtiles.getDAO().enregistrerModifications();
		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this);
	}
}
