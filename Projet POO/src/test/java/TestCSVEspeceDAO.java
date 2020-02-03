package test.java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.CSVEspeceDAO;
import dao.ChampIncorrectException;
import dao.Espece;


public class TestCSVEspeceDAO {
	// Classe qui sert à tester les différentes méthodes de la classe CSVEspeceDAO
	private static Espece husky; // L'espece qui va servir de test
	private static String ligneHusky;
	private static String dossierImages = "src/test/resources/images/";


	@Before
	public void initialisation() {
		ArrayList<String> synonymes = new ArrayList<String>();
		synonymes.add("chien des glaces");synonymes.add("husky sibérien");
		// On crée l'objet
		try {
			husky = new Espece(0,"husky de sybérie","canis","canidae","carnivora","mammalia",
					"chordata","il est traditionnellement élevé comme chien d'attelage","carnivore",
					"sensible","4","2","src/test/resources/images/250px-Siberian-husky-1291343_1920.jpg",
					"https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg",
					synonymes);
		} catch (ChampIncorrectException e1) {
			e1.printStackTrace();
		}	
		ligneHusky = "husky de sybérie,canis,canidae,carnivora,mammalia,chordata,il est traditionnellement élevé comme chien d'attelage,carnivore,sensible,4,2,https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg,chien des glaces,husky sibérien";

		// On prépare un fichier csv
		File fichier = new File("src/test/resources/test interface EspeceDAO.csv");
		try (FileOutputStream fos = new FileOutputStream(fichier);
				OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				BufferedWriter bw = new BufferedWriter(osw)) {
			bw.write(ligneHusky+"\n" + 
					"ligne test,1,1,1,1,1,1,carnivore,sensible,1,2,https://cdn.pixabay.com/photo/2019/12/23/08/15/alaska-4714097__340.jpg,syno 1, syno 2");
			bw.flush();
		} catch (IOException e) {}

	}
	
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

		// On crée un sous dossier vide qui servira plus tard
		try {
			Files.createDirectories(Paths.get(dossierSortie+"/essai/"));
			Files.deleteIfExists(Paths.get(dossierSortie+"/essai/testLecture.png"));
			Files.deleteIfExists(Paths.get(dossierSortie+"/essai/testLecture.txt"));
		} catch (IOException e) {
			// On ne fait rien (le dossier est déjà vide)
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
		
		// 8eme cas : le chemin correspond à un fichier qui n'est pas une image
		chemin = "src/test/resources/testLecture.txt";
		assertEquals(7,CSVEspeceDAO.copierImage(chemin, dossierSortie+"essai/"));

		// 9eme cas : le chemin correspond à une image présente sur le disque
		chemin = "src/test/resources/testLecture.png";

		assertEquals(0,CSVEspeceDAO.copierImage(chemin, dossierSortie+"essai/"));

		// 10eme cas : autres erreurs
		// Par exemple la connexion se coupe brusquement ...
		// Le retour est 5 mais on ne le teste pas ici ...	
	}


	@Test
	public void constructeurCSVEspeceDAO() {

		// Le but du test est de vérifier le constructeur de CSVEspeceDAO ainsi que la 
		// conversion des objets (texte - objet Java)
		CSVEspeceDAO lecteurCSV = null;

		// On teste notre constructeur

		File fichier = new File("src/test/resources/test mal forme.csv"); // Fichier malformé à la 2eme ligne

		try {
			lecteurCSV = new CSVEspeceDAO(fichier,dossierImages);
			assertEquals(1,lecteurCSV.recupererErreurs().size());
		} catch (IOException e) {
		
		}

		fichier = new File("src/test/resources/inexistant.csv"); // Fichier inexistant

		try {
			lecteurCSV = new CSVEspeceDAO(fichier,dossierImages);
			fail("Fichier inexistant !");
		} catch (IOException e) { }

		fichier = new File("src/test/resources/test bien forme valeur mauvaise.csv");

		try {
			lecteurCSV = new CSVEspeceDAO(fichier,dossierImages);
			assertEquals(1,lecteurCSV.recupererErreurs().size());
		} catch (IOException e) {
			fail("Fichier present !");
		}
		
		fichier = new File("src/test/resources/test bien forme bonne valeur.csv");

		try {
			lecteurCSV = new CSVEspeceDAO(fichier,dossierImages);
			assertTrue(lecteurCSV.recupererErreurs().isEmpty());
		} catch (IOException e) {
			fail("Fichier present !");
		}

		// On teste les methodes convertir
		assertEquals(ligneHusky,lecteurCSV.convertir(husky));
		try {
			assertEquals(husky,lecteurCSV.convertir(ligneHusky,husky.getId()));
		} catch (ChampIncorrectException e) {
			fail("Ligne correcte !");
		}
	}
	
