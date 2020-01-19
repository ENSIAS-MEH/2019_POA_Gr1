package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * 
 * Cette classe permet de faire la correspondance entre objet java et données textes (fichier csv)
 * Il permet de lire un fichier csv et de créer des objets Espece.
 *
 */
public class CSVEspeceDAO implements EspeceDAO {
	protected File fichier; // Le fichier qu'on lit
	protected ArrayList<String> contenuFichier = new ArrayList<String>(); 
	protected int nouvelleLigne; // Cet entier correspond à la dernière ligne qui a été lue + 1 
	protected final int nombreElementsFixe = 11; // C'est le nombre minimum d'élements d'une ligne
	
	/**
	 * Constructeur de FichierCSV 
	 * 
	 * <br> Les lignes du fichier csv doivent respecter la forme suivante :  nom,genre,famille,ordre,classe,embranchement,
	 * description,groupe trophique,groupe ecologique,categorie importance,
	 * chemin vers l'image[, synonyme 1, synonyme 2, ...].<br> Tous les champs sont obligatoires sauf 
	 * les synonymes.
	 * @param fichier C'est le fichier qu'on veut lire
	 * @throws IOException Si le fichier n'est pas trouvé
	 * @throws FormeIncorrecteException Si les lignes du fichier ne respectent pas la forme correcte
	 */
	public CSVEspeceDAO(File fichier) throws IOException, FormeIncorrecteException{
		this.fichier = fichier;
		String ligne;
		int i = 0; // On initialise un compteur pour savoir à quelle ligne s'est produite une erreur
		try(FileInputStream fis = new FileInputStream(fichier);
				InputStreamReader isr = new InputStreamReader(fis,"utf-8");
				BufferedReader br = new BufferedReader(isr)){
			while( (ligne = br.readLine()) != null) {
				// On vérifie d'abord qu'il y a assez d'élements
				String[] elements = ligne.split(",");
				// On ne considère pas les ligne vides (pas d'erreurs)
				if ( (!ligne.equals("")) && elements.length < nombreElementsFixe) {
					throw new FormeIncorrecteException("Erreur à la ligne "+(1+1)+" : Il n'y a que"
							+ " "+elements.length+" élements !\nIl faut au moins "+nombreElementsFixe+
							" élements.",(i+1));
				}
				contenuFichier.add(ligne);
				i++;
			}
		}
		nouvelleLigne = i;
	}
	
    /**
     * Renvoie un objet Espece qui correspond à la ligne en question
     * Tous les caractères sont transformés en minuscule sauf l'url
     * 
     * @param ligne La chaine de caractere à convertir
     * @param id l'identifiant de l'objet Espece à creer
     * @return l'objet Espece crée
     */
	public Espece convertir(String ligne, int id) {
		String[] elements = ligne.split(",");
		// On met tout en minuscule sauf l'url qui est le 11eme element
		for (int i = 0; i< elements.length; i++)
			elements[i] = i == 10 ? elements[i] : elements[i].toLowerCase();
		
		ArrayList<String> synonymes = new ArrayList<String>();
		if (elements.length > nombreElementsFixe) {
			for (int i = nombreElementsFixe; i < elements.length; i++)
				synonymes.add(elements[i]);
		}
		return new Espece(id,elements[0],elements[1],elements[2],elements[3],elements[4],elements[5],
				elements[6],elements[7],elements[8],elements[9],elements[10],synonymes);

	}
	
	/**
	 * Renvoie une chaine de caractere qui correspond à l'espece
	 * Tous les caractères sont transformés en minuscule sauf l'url
	 * 
	 * @param espece l'objet Espece à convertir
	 * @return la chaine de caractere correspondante
	 */
	public String convertir(Espece espece) {
		String synonymes = "";
		ArrayList<String> liste = espece.getSynonymes();
		for (int i = 0; i < liste.size(); i++)
			synonymes += (i == 0 ? "" : "," ) + liste.get(i);
		return (espece.getNom()+","+espece.getGenre()+","+espece.getFamille()+","+
		espece.getOrdre()+","+espece.getClasse()+","+espece.getEmbranchement()+","+
		espece.getDescription()+","+espece.getGroupeTrophique()+","+espece.getGroupeEcologique()+","+
		espece.getCategorieImportance()+",").toLowerCase()+espece.getCheminImage()+","+synonymes.toLowerCase();

	}

