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

/**
 * Controlleur de la fen�tre d'affichage des esp�ces.<br>
 * On y affichera les esp�ces, reparties par niveaux ( de 0 � 3, voir
 * {@link dao.Espece#getIntervalleZone()}. L'affichage correspond � une
 * miniature de l'esp�ce, un bouton qui permet de changer la fen�tre vers
 * l'affichage de cette esp�ce ( {@link controller.CreatureDisplayController} et
 * un CheckBox o� on peut choisir les esp�ces qu'on souhaite supprimer.
 * 
 *
 */
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
		 * On r�cup�re les esp�ces depuis le csv
		 */
		recupererResultat((TrucsUtiles.getDAO().recupererToutes()));

	}

	private ArrayList<CheckBox> CheckBoxList = new ArrayList<CheckBox>();

	/**
	 * M�thode pour l'option dans la barre de menu > Fichier > Ouvrir un
	 * fichier.<br>
	 * On change le fichier csv, et on r�affiche.<br>
	 * Elle se d�clenche en cliquant sur le bouton y associ�.
	 * 
	 * @param event Evenement de clic sur le bouton
	 * @see {@link main.TrucsUtiles#setCsv(Object, String, Object)
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
			popUp.initModality(Modality.APPLICATION_MODAL);
			popUp.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse � la premiere fenetre de
	 * l'application<br>
	 * Elle se d�clenche en cliquant sur le bouton y associ�.
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
	 * M�thode pour l'option dans la barre de menu > Help > Rechercher esp�ces On
	 * passe � la fenetre de filtrage<br>
	 * Elle se d�clenche en cliquant sur le bouton y associ�
	 * 
	 * @param event Evenement de clic sur le bouton
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this, true);
	}

	/**
	 * M�thode pour initialiser afficher les esp�ces.<br>
	 * Elles seront reparties selon des niveaux de 0 � 3, chaque niveau correspond �
	 * un HBox.<br>
	 * Les esp�ces seront charg�es � partir du DAO.
	 * 
	 * @param especes ArrayList d'esp�ces, il correspondra � la valeur retourn�e par
	 *                la m�thode {@link dao.EspeceDAO#recupererToutes()}.
	 */
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
	 * M�thode priv�e pour cr�er l'affichage d'une seule esp�ce.
	 * 
	 * @param espece L'esp�ce pour laquelle on cr��e cet affichage
	 * @return Un VBox qui contient en ordre l'image de l'espece, un bouton qui
	 *         pointe vers cette esp�ce et un CheckBox pour la choisir au cas o�
	 *         l'utilisateur voudrait la supprimer
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
	 * M�thode priv�e pour cr�er un bouton qui pointe vers une espece.
	 * 
	 * @param espece L'esp�ce en question
	 * @return Un Button qui pointe vers cette esp�ce et ouvre sa fen�tre de
	 *         description
	 */
	private Button boutonEspece(Espece espece) {
		Button b = new Button(espece.getNom());
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * On passe l'esp�ce du bouton � la fenetre d'affichage de cette espece Puis on
				 * passe � cette fenetre
				 */
				CreatureDisplayController.setEspece(espece);
				TrucsUtiles.changeStage(event, "/fxml/CreatureDisplay.fxml", this, true);
			}
		});
		return b;
	}

	/**
	 * M�thode pour passer � la fenetre d'ajout d'une espece dans le fichier.<br>
	 * Elle sera invoqu�e par le bouton d'ajout.
	 * 
	 * @param event Evenement de clic sur le bouton.
	 * @see {@link controller.AjouterEspeceController}
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
	 * M�thode pour supprimer des especes. Elle doivent �tre selectionn�es deja par
	 * le biais de leur CheckBox. Elle sera invoqu�e par le bouton de suppression.
	 * 
	 * @param event Evenement de clic sur le bouton
	 */
	@FXML
	private void supprimerEspecesHandler(ActionEvent event) {

		boolean selected = false;

		Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
		alertConfirmation.setHeaderText("Voulez vous supprimer les cr�atures selectionn�es ?");
		Optional<ButtonType> result = alertConfirmation.showAndWait();

		if (result.get() == ButtonType.OK) {

			// On m�morisera ici les CheckBox � enlever plus tard
			ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

			// On sauvegarde la liste d'origine pour localiser les indices
			ArrayList<Espece> list = TrucsUtiles.getDAO().recupererToutes();

			for (CheckBox cb : CheckBoxList) {
				if (cb.isSelected()) {
					// On enl�ve l'espece du DAO

					TrucsUtiles.getDAO().supprimer(((list.get(CheckBoxList.indexOf(cb)))).getId());

					// On enregistre les index des CheckBox
					checkBoxes.add(cb);
					selected = true;

				}
			}
			// On retire les CheckBox enregistr�s � la fin

			if (!checkBoxes.isEmpty()) {
				for (CheckBox cb : checkBoxes) {
					CheckBoxList.remove(cb);
				}
			}

			if (!selected) {
				Alert alertSucces = new Alert(AlertType.INFORMATION);
				alertSucces.setHeaderText("Aucune cr�ature n'est choisie");
				alertSucces.showAndWait();
			} else {
				TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this, false);
			}
		}
	}

	/**
	 * M�thode pour enregistrer les modifications apport�s au fichier d'origine. Les
	 * ajouts et suppressions ne prennent pas effet permanent tant que
	 * l'enregistrement n'est pas effectu�. Le fichier d'origine sera modifi�.
	 * 
	 * @param event Evenement de clic sur le bouton d'enregistrement
	 */
	@FXML
	private void enregistrerHandler(ActionEvent event) {
		Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
		alertConfirmation.setHeaderText("Voulez vous enregistrer le fichier ? Cette op�ration est pas irreversible");
		Optional<ButtonType> result = alertConfirmation.showAndWait();

		if (result.get() == ButtonType.OK) {
			TrucsUtiles.getDAO().enregistrerModifications();
		}

	}
}
