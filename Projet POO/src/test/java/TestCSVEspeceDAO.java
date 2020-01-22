package test.java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dao.CSVEspeceDAO;
import dao.Espece;
import dao.FormeIncorrecteException;


public class TestCSVEspeceDAO {
	// Classe qui sert � tester les diff�rentes m�thodes de la classe CSVEspeceDAO
	private static Espece husky; // L'espece qui va servir de test
	private static int id = 0; // L'id de l'espece


	@Before
	public void initialisation() {
		ArrayList<String> synonymes = new ArrayList<String>();
		synonymes.add("chien des glaces");synonymes.add("husky sib�rien");
		// On cr�e l'objet
		husky = new Espece(id,"husky de syb�rie","canis","canidae","carnivora","mammalia",
				"chordata","il est traditionnellement �lev� comme chien d'attelage","carnivore",
				"espece sensible","4","","https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg",
				synonymes);	

		// On pr�pare un fichier csv
		File fichier = new File("src/test/resources/test interface EspeceDAO.csv");
		try (FileOutputStream fos = new FileOutputStream(fichier);
				OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				BufferedWriter bw = new BufferedWriter(osw)) {
			bw.write("husky de syb�rie,canis,canidae,carnivora,mammalia,chordata,il est traditionnellement �lev� comme chien d'attelage,carnivore,espece sensible,4,https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg,chien des glaces,husky sib�rien\n" + 
					"ligne test,1,1,1,1,1,1,1,1,1,1,syno 1, syno 2");
			bw.flush();
		} catch (IOException e) {}

	}

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

		// On cr�e un sous dossier vide qui servira plus tard
		try {
			Files.createDirectories(Paths.get(dossierSortie+"/essai/"));
			Files.deleteIfExists(Paths.get(dossierSortie+"/essai/testLecture.png"));
		} catch (IOException e) {
			// On ne fait rien (le dossier est d�j� vide)
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
		chemin = "src/test/resources/testLecture.png";

		assertEquals(0,CSVEspeceDAO.copierImage(chemin, dossierSortie+"/essai/"));

		// 9eme cas : autres erreurs
		// Par exemple la connexion se coupe brusquement ...
		// Le retour est 5 mais on ne le teste pas ici ...	
	}


	@Test
	public void constructeurCSVEspeceDAO() {

		// Le but du test est de v�rifier le constructeur de CSVEspeceDAO ainsi que la 
		// conversion des objets (texte - objet Java)
		CSVEspeceDAO lecteurCSV = null;

		// On teste notre constructeur

		File fichier = new File("src/test/resources/test mal forme.csv"); // Fichier malform� � la 2eme ligne

		try {
			lecteurCSV = new CSVEspeceDAO(fichier);
			fail("Fichier malform� !");
		} catch (IOException e) {
			fail("Fichier existant !");
		} catch (FormeIncorrecteException e) {
			assertEquals(2,e.getLigne()); // On v�rifie la ligne de l'erreur
		}

		fichier = new File("src/test/resources/inexistant.csv"); // Fichier inexistant

		try {
			lecteurCSV = new CSVEspeceDAO(fichier);
			fail("Fichier inexistant !");
		} catch (IOException e) {

		} catch (FormeIncorrecteException e) {
			fail("Fichier inexistant !");
		}

		fichier = new File("src/test/resources/test bien forme.csv"); // Fichier bien form�

		try {
			lecteurCSV = new CSVEspeceDAO(fichier);
		} catch (IOException e) {
			fail("Fichier correct !");
		} catch (FormeIncorrecteException e) {
			fail("Fichier correct !");
		}

		// On teste les methodes convertir
		String ligneHusky = "husky de syb�rie,canis,canidae,carnivora,mammalia,chordata,"
				+ "il est traditionnellement �lev� comme chien d'attelage,carnivore,"
				+ "espece sensible,4,https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Siberian-husky-1291343_1920.jpg/250px-Siberian-husky-1291343_1920.jpg,"
				+ "chien des glaces,husky sib�rien";

		assertEquals(ligneHusky,lecteurCSV.convertir(husky));
		assertEquals(husky,lecteurCSV.convertir(ligneHusky,id));
	}

	@Test
	public void testInterfaceEspeceDAO() {
		// Cette fonction sert � tester les diff�rentes m�thodes de l'interface EspeceDAO


		File fichier = new File("src/test/resources/test interface EspeceDAO.csv");
		// Ce fichier contient 2 especes : en 1er le husky et en 2eme une espece quelconque
		CSVEspeceDAO especeDAO = null;
		try {
			especeDAO = new CSVEspeceDAO(fichier);
		} catch (IOException | FormeIncorrecteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		// Test lecture
		Espece huskyLu = especeDAO.recuperer(id);
		assertEquals(husky,huskyLu);

		// Test modification
		huskyLu.setDescription("modification de la description");
		husky.setDescription("modification de la description");
		especeDAO.mettreAJour(huskyLu);
		assertEquals(husky,especeDAO.recuperer(id));

		// Test ajout
		Espece lambda = new Espece(999,"a","b","c","d","e","f","g","h","i","j","","k",
				new ArrayList<String>());
		// L'identifiant de lambda va �tre modifi� lors de l'ajout dans le fichier
		int idLambda = especeDAO.ajouter(lambda); 
		lambda.setId(idLambda);
		assertEquals(lambda,especeDAO.recuperer(idLambda));

		// Test filtrage
		// A FAIRE

		// Test enregistrement
		especeDAO.enregistrerModifications();
		// On va lire le fichier pour voir si la ligne qu'on a ajout� est l�
		List<String> contenuFichierReel = null;
		try {
			contenuFichierReel = Files.readAllLines(Path.of("src/test/resources/test interface EspeceDAO.csv"));
		} catch (IOException e1) {
			e1.printStackTrace();
			fail("Erreur !");
		}
		// On v�rifie le nombre de ligne
		assertEquals(especeDAO.getContenuFichier().size(),contenuFichierReel.size());

		// On v�rifie la ligne ajout�e
		String ligne = contenuFichierReel.get(idLambda);
		assertEquals(lambda,especeDAO.convertir(ligne,idLambda));
		
		// Test suppression
		
		especeDAO.supprimer(idLambda);
		assertEquals("",especeDAO.getContenuFichier().get(idLambda));
	}

}
