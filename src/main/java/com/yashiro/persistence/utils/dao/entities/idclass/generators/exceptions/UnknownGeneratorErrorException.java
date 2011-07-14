/**
 * 
 */
package com.yashiro.persistence.utils.dao.entities.idclass.generators.exceptions;

import com.yashiro.persistence.utils.annotations.validator.engine.exceptions.BaseJPersistenceUtilsException;

/**
 * Classe repr�sentant l'exception g�n�r�e lorsqu'une erreur inconnue survient
 * durant la g�n�ration d'Identifiant
 * @author Jean-Jacques
 * @version 1.0
 */
public class UnknownGeneratorErrorException extends BaseJPersistenceUtilsException{

	/**
	 * ID genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructeur par defaut
	 */
	public UnknownGeneratorErrorException(Throwable e) {
		
		// Appel Parent
		super("generator.unkowngeneratorerror", e);
	}
}
