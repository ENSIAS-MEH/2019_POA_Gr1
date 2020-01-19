package dao;

import java.io.File;
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
	 * Fonction qui renvoie toutes les especes vérifiant les critères précisés
	 * ----
	 * @return Une liste contenant toutes les especes vérifiant les critères précisés
	 */
	public ArrayList<Espece> filtrer();
	
	/**
	 * Fonction qui ajoute une espece à la base de données
	 * 
	 * @param espece L'espece qu'on veut ajouter à la base de données
	 */
	public void ajouter(Espece espece);
	
	/**
	 * Fonction qui supprime une espece de la base de données
	 * 
	 * @param id L'identifiant de l'espece qu'on veut supprimer
	 */
	public void supprimer(int id);
	
	/**
	 * Fonction qui modifie une espece de la base de données
	 * C'est l'identifiant de l'espece qui est utilisé pour trouver "l'ancienne" espece (qui sera 
	 * remplacée)
	 * @param espece La nouvelle espece qui doit remplacer l'ancienne
	 */
	void mettreAJour(Espece espece);
	
	/**
	 * Fonction qui sauvegarde les eventuelles modifications faites sur la base de données
	 */
	void enregistrerModifications();
	
	public static void main(String[] args) {
	}
}
