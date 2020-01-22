package dao;

/**
 * 
 * Interface d'une factory qui permet de recuperer le DAO correspond � la factory
 *
 */
public interface ConnectionFactory {
	
	/**
	 * Retourne un objet EspeceDAO qui permet de construire les TO (TransferObjetct).
	 * @return Un EspeceDAO pour construire des objets especes et modifier les donn�es. <br>
	 * La fonction renvoie null en cas de probleme d'instanciation
	 */
	public EspeceDAO getEspeceDAO();

}