	@Test
	public void testEspece() {
		// Tests des differents setters de l'objet Espece (et du constructeur)
		Espece test = null;
		try {
			test = new Espece(0,"test","test","test","test","test","","","carnivore","sensible","4","2",
					"",new ArrayList<String>());
		} catch (ChampIncorrectException e) {
			fail("L'espece est correcte !");
		}
		
		try {
			test.setNom(""); // Non
			fail("Le nom est incorrect");
		} catch (ChampIncorrectException e) {
		}
		
		try {
			test.setGroupeEcologique("eee"); // Non
			fail("");
		} catch (ChampIncorrectException e) {
		} 
		
		try {
			test.setGroupeTrophique("fere"); // Non
			fail("");
		} catch (ChampIncorrectException e) {
		}
		
		try {
			test.setZone(5); // Non
			fail("");
		} catch (ChampIncorrectException e) {
		}
		
		try {
			test.setGroupeTrophique("necrophage"); // Oui
		} catch (ChampIncorrectException e) {
			fail("Le groupe trophique est correct !");
		} 
	}

	@Test
	public void testInterfaceEspeceDAO() {
		// Cette fonction sert à tester les différentes méthodes de l'interface EspeceDAO


		File fichier = new File("src/test/resources/test interface EspeceDAO.csv");
		// Ce fichier contient 2 especes : en 1er le husky et en 2eme une espece quelconque
		CSVEspeceDAO especeDAO = null;
		try {
			especeDAO = new CSVEspeceDAO(fichier,dossierImages);
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

		// Test lecture
		Espece huskyLu = especeDAO.recuperer(0);
		assertEquals(husky,huskyLu);

		// Test modification
		huskyLu.setDescription("modification de la description");
		husky.setDescription("modification de la description");
		especeDAO.mettreAJour(huskyLu);
		assertEquals(husky,especeDAO.recuperer(huskyLu.getId()));

		// Test ajout
		Espece lambda = null;
		try {
			lambda = new Espece(999,"a","b","c","d","e","f","g","carnivore","sensible","j","1","src/test/resources/testLecture.png",
					new ArrayList<String>());
		} catch (ChampIncorrectException e) {
			e.printStackTrace();
		}
		// L'identifiant de lambda va être modifié lors de l'ajout dans le fichier
		try {
			especeDAO.ajouter(lambda);
		} catch (ChampIncorrectException e1) {
			fail("Espece incorrecte !");
		}
		int idLambda = lambda.getId();
		assertEquals(lambda.getId(),idLambda); 
		assertEquals(lambda,especeDAO.recuperer(idLambda));

		// Test filtrage
		ArrayList<Espece> resultat;
		
		resultat = especeDAO.filtrer("champ introuvable", "tous", "tous", "tous");
		assertTrue(resultat.isEmpty());
		
		resultat = especeDAO.filtrer("husky", "embranchement", "tous", "tous");
		assertTrue(resultat.isEmpty());
		
		resultat = especeDAO.filtrer("husky", "nom", "tous", "tous");
		assertEquals(1,resultat.size());
		assertEquals(husky,resultat.get(0));
		
		// Test enregistrement
		
		assertTrue(especeDAO.enregistrerModifications());
		
		
		// On va lire le fichier pour voir si la ligne qu'on a ajouté est là
		List<String> contenuFichierReel = new ArrayList<String>();
		String l;
		try (FileInputStream fis = new FileInputStream(fichier);
				InputStreamReader isr = new InputStreamReader(fis, "utf-8");
				BufferedReader br = new BufferedReader(isr)) {
			while ((l = br.readLine()) != null)
					contenuFichierReel.add(l);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Fichier existant !");
		} 
		
		// On vérifie le nombre de ligne
		assertEquals(especeDAO.recupererToutes().size(),contenuFichierReel.size());

		// On vérifie la ligne ajoutée
		String ligne = contenuFichierReel.get(idLambda);
		try {
			assertEquals(lambda,especeDAO.convertir(ligne,idLambda));
		} catch (ChampIncorrectException e) {
			fail("L'espece est correcte ! ");
		}
		
		// Test suppression
		
		especeDAO.supprimer(idLambda);
		assertEquals(null,especeDAO.recuperer(idLambda));
	}

}
