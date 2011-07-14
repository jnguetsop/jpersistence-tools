/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

import com.yashiro.persistence.utils.annotations.validator.engine.AuthorizedStringValuesListRule;

/**
 * Annotation de la r�gle de validation permettant de contr�ler que la valeur d'une propri�t�
 * se trouve bien dans une liste de cha�nes donn�e
 * @author Jean-Jacques
 * @version 1.0
 */
@ValidatorClass(value = AuthorizedStringValuesListRule.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorizedStringValuesList {
	
	/**
	 * Methode d'obtention du message a afficher lors du non respect de la r�gle
	 * @return	Le Message
	 */
	String message() default "AuthorizedStringValuesList.invalidpropertyvalue";
	
	/**
	 * Methode d'obtention de la liste des Cha�ne authorisees
	 * @return	Liste des Cha�nes authorisees
	 */
	String[] values();
	
	/**
	 * Methode permettant de savoir si la casse est prise en compte
	 * @return Etat de prise en compte de la casse
	 */
	boolean caseSensitive() default false;
}
