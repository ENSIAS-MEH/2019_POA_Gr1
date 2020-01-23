package dao;

import java.util.ArrayList;

public interface EspeceDAO {
	/**
	 * Fonction qui construit l'objet Espece qui a l'identifiant id
	 * 
	 * @param id l'identifiant (unique) de l'espece recherch�e
	 * @return L'espece en question si elle est trouv�e, null sinon
	 */
	public Espece recuperer(int id);

	/**
	 * Fonction qui renvoie toutes les especes pr�sentes dans la base de donn�es
	 * @return Une liste contenant toutes les especes de la BDD
	 */
	public ArrayList<Espece> recupererToutes();
	
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
	public ArrayList<Espece> filtrer(String saisie, String champSaisi, String gEcoSaisi, String gTroSaisi);
	
	/**
	 * Fonction qui ajoute une espece � la base de donn�es
	 * 
	 * @param espece L'espece qu'on veut ajouter � la base de donn�es
	 */
	public int ajouter(Espece espece);
	
	/**
	 * Fonction qui supprime une espece de la base de donn�es
	 * 
	 * @param id L'identifiant de l'espece qu'on veut supprimer
	 * @return Un entier qui correspond � l'identifiant de l'espece dans la base de donn�es
	 */
	public void supprimer(int id);
	
	/**
	 * Fonction qui modifie une espece de la base de donn�es
	 * C'est l'identifiant de l'espece qui est utilis� pour trouver "l'ancienne" espece (qui sera 
	 * remplac�e)
	 * @param espece La nouvelle espece qui doit remplacer l'ancienne
	 */
	public void mettreAJour(Espece espece);
	
	/**
	 * Fonction qui sauvegarde les eventuelles modifications faites sur la base de donn�es
	 */
	public void enregistrerModifications();
	
	/**
	 * Fonction qui permet de recuperer des eventuelles erreurs en particulier apr�s la cr�ation de l'objet
	 * @return Une liste de chaine de caract�re, chaque chaine repr�sente une erreur
	 */
	public ArrayList<String> recupererErreurs();
	
	
	public static void main(String[] args) {
	}
}
