package dao;

/**
 * Classe qui repr�sente une exception lev�e lorsque qu'un fichier csv n'a pas la forme requise
 *
 *
 */
public class FormeIncorrecteException extends Exception {

	private static final long serialVersionUID = 1L;
	private int ligne;

	/**
	 * Retourne un exception pour une forme incorrecte du fichier
	 * @param message Un message qui explique l'erreur
	 */
	public FormeIncorrecteException(String message,int ligne) {
		super(message);
		this.ligne = ligne; // La ligne ou l'erreur s'est produite
	}
	
	/**
	 * Retourne la ligne du fichier malform�e
	 * @return Le num�ro de la ligne qui pose probleme
	 */
	public int getLigne() {
		return ligne;
	}

}
