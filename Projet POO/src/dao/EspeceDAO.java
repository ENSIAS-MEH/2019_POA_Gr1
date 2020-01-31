package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * Classe abstraite qui permet sert d'interface entre les donn�es et les objets Espece
 *
 */
public abstract class EspeceDAO {
	
	protected ArrayList<String> listeErreurs = new ArrayList<String>();// Une liste contenant les erreurs
	// qui ont �t� rencontr�es
	protected String dossierImages = "images/"; // Le dossier par defaut ou les images sont enregistr�es
	

	/**
	 * Fonction qui construit l'objet Espece qui a l'identifiant id
	 * 
	 * @param id l'identifiant (unique) de l'espece recherch�e
	 * @return L'espece en question si elle est trouv�e, null sinon
	 */
	public abstract Espece recuperer(int id);

	/**
	 * Fonction qui renvoie toutes les especes pr�sentes dans la base de donn�es
	 * @return Une liste contenant toutes les especes de la BDD
	 */
	public abstract ArrayList<Espece> recupererToutes();
	
	/**
	 * Fonction qui renvoie toutes les especes v�rifiant les crit�res pr�cis�s. <br>
	 * 
	 * Le champSaisi doit �tre dans la liste suivante : 
	 * [noms, familles, classes, genres, descriptions, ordres, embranchements,tous] <br>
	 * gEcoSaisi et gTroSaisi repr�sentent les groupes ecologiques et trophiques choisis
	 * 
	 * @param saisie Le mot ou expression recherch�e
	 * @param champSaisi La zone o� on cherche l'expression
	 * @param gEcoSaisi Le groupe ecologiaue choisi
	 * @param gTroSaisi Le groupe trophique choisi
	 * @return Une liste contenant toutes les especes v�rifiant les crit�res pr�cis�s
	 */
	public abstract ArrayList<Espece> filtrer(String saisie, String champSaisi, String gEcoSaisi, String gTroSaisi);
	
	/**
	 * Fonction qui ajoute une espece � la base de donn�es
	 * Cette fonction modifie l'identifiant de l'objet espece pass� en parametre
	 * @param espece L'espece qu'on veut ajouter � la base de donn�es
	 * @return Un entier qui correspond � l'identifiant de l'espece dans la base de donn�es
	 * @throws ChampIncorrectException Si l'espece n'a pas d'image sur le disque et qu'on arrive pas � la 
	 * recuperer
	 */
	public abstract int ajouter(Espece espece) throws ChampIncorrectException;
	
	/**
	 * Fonction qui supprime une espece de la base de donn�es
	 * 
	 * @param id L'identifiant de l'espece qu'on veut supprimer
	 */
	public abstract void supprimer(int id);
	
	/**
	 * Fonction qui modifie une espece de la base de donn�es
	 * C'est l'identifiant de l'espece qui est utilis� pour trouver "l'ancienne" espece (qui sera 
	 * remplac�e)
	 * @param espece La nouvelle espece qui doit remplacer l'ancienne
	 */
	public abstract void mettreAJour(Espece espece);
	
	/**
	 * Fonction qui sauvegarde les eventuelles modifications faites sur la base de donn�es
	 */
	public abstract void enregistrerModifications();
	
	/**
	 * Fonction qui permet de recuperer des eventuelles erreurs en particulier apr�s la cr�ation de l'objet
	 * <br> Cette liste est vid�e � chaque appel de la fonction
	 * @return Une liste de chaine de caract�re, chaque chaine repr�sente une erreur
	 */
	public ArrayList<String> recupererErreurs(){
		ArrayList<String> copie = new ArrayList<String>(listeErreurs );
		listeErreurs.clear(); // On vide la liste des erreurs
		return copie;
	}
	
