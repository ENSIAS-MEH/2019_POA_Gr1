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
	private RadioButton nom, famille, classe, genre, description, ordre, embranchement, categorie, tous;

	@FXML
	private ChoiceBox<String> groupeTrophiqueChoiceBox, groupeEcologiqueChoiceBox;

	@FXML
	private MenuBar menuBar;

	// On regroupe les boutons dans un tableau afin de determiner lequel est
	// selectionn�
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
	 * M�thode qui se d�clenche pour effectuer la recherche
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherButtonHandler(ActionEvent event) {
		// On r�cup�re ce qui est dans la zone de saisie
		String saisie = searchTextField.getText();

		// On r�cup�re le RadioButton choisis ( champ dans laquel chercher )
		String boutonChoisis = fetchClickedRadioButton().getId();

		// On r�cup�re les groupes ecologiques et trophiques choisis
		String groupeEcologiqueChoisi = groupeEcologiqueChoiceBox.getValue().toString();
		String groupeTrophiqueChoisi = groupeTrophiqueChoiceBox.getValue().toString();

		resultRecherche = (TrucsUtiles.getDAO()).filtrer(saisie, boutonChoisis, groupeEcologiqueChoisi,
				groupeTrophiqueChoisi);

		TrucsUtiles.changeStage(event, "/fxml/ResultDisplay.fxml", this);

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

		// On initialise une liste des groupes �cologiques
		ArrayList<String> groupesEcologiquesArray = new ArrayList<String>();

		// qu'on remplit � partir de celle dans la classe Espece
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
	 * M�thode pour l'option dans la barre de menu>Fichier>Ouvrir un fichier On
	 * change le fichier csv, et on r�affiche
	 * 
	 * @param event
	 */
	@FXML
	private void ouvrirFichierHandler(ActionEvent event) {
		try {
			/*
			 * Possibilit� de choisir de choisir un autre fichier, On refresh la scene
			 * courante utilisant le m�me fichier fxml
			 */
			TrucsUtiles.setCsv(menuBar, "/fxml/FileDisplay.fxml", this);

			/*
			 * On affiche un pop qui indique que le fichier a bien �t� r�cup�r� Et affiche
			 * les erreurs dans ce fichier s'il y'en a
			 */
			Stage popUp = new Stage();
			popUp.setTitle("Succ�s");
			Parent popUpRoot = FXMLLoader.load(getClass().getResource("/fxml/ErrorsPopUp.fxml"));
			Scene scene = new Scene(popUpRoot);
			popUp.setScene(scene);
			popUp.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�thode pour l'option dans la barre de menu>Fichier>Fermer le fichier On
	 * remet le fichier LeCsv en null On repasse � la premiere fenetre de
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
	 * M�thode pour l'option dans la barre de menu>Help>Rechercher esp�ces On passe
	 * � la fenetre de filtrage
	 * 
	 * @param event
	 */
	@FXML
	private void rechercherEspeceHandler(ActionEvent event) {
		TrucsUtiles.changeStage(menuBar, "/fxml/Filter.fxml", this);
	}

	/**
	 * M�thode pour revenir � l'affichage premier du fichier
	 * 
	 * @param event
	 */
	@FXML
	private void retourButtonHandler(ActionEvent event) {
		TrucsUtiles.changeStage(event, "/fxml/FileDisplay.fxml", this);
	}
}
