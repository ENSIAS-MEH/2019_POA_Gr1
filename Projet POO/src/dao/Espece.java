package dao;

import java.util.ArrayList;
import java.util.Objects;

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
	public Espece(int id, String nom, String genre, String famille, String ordre,
			String classe, String embranchement, String description, String groupeTrophique,
			String groupeEcologique, String categorieImportance,String cheminImageDisque,String cheminImageOriginale,ArrayList<String> synonymes) {
		this.id = id;
		this.cheminImageDisque = cheminImageDisque;
		this.cheminImageOriginale = cheminImageOriginale;
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

	@Override
	public boolean equals(Object obj) {
		/*
		 * Fonction qui vérifie si obj est égal (du point de vue attribut ) à l'objet actuel.
		 * Auto-générée
		 */

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Espece)) {
			return false;
		}
		Espece other = (Espece) obj;
		return Objects.equals(categorieImportance, other.categorieImportance)
				&& Objects.equals(cheminImageDisque, other.cheminImageDisque)
				&& Objects.equals(cheminImageOriginale, other.cheminImageOriginale)
				&& Objects.equals(classe, other.classe) && Objects.equals(description, other.description)
				&& Objects.equals(embranchement, other.embranchement) && Objects.equals(famille, other.famille)
				&& Objects.equals(genre, other.genre) && Objects.equals(groupeEcologique, other.groupeEcologique)
				&& Objects.equals(groupeTrophique, other.groupeTrophique) && id == other.id
				&& Objects.equals(nom, other.nom) && Objects.equals(ordre, other.ordre)
				&& Objects.equals(synonymes, other.synonymes);
	}

	protected int id;
	protected String cheminImageDisque;
	protected String cheminImageOriginale;
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

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @param embranchement the embranchement to set
	 */
	public void setEmbranchement(String embranchement) {
		this.embranchement = embranchement;
	}

	/**
	 * @param classe the classe to set
	 */
	public void setClasse(String classe) {
		this.classe = classe;
	}

	/**
	 * @param famille the famille to set
	 */
	public void setFamille(String famille) {
		this.famille = famille;
	}

	/**
	 * @param ordre the ordre to set
	 */
	public void setOrdre(String ordre) {
		this.ordre = ordre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @param synonymes the synonymes to set
	 */
	public void setSynonymes(ArrayList<String> synonymes) {
		this.synonymes = synonymes;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param groupeTrophique the groupeTrophique to set
	 */
	public void setGroupeTrophique(String groupeTrophique) {
		this.groupeTrophique = groupeTrophique;
	}

	/**
	 * @param groupeEcologique the groupeEcologique to set
	 */
	public void setGroupeEcologique(String groupeEcologique) {
		this.groupeEcologique = groupeEcologique;
	}

	/**
	 * @param categorieImportance the categorieImportance to set
	 */
	public void setCategorieImportance(String categorieImportance) {
		this.categorieImportance = categorieImportance;
	}

	/**
	 * @return the cheminImageDisque
	 */
	public String getCheminImageDisque() {
		return cheminImageDisque;
	}

	/**
	 * @param cheminImageDisque the cheminImageDisque to set
	 */
	public void setCheminImageDisque(String cheminImageDisque) {
		this.cheminImageDisque = cheminImageDisque;
	}

	/**
	 * @return the cheminImageOriginale
	 */
	public String getCheminImageOriginale() {
		return cheminImageOriginale;
	}

	/**
	 * @param cheminImageOriginale the cheminImageOriginale to set
	 */
	public void setCheminImageOriginale(String cheminImageOriginale) {
		this.cheminImageOriginale = cheminImageOriginale;
	}
}