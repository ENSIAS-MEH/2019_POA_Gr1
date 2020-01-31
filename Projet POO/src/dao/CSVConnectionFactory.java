package dao;

import java.io.File;
import java.io.IOException;

/**
 * Cette classe represente une factory d'objet CSVEspeceDAO
 *
 */
public class CSVConnectionFactory implements ConnectionFactory {
	protected File fichier; // Le fichier csv
	
	/**
	 * Constructeur de CSVConnectionFactory
	 * @param fichier Le fichier csv qui doit être lu
	 */
	public CSVConnectionFactory(File fichier) {
		this.fichier = fichier;
	}
	
	/**
	 *  Retourne le fichier csv
	 * @return le fichier csv
	 */
	public File getFile() {
		return fichier;
	}
	
	public EspeceDAO getEspeceDAO() {
		try {
			return new CSVEspeceDAO(fichier);
		} catch (IOException e) {
			return null;
		}	
	}

}
