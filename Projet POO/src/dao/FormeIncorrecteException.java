package dao;

public class FormeIncorrecteException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Retourne un exception pour une forme incorrecte du fichier
	 * @param message Un message qui explique l'erreur
	 */
	public FormeIncorrecteException(String message) {
		super(message);
	}

}
