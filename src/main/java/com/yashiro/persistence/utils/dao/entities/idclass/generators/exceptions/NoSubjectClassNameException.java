/**
 * 
 */
package com.yashiro.persistence.utils.dao.entities.idclass.generators.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe repr�sentant l'exception g�n�r�e lorsque le nom de la classe en cours 
 * de g�n�ration d'identifiant n'est pas renseign�e
 * @author Jean-Jacques
 * @version 1.0
 */
public class NoSubjectClassNameException extends BaseJPersistenceUtilsException {

	/**
	 * ID genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public NoSubjectClassNameException() {
		
		// Appel Parent
		super("generator.nosubjectclassnamedefined");
	}
}
