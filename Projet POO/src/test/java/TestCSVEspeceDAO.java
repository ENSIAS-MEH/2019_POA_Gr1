package test.java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import dao.CSVEspeceDAO;
import dao.Espece;
import dao.FormeIncorrecteException;


public class TestCSVEspeceDAO {
	// Classe qui sert à tester les différentes méthodes de la classe CSVEspeceDAO

	@Test
	public void lectureImage() {
		/* Le but de ce test est vérifier si la fonction copierImage de la classe 
		 * CSVEspeceDAO fonctionne correctement
		 * Notons que cette fonction ne permet pas seulement de copier des images mais 
		 * n'importe quel type de fichier
		 */
		String dossierSortie = "src/test/resources/";
		// 1er cas : télécharger une image sur internet --------
		String chemin = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
		String nomNormal = dossierSortie + chemin.substring(chemin.lastIndexOf("/")+1);
		// On supprime l'image si elle existe déjà
		try {
			Files.deleteIfExists(Paths.get(nomNormal));
		} catch (IOException e) {
			// On ne fait rien (le fichier n'existe pas)
		}
		// On devrait avoir 0 (télechargement reussi depuis internet)
		assertEquals(0,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 2eme cas : l'image existe déjà (normalement le retour est -1)
		assertEquals(-1,CSVEspeceDAO.copierImage(chemin, dossierSortie));


		// 3eme cas : le nom de l'image n'est pas correcte
		// par exemple il contient des ? ou < au niveau du nom
		chemin = "https://www.google.com/images/impo?<ssible.mp4";
		assertEquals(1,CSVEspeceDAO.copierImage(chemin, dossierSortie));


		// 4eme cas : le chemin correspond à une image sur internet inexistante
		chemin = "https://www.google.com/images/branding/googlelogo/1x/rwerwerwerwwerwe.pkj";
		assertEquals(2,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		try {
			Files.deleteIfExists(Paths.get(nomNormal));
		} catch (IOException e) { }

		// 5eme cas : l'adresse est sensée être celle d'un fichier sur internet mais la forme ne 
		// correspond pas
		chemin = "httpspoojij://www.google/images.png"; // Protocole inexistant
		assertEquals(3,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 6eme cas : l'hôte est injoignable (par exemple il n'y a pas de connexion internet)
		// On invente un hôte
		chemin = "https://www.gotwritjwrtiwrjwriotjwroitjwrotiwjrotriwggle.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
		assertEquals(4,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 7eme cas : le chemin correspond à une image sur le disque dur qui n'existe pas
		chemin = "C:/Je/Ne/Suis/Pas/La.png";
		assertEquals(6,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 8eme cas : le chemin correspond à une image présente sur le disque
		chemin = "src/test/resources/a.png";
		dossierSortie = "src/test/resources/essai/";
		// On crée un sous dossier vide pour copie l'image
		try {
			Files.createDirectories(Paths.get(dossierSortie));
			Files.deleteIfExists(Paths.get(dossierSortie+"a.png"));
		} catch (IOException e) {
			// On ne fait rien (le dossier est déjà vide)
		}
		assertEquals(0,CSVEspeceDAO.copierImage(chemin, dossierSortie));

		// 9eme cas : autres erreurs
		// Par exemple la connexion se coupe brusquement ...
		// Le retour est 5 mais on ne le teste pas ici ...	
	}

	
	@Test
	public void constructeurCSVEspeceDAO() {

		// Le but du test est de vérifier le constructeur de CSVEspeceDAO ainsi que la 
		// conversion des objets (texte - objet Java)
		CSVEspeceDAO lecteurCSV = null;
		
		// On teste notre constructeur
		
		File fichier = new File("src/test/resources/test1.csv"); // Fichier malformé à la 2eme ligne
		
		try {
			lecteurCSV = new CSVEspeceDAO(fichier);
			fail("Fichier malformé !");
		} catch (IOException e) {
			fail("Fichier existant !");
		} catch (FormeIncorrecteException e) {
			assertEquals(2,e.getLigne()); // On vérifie la ligne de l'erreur
		}

		fichier = new File("src/test/resources/tes.csv"); // Fichier inexistant

		try {
			lecteurCSV = new CSVEspeceDAO(fichier);
			fail("Fichier inexistant !");
		} catch (IOException e) {
			
		} catch (FormeIncorrecteException e) {
			fail("Fichier inexistant !");
		}
		
		fichier = new File("src/test/resources/test2.csv"); // Fichier bien formé

		try {
			lecteurCSV = new CSVEspeceDAO(fichier);
		} catch (IOException e) {
			fail("Fichier correct !");
		} catch (FormeIncorrecteException e) {
			fail("Fichier correct !");
		}

		// On teste les methodes convertir
		String ligneHusky = "husky sibérien,canis,canidae,carnivora,mammalia,chordata,"
				+ "il est traditionnellement élevé comme chien d'attelage,carnivore,"
				+ "espece sensible,4,https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg,"
				+ "chien des glaces,husky sibérien";
		
		int id = 1;
		ArrayList<String> synonymes = new ArrayList<String>();
		synonymes.add("chien des glaces");synonymes.add("husky sibérien");
		
		Espece husky = new Espece(id,"husky sibérien","canis","canidae","carnivora","mammalia",
				"chordata","il est traditionnellement élevé comme chien d'attelage","carnivore",
				"espece sensible","4","https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg",
				synonymes);
		
		assertEquals(ligneHusky,lecteurCSV.convertir(husky));
		assertEquals(husky,lecteurCSV.convertir(ligneHusky,id));
	}

}
