package main;

import java.util.ArrayList;

import dao.Espece;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Classe abstraite où on met 2 méthodes pour l'affichage d'une liste d'espèces
 * Elle sera mère de tout controlleur qui affiche des espèces
 * @author Amine
 *
 */
public abstract class Display {
	/**
	 * Méthode pour ajouter les especes d'une façon verticale
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
	 * Méthode pour generer un affichage horizontal pour une espece donnée Cet
	 * affichage sera ensuite mis dans une box verticale
	 * 
	 * @param espece
	 * @return
	 */
	public HBox especeRow(Espece espece) {
		HBox row = new HBox();
		row.getChildren().addAll(new Button(espece.getNom()), new Label(espece.getDescription()));
		return row;
	}
}
