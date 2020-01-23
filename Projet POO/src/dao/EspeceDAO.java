package dao;

import java.util.ArrayList;

public interface EspeceDAO {
	/**
	 * Fonction qui construit l'objet Espece qui a l'identifiant id
	 * 
	 * @param id l'identifiant (unique) de l'espece recherchée
	 * @return L'espece en question si elle est trouvée, null sinon
	 */
	public Espece recuperer(int id);

	/**
	 * Fonction qui renvoie toutes les especes présentes dans la base de données
	 * @return Une liste contenant toutes les especes de la BDD
	 */
	public ArrayList<Espece> recupererToutes();
	
	/**
	 * Fonction qui renvoie toutes les especes vérifiant les critères précisés. <br>
	 * 
	 * Le champSaisi doit être dans la liste suivante : 
	 * [noms, familles, classes, genres, descriptions, ordres, embranchements,tous] <br>
	 * gEcoSaisi et gTroSaisi représentent les groupes ecologiques et trophiques choisis
	 * 
	 * @param saisie Le mot ou expression recherchée
	 * @param champSaisi La zone où on cherche l'expression
	 * @param gEcoSaisi Le groupe ecologiaue choisi
	 * @param gTroSaisi Le groupe trophique choisi
	 * @return Une liste contenant toutes les especes vérifiant les critères précisés
	 */
	public ArrayList<Espece> filtrer(String saisie, String champSaisi, String gEcoSaisi, String gTroSaisi);
	
	/**
	 * Fonction qui ajoute une espece à la base de données
	 * 
	 * @param espece L'espece qu'on veut ajouter à la base de données
	 */
	public int ajouter(Espece espece);
	
	/**
	 * Fonction qui supprime une espece de la base de données
	 * 
	 * @param id L'identifiant de l'espece qu'on veut supprimer
	 * @return Un entier qui correspond à l'identifiant de l'espece dans la base de données
	 */
	public void supprimer(int id);
	
	/**
	 * Fonction qui modifie une espece de la base de données
	 * C'est l'identifiant de l'espece qui est utilisé pour trouver "l'ancienne" espece (qui sera 
	 * remplacée)
	 * @param espece La nouvelle espece qui doit remplacer l'ancienne
	 */
	public void mettreAJour(Espece espece);
	
	/**
	 * Fonction qui sauvegarde les eventuelles modifications faites sur la base de données
	 */
	public void enregistrerModifications();
	
	/**
	 * Fonction qui permet de recuperer des eventuelles erreurs en particulier après la création de l'objet
	 * @return Une liste de chaine de caractère, chaque chaine représente une erreur
	 */
	public ArrayList<String> recupererErreurs();
	
	
	public static void main(String[] args) {
	}
}
