package dao;

import java.io.File;
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
	 * Fonction qui renvoie toutes les especes v�rifiant les crit�res pr�cis�s
	 * ----
	 * @return Une liste contenant toutes les especes v�rifiant les crit�res pr�cis�s
	 */
	public ArrayList<Espece> filtrer();
	
	/**
	 * Fonction qui ajoute une espece � la base de donn�es
	 * 
	 * @param espece L'espece qu'on veut ajouter � la base de donn�es
	 */
	public void ajouter(Espece espece);
	
	/**
	 * Fonction qui supprime une espece de la base de donn�es
	 * 
	 * @param id L'identifiant de l'espece qu'on veut supprimer
	 */
	public void supprimer(int id);
	
	/**
	 * Fonction qui modifie une espece de la base de donn�es
	 * C'est l'identifiant de l'espece qui est utilis� pour trouver "l'ancienne" espece (qui sera 
	 * remplac�e)
	 * @param espece La nouvelle espece qui doit remplacer l'ancienne
	 */
	void mettreAJour(Espece espece);
	
	/**
	 * Fonction qui sauvegarde les eventuelles modifications faites sur la base de donn�es
	 */
	void enregistrerModifications();
	
	public static void main(String[] args) {
	}
}
