package test.java;


import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Ignore;
import org.junit.Test;

import dao.CSVEspeceDAO;


public class TestCSVEspeceDAO {
	// Classe qui sert � tester les diff�rentes m�thodes de la classe CSVEspeceDAO


	@Test
	public void lectureImage() {
		/* Le but de ce test est v�rifier si la fonction copierImage de la classe 
		 * CSVEspeceDAO fonctionne correctement
		 * Notons que cette fonction ne permet pas seulement de copier des images mais 
		 * n'importe quel type de fichier
		 */
		String dossierSortie = "src/test/resources/";
		// 1er cas : t�l�charger une image sur internet --------
		String chemin = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
		String nomNormal = dossierSortie + chemin.substring(chemin.lastIndexOf("/")+1);
		// On supprime l'image si elle existe d�j�
		try {
			Files.deleteIfExists(Paths.get(nomNormal));
		} catch (IOException e) {
			// On ne fait rien (le fichier n'existe pas)
		}
		// On devrait avoir 0 (t�lechargement reussi depuis internet)
		assertEquals(0,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 2eme cas : l'image existe d�j� (normalement le retour est -1)
		assertEquals(-1,CSVEspeceDAO.copierImage(chemin, dossierSortie));


		// 3eme cas : le nom de l'image n'est pas correcte
		// par exemple il contient des ? ou < au niveau du nom
		chemin = "https://www.google.com/images/impo?<ssible.mp4";
		assertEquals(1,CSVEspeceDAO.copierImage(chemin, dossierSortie));


		// 4eme cas : le chemin correspond � une image sur internet inexistante
		chemin = "https://www.google.com/images/branding/googlelogo/1x/rwerwerwerwwerwe.pkj";
		assertEquals(2,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		try {
			Files.deleteIfExists(Paths.get(nomNormal));
		} catch (IOException e) { }

		// 5eme cas : l'adresse est sens�e �tre celle d'un fichier sur internet mais la forme ne 
		// correspond pas
		chemin = "httpspoojij://www.google/images.png"; // Protocole inexistant
		assertEquals(3,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 6eme cas : l'h�te est injoignable (par exemple il n'y a pas de connexion internet)
		// On invente un h�te
		chemin = "https://www.gotwritjwrtiwrjwriotjwroitjwrotiwjrotriwggle.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
		assertEquals(4,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 7eme cas : le chemin correspond � une image sur le disque dur qui n'existe pas
		chemin = "C:/Je/Ne/Suis/Pas/La.png";
		assertEquals(6,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 8eme cas : le chemin correspond � une image pr�sente sur le disque
		chemin = "src/test/resources/a.png";
		dossierSortie = "src/test/resources/essai/";
		// On cr�e un sous dossier vide pour copie l'image
		try {
			Files.createDirectories(Paths.get(dossierSortie));
			Files.deleteIfExists(Paths.get(dossierSortie+"a.png"));
		} catch (IOException e) {
			// On ne fait rien (le dossier est d�j� vide)
		}
		assertEquals(0,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 9eme cas : autres erreurs
		// Par exemple la connexion se coupe brusquement ...
		// Le retour est 5 mais on ne le teste pas ici ...	
	}
	
	@Ignore
	@Test
	public void modificationsFichier() {
		// Le but du test est de v�rifier les actions de lectures et �critures de l'objet
	}

}