	/**
	 * @return the contenuFichier
	 */
	public ArrayList<String> getContenuFichier() {
		return contenuFichier;
	}

	public void mettreAJour(Espece espece) {
		int id = espece.getId();
		contenuFichier.set(id, convertir(espece));
	}

	public int ajouter(Espece espece) {
		int id = nouvelleLigne;
		espece.setId(id);
		nouvelleLigne++;
		contenuFichier.add(convertir(espece));
		// On renvoie l'identifiant de l'espece
		return id;
	}

	public void supprimer(int id) {
		contenuFichier.set(id, "");
	}

	public void enregistrerModifications() {
		try(FileOutputStream fos = new FileOutputStream(fichier);
				OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				BufferedWriter bw = new BufferedWriter(osw)){
			for (String ligne: contenuFichier)
				bw.write(ligne+"\n");
		} catch (IOException e) {
		}
	}
	
	/**
	 * Télécharge l'image qui correspond à la chaine entree et la place dans le dossier sortie sous le 
	 * nom spécifié
	 * @param entree Le chemin de l'image à telecharger
	 * @param sortie Le dossier ou il faut placer l'image
	 * @param nom Le nom de l'image dans le dossier de sortie
	 * @return Un code d'opération qui détermine si la fonction a reussi à télécharger l'image :
	 * <br> 0 si l'image a bien été recupérée
	 * <br> -1 si l'image existe déjà dans le dossier de sortie
	 * <br> les autres codes correspondent à des erreurs : 
	 * <br> 1 si le nom de l'image n'est pas correcte
	 * <br> 2 si l'image n'est pas trouvée 
	 * <br> 3 si l'adresse est sensée être celle d'un fichier sur internet mais la forme ne correspond pas
	 * <br> 4 si l'hôte est injoignable (par exemple il n'y a pas de connexion internet)
	 * <br> 6 si l'hôte est injoignable (par exemple il n'y a pas de connexion internet)
	 * <br> 7 si le chemin correspond à une image sur le disque dur qui n'existe pas
	 * <br> 5 pour les autres erreurs
	 * 
	 */
	public static int copierImage(String entree, String sortie, String nom) {
		boolean dejaLa = false;
		sortie = sortie+nom;
		try {
			// On verifie si l'image existe déjà
			dejaLa = Files.exists(Paths.get(sortie));
		} catch(InvalidPathException e) {
			// Chemin incorrect
			return 1;
		}
		if (dejaLa) {
			// Image déjà là
			return -1;
		}
		// Si l'image commence par http on va la chercher sur le internet sinon on la cherche sur le disque
		else if(entree.startsWith("http")) {
			try(InputStream in = new URL(entree).openStream()){
				Files.copy(in, Paths.get(sortie));
				return 0; // Tout s'est bien passé
			} 
			catch (FileNotFoundException e) {
				return 2; // Fichier non trouvé
			}
			catch (MalformedURLException e) {
				return 3; // URL incorrecte
			}
			catch (UnknownHostException e) {
				return 4; // Probleme connexion
			}
			catch (IOException e) {
				return 5; // Autres erreurs
			}
		}
		else {
			try {
				Files.copy(Paths.get(entree), Paths.get(sortie));
				return 0; // Tout s'est bien passé
			} catch (IOException e) {
				return 6; // Fichier non trouvé sur le disque
			}
		}
	}
	/**
	 * Télécharge l'image qui correspond à la chaine entree et la place dans le dossier sortie sous le 
	 * meme nom
	 * @param entree Le chemin de l'image à telecharger
	 * @param sortie Le dossier ou il faut placer l'image
	 * @return Un code d'opération qui détermine si la fonction a reussi à télécharger l'image
	 * @see dao.CSVEspeceDAO#copierImage(String, String, String)
	 */
	public static int copierImage(String entree, String sortie) {
		return CSVEspeceDAO.copierImage(entree, sortie, entree.substring(entree.lastIndexOf("/")+1));
	}
	
	public Espece recuperer(int id) {
		return convertir(contenuFichier.get(id),id);
	}

	public ArrayList<Espece> recupererToutes() {
		ArrayList<Espece> liste = new ArrayList<Espece>();
		for (int i = 0; i<contenuFichier.size(); i++) {
			String ligne = contenuFichier.get(i);
			if (!ligne.equals(""))
				liste.add(convertir(ligne,i));
		}
		return liste;
	}

	public ArrayList<Espece> filtrer() {
		// A DETERMINER
		return null;
	}
}
