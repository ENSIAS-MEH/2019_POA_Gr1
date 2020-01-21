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
		String groupeTrophiquesChoisi = groupeTrophiqueChoiceBox.getValue().toString();

		// TODO La méthode de filtrage
		ArrayList<Espece> resultRecherche = CSVEspeceDAO.this.filtrer();

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
	 * Méthode pour initialiser les groupes trophiques et écologiques
	 */
	private void initializeGroupesTrophiques_Ecologiques() {

		ArrayList<String> groupesEcologiquesArray = new ArrayList<String>() {
			{
				add("Tous");
				add("Espèces sensibles");
				add("Espèces indifférentes");
				add("Espèces tolérantes");
				add("Espèces opportunistes de deuxième ordre");
				add("Espèces opportunistes de premier ordre");
			}
		};

		ObservableList<String> groupesEcologiquesList = FXCollections.observableArrayList(groupesEcologiquesArray);

		groupeEcologiqueChoiceBox.setItems(groupesEcologiquesList);

		groupeEcologiqueChoiceBox.getSelectionModel().selectFirst();

		ArrayList<String> groupesTrophiquesArray = new ArrayList<String>() {
			{
				add("Tous");
				add("Carnivores");
				add("Nécrophages");
				add("Herbivores");
				add("Détritivores");
				add("Suspensivores");
				add("Déposivores");
				add("Déposivores non sélectifs");
				add("Microbrouteurs");
			}
		};

		ObservableList<String> groupesTrophiquesList = FXCollections.observableArrayList(groupesTrophiquesArray);

		groupeTrophiqueChoiceBox.setItems(groupesTrophiquesList);

		groupeTrophiqueChoiceBox.getSelectionModel().selectFirst();

	}
}
