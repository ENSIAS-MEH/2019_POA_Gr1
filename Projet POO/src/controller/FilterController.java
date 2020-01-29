package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
	private RadioButton nom, famille, classe, genre, description, ordre, embranchement, categorie, tous;

	@FXML
	private ChoiceBox<String> groupeTrophiqueChoiceBox, groupeEcologiqueChoiceBox;

	@FXML
	private MenuBar menuBar;

	// On regroupe les boutons dans un tableau afin de determiner lequel est
	// selectionné
	private RadioButton[] buttonsList;

	static ArrayList<Espece> resultRecherche;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		resultRecherche = null;
		buttonsList = new RadioButton[] { nom, famille, classe, genre, description, ordre, embranchement, categorie,
				tous };

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

		// On récupère le RadioButton choisis ( champ dans laquel chercher )
		String boutonChoisis = fetchClickedRadioButton().getId();

		// On récupère les groupes ecologiques et trophiques choisis
		String groupeEcologiqueChoisi = groupeEcologiqueChoiceBox.getValue().toString();
		String groupeTrophiqueChoisi = groupeTrophiqueChoiceBox.getValue().toString();

		resultRecherche = (TrucsUtiles.getDAO()).filtrer(saisie, boutonChoisis, groupeEcologiqueChoisi,
				groupeTrophiqueChoisi);

		TrucsUtiles.changeStage(event, "/fxml/ResultDisplay.fxml", this);

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

		// On initialise une liste des groupes écologiques
		ArrayList<String> groupesEcologiquesArray = new ArrayList<String>();

		// qu'on remplit à partir de celle dans la classe Espece
		for (String s : Espece.getListeGroupeEcologique())
			groupesEcologiquesArray.add(s);
		groupesEcologiquesArray.add("tous"); // On ajoute le tous

		// et on adapte cette liste au CheckBox
		ObservableList<String> groupesEcologiquesList = FXCollections.observableArrayList(groupesEcologiquesArray);
		groupeEcologiqueChoiceBox.setItems(groupesEcologiquesList);
		groupeEcologiqueChoiceBox.getSelectionModel().selectFirst();

		// Meme manip qu'en haut, pour les groupes trophiques
		ArrayList<String> groupesTrophiquesArray = new ArrayList<String>();

		for (String s : Espece.getListeGroupeTrophique())
			groupesTrophiquesArray.add(s);
		groupesTrophiquesArray.add("tous");

		ObservableList<String> groupesTrophiquesList = FXCollections.observableArrayList(groupesTrophiquesArray);
		groupeTrophiqueChoiceBox.setItems(groupesTrophiquesList);
		groupeTrophiqueChoiceBox.getSelectionModel().selectFirst();

	}

	/**
	 * Méthode pour l'option dans la barre de menu>Fichier>Ouvrir un fichier On
	 * change le fichier csv, et on réaffiche
	 * 
	 * @param event
	 */
	@FXML
	private void ouvrirFichierHandler(ActionEvent event) {
		try {
			/*
			 * Possibilité de choisir de choisir un autre fichier, On refresh la scene
			 * courante utilisant le même fichier fxml
			 */
			TrucsUtiles.setCsv(menuBar, "/fxml/FileDisplay.fxml", this);

			/*
			 * On affiche un pop qui indique que le fichier a bien été récupéré Et affiche
			 * les erreurs dans ce fichier s'il y'en a
			 */
			Stage popUp = new Stage();
			popUp.setTitle("Succès");
			Parent popUpRoot = FXMLLoader.load(getClass().getResource("/fxml/ErrorsPopUp.fxml"));
			Scene scene = new Scene(popUpRoot);
			popUp.setScene(scene);
			popUp.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse à la premiere fenetre de
	 * l'application
	 * 
	 * @param event
	 */
	@FXML
	private void fermerFichierHandler(ActionEvent event) {
		TrucsUtiles.setCsvNull();
		TrucsUtiles.changeStage(menuBar, "/fxml/Menu.fxml", this);
	}

	/**
	 * Méthode pour l'option dans la barre de menu>Help>Rechercher espèces On passe
	 * à la fenetre de filtrage
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this);
	}

	/**
	 * Méthode pour revenir à l'affichage premier du fichier
	 * 
	 * @param event
	 */
	@FXML
	private void retourButtonHandler(ActionEvent event) {
		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this);
	}
}
