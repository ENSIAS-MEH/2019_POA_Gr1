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
		String groupeTrophiquesChoisi = groupeTrophiqueChoiceBox.getValue().toString();

		// TODO La m�thode de filtrage
		ArrayList<Espece> resultRecherche = CSVEspeceDAO.this.filtrer();

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
	 * M�thode pour initialiser les groupes trophiques et �cologiques
	 */
	private void initializeGroupesTrophiques_Ecologiques() {

		ArrayList<String> groupesEcologiquesArray = new ArrayList<String>() {
			{
				add("Tous");
				add("Esp�ces sensibles");
				add("Esp�ces indiff�rentes");
				add("Esp�ces tol�rantes");
				add("Esp�ces opportunistes de deuxi�me ordre");
				add("Esp�ces opportunistes de premier ordre");
			}
		};

		ObservableList<String> groupesEcologiquesList = FXCollections.observableArrayList(groupesEcologiquesArray);

		groupeEcologiqueChoiceBox.setItems(groupesEcologiquesList);

		groupeEcologiqueChoiceBox.getSelectionModel().selectFirst();

		ArrayList<String> groupesTrophiquesArray = new ArrayList<String>() {
			{
				add("Tous");
				add("Carnivores");
				add("N�crophages");
				add("Herbivores");
				add("D�tritivores");
				add("Suspensivores");
				add("D�posivores");
				add("D�posivores non s�lectifs");
				add("Microbrouteurs");
			}
		};

		ObservableList<String> groupesTrophiquesList = FXCollections.observableArrayList(groupesTrophiquesArray);

		groupeTrophiqueChoiceBox.setItems(groupesTrophiquesList);

		groupeTrophiqueChoiceBox.getSelectionModel().selectFirst();

	}
}
