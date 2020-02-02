package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.TrucsUtiles;

/**
 * Cette classe est le controlleur de la fenêtre d'ajout d'espèce. L'utilisateur
 * doit obligatoirement saisir un nom de l'espèce et un lien valide d'une image
 * sur internet.
 * 
 *
 */
public class AjouterEspeceController implements Initializable {

	@FXML
	private TextField nom, genre, famille, ordre, classe, embranchement, cheminImage, description, categorie;

	@FXML
	private ChoiceBox<String> zone, groupeTrophique, groupeEcologique;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeChoiceBoxes();
	}

	/**
	 * Méthode pour ajouter une espece dans la base de données par le biais de la
	 * méthode ajouter dans du DAO.<br>
	 * Elle se déclenche en cliquant sur le bouton y associé.
	 * 
	 * @param event Evenement de clic sur le bouton
	 * @see {@link dao.Espece#Espece(int, String, String, String, String, String, String, String, String, String, String, String, String, String, ArrayList)}
	 */
	@FXML
	private void ajouterEspeceHandler(ActionEvent event) {
		Alert alertSucces = null;

		try {
			// On montre une alerte pour confirmer l'ajout de l'espece
			Alert alertConfirmation = new Alert(AlertType.CONFIRMATION);
			alertConfirmation.setHeaderText("Voulez vous ajouter cette espèce ?");
			Optional<ButtonType> result = alertConfirmation.showAndWait();

			if (result.get() == ButtonType.OK) {
				try {
					// C'est au moment de cliquer sur OK qu'on ajoute l'instance de Espece au DAO
					Espece e = new Espece(0, nom.getText(), genre.getText(), famille.getText(), ordre.getText(),
							classe.getText(), embranchement.getText(), description.getText(),
							groupeTrophique.getValue().toString(), groupeEcologique.getValue().toString(),
							categorie.getText(), zone.getValue().toString(), cheminImage.getText(),
							new ArrayList<String>());

					e.setId(TrucsUtiles.getDAO().ajouter(e));

					alertSucces = new Alert(AlertType.INFORMATION);
					alertSucces.setHeaderText("Espèce ajoutée");
					alertSucces.showAndWait();

				} catch (Exception e) {
					// Le cas où le nom ou le lien est erroné
					alertSucces = new Alert(AlertType.ERROR);
					alertSucces.setHeaderText(e.getMessage());
					alertSucces.showAndWait();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode pour initialiser les groupes trophiques et écologiques d'après les
	 * tableaux des groupes fournits dans la classe Espece.<br>
	 * Pour connaitre les valeurs correctes, voir
	 * {@link dao.Espece#getListeGroupeEcologique} pour les groupe ecologiques,
	 * {@link dao.Espece#getListeGroupeTrophique} pour le groupe trophiques et
	 * {@link dao.Espece#getIntervalleZone} pour les zones.
	 */
	private void initializeChoiceBoxes() {

		// On initialise une liste des groupes écologiques
		ArrayList<String> groupesEcologiquesArray = new ArrayList<String>();

		// qu'on remplit à partir de celle dans la classe Espece
		for (String s : Espece.getListeGroupeEcologique())
			groupesEcologiquesArray.add(s);

		// et on adapte cette liste au CheckBox
		ObservableList<String> groupesEcologiquesList = FXCollections.observableArrayList(groupesEcologiquesArray);
		groupeEcologique.setItems(groupesEcologiquesList);
		groupeEcologique.getSelectionModel().selectFirst();

		// Meme manip qu'en haut, pour les groupes trophiques
		ArrayList<String> groupesTrophiquesArray = new ArrayList<String>();

		for (String s : Espece.getListeGroupeTrophique())
			groupesTrophiquesArray.add(s);

		ObservableList<String> groupesTrophiquesList = FXCollections.observableArrayList(groupesTrophiquesArray);
		groupeTrophique.setItems(groupesTrophiquesList);
		groupeTrophique.getSelectionModel().selectFirst();

		// On initialise le checkbox pour la zone aussi
		ArrayList<String> zoneArray = new ArrayList<String>() {
			{
				add("0");
				add("1");
				add("2");
				add("3");
			}
		};

		ObservableList<String> zoneList = FXCollections.observableArrayList(zoneArray);
		zone.setItems(zoneList);
		zone.getSelectionModel().selectFirst();
	}
}
