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
 * Controlleur de la fen�tre de resultats du filtrage<br>
 * L'affichage est similaire � l'affichage de base du fichier, sans possibilit�
 * d'ajout ou de suppression parcontre.<br>
 * On peut retourner � l'affichage de base du fichier par un bouton d�di�.
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
		// On ajoute r�cup�re les esp�ces filtr�es
		recupererResultat(FilterController.resultRecherche);
	}

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
	 * M�thode pour revenir � l'affichage du fichier.
	 * 
	 * @param event Evenement de clic sur le bouton
	 */
	@FXML
	private void retourButtonHandler(ActionEvent event) {
		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this, true);
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
	 * M�thode priv�e pour cr�er l'affichage d'une seule esp�ce.
	 * 
	 * @param espece L'esp�ce pour laquelle on cr��e cet affichage
	 * @return Un VBox qui contient en ordre l'image de l'espece et un bouton qui
	 *         pointe vers cette esp�ce
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
}
