package main;

import java.util.ArrayList;

import controller.CreatureDisplayController;
import dao.Espece;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Classe abstraite o� on met 2 m�thodes pour l'affichage d'une liste d'esp�ces
 * Elle sera m�re de tout controlleur qui affiche des esp�ces
 * 
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
//	public void recupererResultat(ArrayList<Espece> especes, VBox vbox) {
//		vbox.getChildren().clear();
//		for (Espece espece : especes) {
//			vbox.getChildren().add(especeRow(espece));
//		}
//	}

	/**
	 * M�thode pour generer un affichage horizontal pour une espece donn�e Cet
	 * affichage sera ensuite mis dans une box verticale
	 * 
	 * @param espece
	 * @return
	 */

	public HBox especeRow(Espece espece) {
		HBox row = new HBox();
		row.getChildren().addAll(boutonEspece(espece), new Label(espece.getDescription()));
		return row;
	}

	/**
	 * M�thode priv�e pour cr�er un bouton qui pointe vers une espece
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
				 * On passe l'esp�ce du bouton � la fenetre d'affichage de cette espece Puis on
				 * passe � cette fenetre
				 */
				CreatureDisplayController.setEspece(espece);
				TrucsUtiles.changeStage(event, "/fxml/CreatureDisplay.fxml", this);
			}
		});
		return b;
	}
}
