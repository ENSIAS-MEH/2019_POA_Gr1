package main;

import java.util.ArrayList;

import dao.Espece;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Classe abstraite o� on met 2 m�thodes pour l'affichage d'une liste d'esp�ces
 * Elle sera m�re de tout controlleur qui affiche des esp�ces
 * @author Amine
 *
 */
public abstract class Display {
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
		row.getChildren().addAll(new Button(espece.getNom()), new Label(espece.getDescription()));
		return row;
	}
}
