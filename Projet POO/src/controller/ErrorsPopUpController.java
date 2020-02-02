package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import main.TrucsUtiles;

/**
 * Controlleur pour une fenetre de pop afin de notifier que le fichier a bien
 * �t� r�cup�r�.<br>
 * En cas d'erreur au niveau des esp�ces ( champs qui ne respectent pas certains
 * crit�res ), elles seront affich�s avec une indication de l'emplacement de
 * l'erreur.
 * 
 */
public class ErrorsPopUpController implements Initializable {

	@FXML
	private TextArea textArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// On affiche les erreurs � partir du DAO
		recupererErreurs(TrucsUtiles.getDAO().recupererErreurs());
	}

	/**
	 * M�thode pour r�cuperer les erreurs et les �crire dans le champs de texte de
	 * la fenetre.<br>
	 * Les erreurs seront r�cup�r�s par la m�thode
	 * {@link dao.EspeceDAO#recupererErreurs()}, ceci juste apr�s la connexion au
	 * fichier.
	 * 
	 * @param tableauErreurs Tableau d'erreurs qui proviendra de la lecture des
	 *                       esp�ces
	 */
	private void recupererErreurs(ArrayList<String> tableauErreurs) {
		for (String e : tableauErreurs)
			textArea.setText(textArea.getText() + e + "\n");
		return;
	}
}
