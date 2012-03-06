/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.bulksoft.persistence.utils.annotations.validator.engine;

import java.util.regex.Pattern;

import org.hibernate.validator.Validator;

import com.bulksoft.persistence.utils.annotations.validator.Email;

/**
 * Implementation de la regle de validation Email
 * @author Jean-Jacques
 * @version 1.0
 */
public class EmailRule implements Validator<Email> {
	
	/**
	 * Constante de construction du Pattern d'Email
	 */
	private static String ATOM = "[^\\x00-\\x1F^\\(^\\)^\\<^\\>^\\@^\\,^\\;^\\:^\\\\^\\\"^\\.^\\[^\\]^\\s]";
	private static String DOMAIN = "(" + ATOM + "+(\\." + ATOM + "+)*";
	private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";
	
	/**
	 * Etat de contole de la chaine vide
	 */
	private boolean matchOnEmpty = true;
	
	/**
	 * Pattern des Mails
	 */
	private Pattern pattern = null;
	
	/*
	 * (non-Javadoc)
	 * @see org.hibernate.validator.EmailValidator#initialize(org.hibernate.validator.Email)
	 */
	public void initialize(Email email) {
		
		// Initialisation de l'Etat de Controle de la chaine vide
		this.matchOnEmpty = email.matchOnEmpty();
		
		// Construction du pattern des mails
		pattern = Pattern.compile(
				"^" + ATOM + "+(\\." + ATOM + "+)*@"
				+ DOMAIN
				+ "|"
				+ IP_DOMAIN
				+ ")$",
				java.util.regex.Pattern.CASE_INSENSITIVE
		);
	}

	@Override
	public boolean isValid(Object value) {

		// Si l'objet est null : false
		if(value == null) return matchOnEmpty;

		// Si l'objet n'est pas une chaine : false
		if(!(value instanceof String)) return false;
		
		// On caste
		String stringValue = (String) value;

		// Si la chaîne est vide : false
		if(stringValue == null || stringValue.length() == 0) return matchOnEmpty;
		
		// On retourne le matching
		return pattern.matcher(stringValue).matches();
	}

}