	/**
	 * T�l�charge l'image qui correspond � la chaine entree et la place dans le
	 * dossier sortie sous le nom sp�cifi�
	 * 
	 * @param entree Le chemin de l'image � telecharger <br>
	 * S'il s'agit d'une image sur internet, elle doit commencer par le protocole (http, ftp, etc)
	 * @param sortie Le dossier ou il faut placer l'image
	 * @param nom    Le nom de l'image dans le dossier de sortie
	 * @return Un code d'op�ration qui d�termine si la fonction a reussi �
	 *         t�l�charger l'image : <br>
	 *         0 si l'image a bien �t� recup�r�e <br>
	 *         -1 si l'image existe d�j� dans le dossier de sortie <br>
	 *         les autres codes correspondent � des erreurs : <br>
	 *         1 si le nom de l'image n'est pas correcte <br>
	 *         2 si l'image n'est pas trouv�e <br>
	 *         3 si l'adresse est sens�e �tre celle d'un fichier sur internet mais
	 *         la forme ne correspond pas <br>
	 *         4 si l'h�te est injoignable (par exemple il n'y a pas de connexion
	 *         internet) <br>
	 *         6 si le chemin correspond � une image sur le disque dur qui n'existe
	 *         pas <br>
	 *         7 si le chemin ne correspond pas � une image
	 *         5 pour les autres erreurs
	 * 
	 */
	public static int copierImage(String entree, String sortie, String nom) {
		boolean dejaLa = false;
		sortie = sortie + nom;
		File fichierSortie = new File(sortie);
		try {
			// On verifie si l'image existe d�j�
			dejaLa = Files.exists(Paths.get(sortie));
		} catch (InvalidPathException e) {
			// Chemin incorrect
			return 1;
		}
		if (dejaLa) {
			// Le fichier est d�j� l� : on v�rifie que c'est une image
			try {
				if (estImage(fichierSortie)) 
					return -1; // ok
				else 
					return 7; // ce n'est pas une image
			} catch (IOException e) {
				return 5;
			} 
		}
		// Si l'image commence par http ou ftp on va la chercher sur le internet sinon on la
		// cherche sur le disque
		else if (entree.startsWith("http") || entree.startsWith("ftp")){
			try (InputStream in = new URL(entree).openStream()) {
				Files.copy(in, Paths.get(sortie));
				// On verifie si c'est bien une image 
				try {
					if (estImage(fichierSortie))
						return 0;
					else {
						fichierSortie.delete();
						return 7;
					}
				} catch (IOException e) {
					return 5;
				} 
			} catch (FileNotFoundException e) {
				return 2; // Fichier non trouv�
			} catch (MalformedURLException e) {
				return 3; // URL incorrecte
			} catch (UnknownHostException e) {
				return 4; // Probleme connexion
			} catch (IOException e) {
				return 5; // Autres erreurs
			}
		} else {
			try {
				Files.copy(Paths.get(entree), Paths.get(sortie));
				if (estImage(fichierSortie))
					return 0;
				else {
					fichierSortie.delete();
					return 7;
				}
			} catch (IOException e) {
				return 6; // Fichier non trouv� sur le disque
			}
		}
	}

	private static boolean estImage(File fichierSortie) throws IOException {
		// Cette fonction verifie si le ficher est une image
		return (ImageIO.read(fichierSortie) != null);
	}

	/**
	 * T�l�charge l'image qui correspond � la chaine entree et la place dans le
	 * dossier sortie sous le meme nom
	 * 
	 * @param entree Le chemin de l'image � telecharger
	 * @param sortie Le dossier ou il faut placer l'image
	 * @return Un code d'op�ration qui d�termine si la fonction a reussi �
	 *         t�l�charger l'image
	 * @see dao.CSVEspeceDAO#copierImage(String, String, String)
	 */
	public static int copierImage(String entree, String sortie) {
		return CSVEspeceDAO.copierImage(entree, sortie, entree.substring(entree.lastIndexOf("/") + 1));
	}
	
	/**
	 * Fonction qui tente de t�lecharger l'image d'une espece
	 * @param image le chemin de l'image originale
	 * @return le chemin de l'image sur le disque
	 * @throws ChampIncorrectException Si l'image n'a pas pu �tre t�l�charg�e
	 */
	public String verifierImage(String image) throws ChampIncorrectException {
		// On tente de telecharger l'image
		int erreurCopie = copierImage(image, dossierImages);
		if (erreurCopie > 0) {
			// Il y a un probleme ...
			// On signale seulement 3 cas : probleme de connexion, non image et les autres
			if (erreurCopie == 2)
				throw new ChampIncorrectException("Impossible de se connecter "
						+ "pour recuperer l'image. Veuillez v�rifier votre connexion et "
						+ "la disponibilit� du site web.");
			else if (erreurCopie == 7)
				throw new ChampIncorrectException("Le lien fourni ne correspond pas � une image.");
			else
				throw new ChampIncorrectException("Impossible de recuperer "
						+ "l'image. Veuillez v�rifier l'addresse.");
		}
		return dossierImages+image.substring(image.lastIndexOf("/") + 1);
	}

	public String getDossierImages() {
		return dossierImages;
	}

	/**
	 * @param dossierImages the dossierImages to set
	 */
	public void setDossierImages(String dossierImages) {
		this.dossierImages = dossierImages;
	}
}
