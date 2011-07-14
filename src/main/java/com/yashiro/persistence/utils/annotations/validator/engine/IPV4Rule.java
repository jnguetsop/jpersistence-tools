/**
 * 
 */
package com.yashiro.persistence.utils.annotations.validator.engine;

import java.util.regex.Pattern;

import org.hibernate.validator.Validator;

import com.yashiro.persistence.utils.annotations.validator.IPV4;

/**
 * Regle de validation de l'IPV4
 * @author Jean-Jacques
 * @version 2.0
 */
public class IPV4Rule implements Validator<IPV4> {

	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(IPV4 annotation) {}

	/* (non-Javadoc)
	 * @see org.hibernate.validator.Validator#isValid(java.lang.Object)
	 */
	@Override
	public boolean isValid(Object value) {
		
		// Si l'objet est null : false
		if(value == null) return false;
		
		// Si l'objet n'est pas une chaine : false
		if(!(value instanceof String)) return false;
		
		// On caste
		String stringValue = (String) value;
		
		// Si la chaine est vide : false
		if(stringValue == null || stringValue.length() == 0) return false;
		
		// Obtention du tableau de valeurs
		String[] parts = stringValue.split("\\.");
		
		// Si la tableau n'a pas une taille de 4
		if(parts == null || parts.length < 4) return false;
		
		// Pattern Numerique
		Pattern pattern = Pattern.compile("\\d+");
		
		// Parcours du tableau
		for (String part : parts) {

			// Si ce n'est pas un Numerique
			if(!pattern.matcher(part).matches()) return false;
		}
		
		// On retourne true
		return true;
	}

}
