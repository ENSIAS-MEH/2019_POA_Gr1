package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.Espece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
	private HBox groupeTrophiqueHBox,groupeEcologiqueHBox;

	@FXML
	private RadioButton noms, familles, classes, genres, descriptions, ordres, embranchements;
	
	RadioButton[] buttonsList = {noms,familles,classes,genres,descriptions,ordres,embranchements};
	CheckBox[] groupeTrophiqueCheckBoxes,groupeEcologiqueCheckBoxes;
	
	private ArrayList<Espece> listEspeces;

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
		
		/*
		 * TODO Apparement on devrait r�cuperer les groupes trophiques/�cologiques du
		 * fichier csv puis initier les boutons ici selon les groupes qui y figurent
		 */
		
		listEspeces = TrucsUtiles.getListEspeces();
		
		for(Espece espece:listEspeces) {
			
			groupeEcologiqueHBox.getChildren().add(new CheckBox(espece.getGroupeEcologique()));
			groupeTrophiqueHBox.getChildren().add(new CheckBox(espece.getGroupeTrophique()));
		}
	}

	@FXML
	private void rechercherButtonHandler(ActionEvent event) {
		// On r�cup�re ce qui est dans la zone de saisie
		String saisie = searchTextField.getText();
		
		// On r�cup�re le RadioButton choisis
		RadioButton boutonChoisis = fetchClickedButton();
		
		

		// TODO Le bloc de recherche � la fin
		
	}
	
	/**
	 * M�thode pour r�cuperer le RadioButton choisis
	 * @return
	 */
	private RadioButton fetchClickedButton() {
		RadioButton resultButton=null;
		for(RadioButton button : buttonsList)
			if (button.isSelected())
				resultButton=button;
		return resultButton;
	}
	
	private ArrayList<CheckBox> fetchCheckBoxes() {
		return new ArrayList<CheckBox>();
	}
}
