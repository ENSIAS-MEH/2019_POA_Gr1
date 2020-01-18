package dao;

import java.util.ArrayList;

/**
 * Cette classe représente un objet espece. Les differents attributs sont les caractéristiques
 * de l'espece.
 *
 */
public class Espece {

	
	/**
	 * Crée un objet espece avec les différentes valeurs spécifiées
	 * 
	 */
	public Espece(int id, String nom, String embranchement, String classe, String ordre,
			String famille, String genre, String description, String groupeTrophique,
			String groupeEcologique, String categorieImportance,String cheminImage,ArrayList<String> synonymes) {
		this.id = id;
		this.cheminImage = cheminImage;
		this.nom = nom;
		this.embranchement = embranchement;
		this.classe = classe;
		this.famille = famille;
		this.ordre = ordre;
		this.genre = genre;
		this.synonymes = synonymes;
		this.description = description;
		this.groupeTrophique = groupeTrophique;
		this.groupeEcologique = groupeEcologique;
		this.categorieImportance = categorieImportance;
	}
	
	protected int id;
	protected String cheminImage;
	protected String nom;
	protected String embranchement;
	protected String classe;
	protected String famille;
	protected String ordre;
	protected String genre;
	protected ArrayList<String> synonymes = new ArrayList<String>();
	protected String description;
	protected String groupeTrophique;
	protected String groupeEcologique;
	protected String categorieImportance;
	public int getId() {
		return id;
	}
	public String getCheminImage() {
		return cheminImage;
	}
	public String getNom() {
		return nom;
	}
	public String getEmbranchement() {
		return embranchement;
	}
	public String getClasse() {
		return classe;
	}
	public String getFamille() {
		return famille;
	}
	public String getOrdre() {
		return ordre;
	}
	public String getGenre() {
		return genre;
	}
	public ArrayList<String> getSynonymes() {
		return synonymes;
	}
	public String getDescription() {
		return description;
	}
	public String getGroupeTrophique() {
		return groupeTrophique;
	}
	public String getGroupeEcologique() {
		return groupeEcologique;
	}
	public String getCategorieImportance() {
		return categorieImportance;
	}
	public void setId(int id) {
		this.id = id;
		
	}
}