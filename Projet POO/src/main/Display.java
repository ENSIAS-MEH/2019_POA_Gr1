package main;

import java.io.IOException;
import java.util.ArrayList;

import controller.CreatureDisplayController;
import dao.Espece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe abstraite o� on met 2 m�thodes pour l'affichage d'une liste d'esp�ces
 * Elle sera m�re de tout controlleur qui affiche des esp�ces
 * @author Amine
 *
 */
public abstract class Display {
	
//	// On retient la barre de menu dans cette fenetre
//	@FXML
//	protected MenuBar menuBar;
	
	/**
	 * M�thode pour ajouter les especes d'une fa�on verticale
	 * 
	 * @param especes
	 * @param vbox
	 */
	public void recupererResultat(ArrayList<Espece> especes, VBox vbox) {
		for (Espece espece : especes) {
			vbox.getChildren().add(especeRow(espece));
		}
	}

	/**
	 * M�thode pour generer un affichage horizontal pour une espece donn�e Cet
	 * affichage sera ensuite mis dans une box verticale
	 * 
	 * @param espece
	 * @return
	 */
	
	public HBox especeRow(Espece espece) {
		HBox row = new HBox();	
		// FIXME faut donner au bouton la fonction de passer a l'affichage de l'espece
		row.getChildren().addAll(boutonEspece(espece), new Label(espece.getDescription()));
		return row;
	}
	
	/**
	 * M�thode priv�e pour cr�er un bouton qui pointe vers une espece
	 * @param e
	 * @return
	 */
	private Button boutonEspece(Espece espece) {
		Button b = new Button(espece.getNom());
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * On passe l'esp�ce du bouton � la fenetre d'affichage de cette espece
				 * Puis on passe � cette fenetre
				 */
				CreatureDisplayController.setEspece(espece);
				TrucsUtiles.changeStage(event, "/fxml/CreatureDisplay.fxml", this);				
			}
		});
		return b;
	}
	
	// TODO 7yd had zbl
//	/**
//	 * M�thode pour l'option dans la barre de menu>Fichier>Ouvrir un fichier On
//	 * change le fichier csv, et on r�affiche
//	 * 
//	 * @param event
//	 */
//	@FXML
//	protected void ouvrirFichierHandler(ActionEvent event) {
//		try {
//			/*
//			 * Possibilit� de choisir de choisir un autre fichier, On refresh la scene
//			 * courante utilisant le m�me fichier fxml
//			 */
//			TrucsUtiles.setCsv(menuBar, "/fxml/FileDisplay.fxml", this);
//
//			/*
//			 * On affiche un pop qui indique que le fichier a bien �t� r�cup�r� Et affiche
//			 * les erreurs dans ce fichier s'il y'en a
//			 */
//			Stage popUp = new Stage();
//			popUp.setTitle("Succ�s");
//			Parent popUpRoot = FXMLLoader.load(getClass().getResource("/fxml/ErrorsPopUp.fxml"));
//			Scene scene = new Scene(popUpRoot);
//			popUp.setScene(scene);
//			popUp.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * M�thode pour l'option dans la barre de menu>Fichier>Fermer le fichier
//	 * On remet le fichier LeCsv en null On repasse � la premiere fenetre de
//	 * l'application
//	 * @param event
//	 */
//	@FXML
//	protected void fermerFichierHandler(ActionEvent event) {
//		TrucsUtiles.setCsvNull();
//		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this);
//	}
//	
//	/**
//	 * M�thode pour l'option dans la barre de menu>Help>Rechercher esp�ces
//	 * On passe � la fenetre de filtrage
//	 * @param event
//	 */
//	@FXML
//	protected void rechercherEspeceHandler(ActionEvent event) {
//		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this);
//	}
}
