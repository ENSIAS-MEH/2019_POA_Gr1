package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Cette classe repr�sente un objet espece. Les differents attributs sont les caract�ristiques
 * de l'espece.
 *
 */
public class Espece {
	
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
	protected int zone;
	
	// Les variables statiques suivantes servent au controle des attributs de l'objet

	protected static ArrayList<String> listeGroupeTrophique = new ArrayList<String>(Arrays.asList("carnivore","necrophage","herbivore","detritivore",
			"suspensivore","deposivore selectif","deposivore non selectif","microbrouteur"));
	protected static ArrayList<String> listeGroupeEcologique = new ArrayList<String>(Arrays.asList("sensible","indifferente","tolerante",
			"opportuniste de 2e ordre","opportuniste de 1er ordre"));
	protected static int minZone = 0, maxZone = 3;

	

	/**
	 * Cr�e un objet espece avec les diff�rentes valeurs sp�cifi�es. <br>
	 * Pour connaitre les valeurs correctes, voir {@link dao.Espece#getListeGroupeEcologique} 
	 * pour le groupe ecologique, {@link dao.Espece#getListeGroupeTrophique} pour le groupe trophique et 
	 * {@link dao.Espece#getIntervalleZone} pour la zone.
	 * @throws ChampIncorrectException Si l'un des champs a une valeur incorrecte
	 * 
	 */
	public Espece(int id, String nom, String genre, String famille, String ordre,
			String classe, String embranchement, String description, String groupeTrophique,
			String groupeEcologique, String categorieImportance,String zone,String cheminImageDisque,
			String cheminImageOriginale,ArrayList<String> synonymes) throws ChampIncorrectException {

		// On v�rifie que les groupes ecologique et trophique sont bons
		String erreurGroupes = estCorrectTrophique(groupeTrophique) + " ; " + estCorrectEcologique(groupeEcologique);
		if (!erreurGroupes.equals(" ; ")) 
			throw new ChampIncorrectException(erreurGroupes);
		
		// On verifie que la zone est bien un entier
		try {
			this.zone = Integer.parseInt(zone);
		} catch (NumberFormatException e) {
			throw new ChampIncorrectException("La valeur << " + zone + " >> pour la zone est incorrecte "
					+ ": un entier est attendu");
		}
		
		// On verifie que l'intervalle est le bon
		if (this.zone > maxZone || this.zone < minZone)
			throw new ChampIncorrectException("L'entier " + zone + " pour la zone est incorrect : "
					+ "il doit �tre entre "+minZone+" et "+maxZone);
		
		this.id = id;
		// On garde le meme nom pour l'image
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
	
	/**
	 * Cr�e un objet espece avec le champ cheminImageDisque vide.
	 * 
	 * @see dao.Espece#Espece(int, String, String, String, String, String, String, String, String, String, String, String, String, String, ArrayList)
	 * 
	 */
	public Espece(int id, String nom, String genre, String famille, String ordre,
			String classe, String embranchement, String description, String groupeTrophique,
			String groupeEcologique, String categorieImportance,String zone,
			String cheminImageOriginale,ArrayList<String> synonymes) throws ChampIncorrectException {
		this(id, nom, genre, famille, ordre, classe, embranchement, description, groupeTrophique, 
				groupeEcologique, categorieImportance, zone, "", cheminImageOriginale, synonymes);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(categorieImportance, cheminImageDisque, cheminImageOriginale, classe, description,
				embranchement, famille, genre, groupeEcologique, groupeTrophique, id, nom, ordre, synonymes, zone);
	}

	@Override
	public boolean equals(Object obj) {
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
				&& Objects.equals(synonymes, other.synonymes) && zone == other.zone;
	}


	/**
	 * Verifie si la chaine de caractere est une valeur correcte pour le groupe trophique
	 * 
	 * @param gTro La chaine de caractere dont on veut verifier la validit�
	 * @return Une chaine de caractere qui est soit vide (pas d'erreurs) soit contenant le message d'erreur
	 * (non vide)
	 */
	public static String estCorrectTrophique(String gTro){
		if (listeGroupeTrophique.contains(gTro))
			return "";
		return "Le groupe trophique doit �tre dans la liste : "
		+ listeGroupeTrophique.toString();
		
	}
	
	/**
	 * Verifie si la chaine de caractere est une valeur correcte pour le groupe ecologique
	 * 
	 * @param gTro La chaine de caractere dont on veut verifier la validit�
	 * @return Une chaine de caractere qui est soit vide (pas d'erreurs) soit contenant le message d'erreur
	 * (non vide)
	 */
	public static String estCorrectEcologique(String gEco){
		if (listeGroupeEcologique.contains(gEco))
			return "";
		return "Le groupe �cologique doit �tre dans la liste : "
		+ listeGroupeEcologique.toString();
	}
	
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
	
	/**
	 * Renvoie la liste des groupes trophiques valides pour l'objet Espece
	 * @return Une liste contenant les valeurs valides
	 */
	public static ArrayList<String> getListeGroupeTrophique(){
		return listeGroupeTrophique;
	}
	
	/**
	 * Renvoie la liste des groupes ecologiques valides pour l'objet Espece
	 * @return Une liste contenant les valeurs valides
	 */
	public static ArrayList<String> getListeGroupeEcologique(){
		return listeGroupeEcologique;
	}
	/**
	 * Renvoie les valeurs extremes (bornes incluses) que peut prendre l'attribut zone
	 * @return un tableau de 2 entiers : le 1er est le minimum, le 2e est le maximum
	 */
	public static int[] getIntervalleZone() {
		return new int[] {minZone,maxZone};
	}
	
	public int getZone() {
		return zone;
	}
	
	/**
	 * 
	 * @param zone the zone to set
	 */
	public void setZone(int zone) {
		this.zone = zone;
	}
	
	/**
	 * 
	 * @param id the id to set
	 */
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