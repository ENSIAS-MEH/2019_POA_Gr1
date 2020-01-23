package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.CSVEspeceDAO;
import dao.Espece;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.Main;
import main.TrucsUtiles;

/**
 * Cette classe est le controlleur de la fenetre de recherche/filtrage,
 * L'utilisateur devrait saisir du texte dans un champ dedi�, et moyennant
 * quelques filtres avec des boutons, il devrait obtenir les r�sultats les plus
 * adapt�s � ces filtres.
 * 
 * @author Amine
 *
 */
public class FilterController implements Initializable {

	@FXML
	private TextField searchTextField;

	@FXML
	private RadioButton noms, familles, classes, genres, descriptions, ordres, embranchements, tous;

	@FXML
	private ChoiceBox<String> groupeTrophiqueChoiceBox, groupeEcologiqueChoiceBox;

	// On regroupe les boutons dans un tableau afin de determiner lequel est
	// selectionn�
	private RadioButton[] buttonsList = { noms, familles, classes, genres, descriptions, ordres, embranchements, tous };
	
	static ArrayList<Espece> resultRecherche;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		resultRecherche=null;
		
		groupeTrophiqueChoiceBox.setId("groupeTrophiqueChoiceBox");
		groupeEcologiqueChoiceBox.setId("groupeEcologiqueChoiceBox");

		initializeGroupesTrophiques_Ecologiques();

	}

	/**
	 * M�thode qui se d�clenche pour effectuer la recherche
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherButtonHandler(ActionEvent event) {
		// On r�cup�re ce qui est dans la zone de saisie
		String saisie = searchTextField.getText();

		// On r�cup�re le RadioButton choisis ( cat�gorie dans laquelle chercher )
		String boutonChoisis = fetchClickedRadioButton().getText();

		// On r�cup�re les groupes ecologiques et trophiques choisis
		String groupeEcologiqueChoisi = groupeEcologiqueChoiceBox.getValue().toString();
		String groupeTrophiqueChoisi = groupeTrophiqueChoiceBox.getValue().toString();

		resultRecherche = (TrucsUtiles.getDAO()).filtrer(saisie, boutonChoisis,
				groupeEcologiqueChoisi, groupeTrophiqueChoisi);

		// TODO passer � la fenetre d'affichage avec un nouveau fichier csv qui
		// correspond au resultat du filtrage ?
		
	}

	/**
	 * M�thode pour r�cuperer le nom du champs choisis
	 * 
	 * @return Elle renvoie le nom du champs dans lequel on souhaite effectuer la
	 *         recherche sous forme de string
	 */
	private RadioButton fetchClickedRadioButton() {
		RadioButton resultButton = null;
		for (RadioButton button : buttonsList)
			if (button.isSelected())
				resultButton = button;
		return resultButton;
	}

	/**
	 * M�thode pour initialiser les groupes trophiques et �cologiques d'apr�s les
	 * tableaux des groupes fournits dans la classe Espece
	 */
	private void initializeGroupesTrophiques_Ecologiques() {

		ArrayList<String> groupesEcologiquesArray = new ArrayList<String>();

		for (String s : Espece.getListeGroupeEcologique())
			groupesEcologiquesArray.add(s);

		ObservableList<String> groupesEcologiquesList = FXCollections.observableArrayList(groupesEcologiquesArray);

		groupeEcologiqueChoiceBox.setItems(groupesEcologiquesList);

		groupeEcologiqueChoiceBox.getSelectionModel().selectFirst();

		ArrayList<String> groupesTrophiquesArray = new ArrayList<String>();

		for (String s : Espece.getListeGroupeTrophique())
			groupesTrophiquesArray.add(s);

		ObservableList<String> groupesTrophiquesList = FXCollections.observableArrayList(groupesTrophiquesArray);

		groupeTrophiqueChoiceBox.setItems(groupesTrophiquesList);

		groupeTrophiqueChoiceBox.getSelectionModel().selectFirst();

	}
}
