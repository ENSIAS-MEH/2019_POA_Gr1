package dao;

/**
 * Classe qui représente une exception levée lorsque l'un des champs de l'objet Espece est incorrect
 *
 *
 */
public class ChampIncorrectException extends Exception {

	private static final long serialVersionUID = -3749295021195737442L;

	/**
	 * Retourne un exception pour un champ incorrect de l'objet Espece
	 * @param message Un message qui explique l'erreur
	 */
	public ChampIncorrectException(String message){
		super(message);
	}

}
