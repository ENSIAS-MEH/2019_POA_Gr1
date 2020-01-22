package dao;

import java.io.File;
import java.io.IOException;

/**
 * Cette classe represente une factory d'objet CSVEspeceDAO.
 * Le pattern singleton est utilisé.
 *
 */
public class CSVConnectionFactory implements ConnectionFactory {
	protected File fichier; // Le fichier csv
	private static CSVConnectionFactory instance;
	
	private CSVConnectionFactory(File fichier) {
		this.fichier = fichier;
	}
	
	/**
	 *  Retourne le fichier csv
	 * @return le fichier csv
	 */
	public File getFile() {
		return fichier;
	}
	
	/**
	 * Retourne l'objet CSVConnectionFactory. Si l'objet existe, il est renvoyé. Sinon un nouvel objet est
	 * crée.  
	 * @param fichier Le fichier csv
	 * @return Un objet CSVConnectionFactory
	 */
	public static ConnectionFactory getInstance(File fichier) {
		if (instance == null) {
			instance = new CSVConnectionFactory(fichier);
		}
		return instance;
	}
	
	public EspeceDAO getEspeceDAO() {
		try {
			return new CSVEspeceDAO(fichier);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
