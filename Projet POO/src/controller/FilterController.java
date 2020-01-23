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
 * L'utilisateur devrait saisir du texte dans un champ dedié, et moyennant
 * quelques filtres avec des boutons, il devrait obtenir les résultats les plus
 * adaptés à ces filtres.
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
	// selectionné
	private RadioButton[] buttonsList = { noms, familles, classes, genres, descriptions, ordres, embranchements, tous };

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		searchTextField.setId("searchTextField");

		noms.setId("noms");
		familles.setId("familles");
		classes.setId("classes");
		genres.setId("genres");
		descriptions.setId("descriptions");
		ordres.setId("ordres");
		embranchements.setId("embranchements");
		tous.setId("tous");
		;

		groupeTrophiqueChoiceBox.setId("groupeTrophiqueChoiceBox");
		groupeEcologiqueChoiceBox.setId("groupeEcologiqueChoiceBox");

		initializeGroupesTrophiques_Ecologiques();

	}

	/**
	 * Méthode qui se déclenche pour effectuer la recherche
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherButtonHandler(ActionEvent event) {
		// On récupère ce qui est dans la zone de saisie
		String saisie = searchTextField.getText();

		// On récupère le RadioButton choisis ( catégorie dans laquelle chercher )
		String boutonChoisis = fetchClickedRadioButton().getText();

		// On récupère les groupes ecologiques et trophiques choisis
		String groupeEcologiqueChoisi = groupeEcologiqueChoiceBox.getValue().toString();
		String groupeTrophiqueChoisi = groupeTrophiqueChoiceBox.getValue().toString();

		ArrayList<Espece> resultRecherche = (TrucsUtiles.getDAO()).filtrer(saisie, boutonChoisis,
				groupeEcologiqueChoisi, groupeTrophiqueChoisi);

		// TODO passer à la fenetre d'affichage avec un nouveau fichier csv qui
		// correspond au resultat du filtrage ?
		
		
	}

	/**
	 * Méthode pour récuperer le nom du champs choisis
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
	 * Méthode pour initialiser les groupes trophiques et écologiques d'après les
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
