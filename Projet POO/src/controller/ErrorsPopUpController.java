package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import main.TrucsUtiles;
/**
 * Controlleur pour une fenetre de pop afin de notifier que le fichier a bien été récupéré
 * En cas d'erreur au niveau des espèces ( champs qui ne respectent pas certains critères )
 * Ces espèces seront affichées également dans ce pop up
 * @author Amine
 *
 */
public class ErrorsPopUpController implements Initializable{
	
	@FXML
	private TextArea textArea;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		// On affiche les erreurs à partir du DAO
		recupererErreurs(TrucsUtiles.getDAO().recupererErreurs());
	}
	
	/**
	 * Méthode pour récuperer les erreurs et les écrire dans le champs de texte de la fenetre
	 * @param tableauErreurs Tableau d'erreurs qui proviendra de la lecture des espèces
	 */
	private void recupererErreurs(ArrayList<String> tableauErreurs) {
		for(String e : tableauErreurs)
			textArea.setText(e+"\n");
		return;
	}
}
