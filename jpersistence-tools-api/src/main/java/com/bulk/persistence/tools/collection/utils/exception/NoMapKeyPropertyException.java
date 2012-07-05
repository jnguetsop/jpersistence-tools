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
package com.bulk.persistence.tools.collection.utils.exception;

import com.bulk.persistence.tools.api.exceptions.JPersistenceToolsException;


/**
 * Exception exprimant l'absence de la propriete Cle lors de la conversion d'une Collection en MAP
 * @author Jean-Jacques ETUNÈ NGI
 */
public class NoMapKeyPropertyException extends JPersistenceToolsException {
	
	/**
	 * ID Genere par Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur par defaut
	 */
	public NoMapKeyPropertyException() {
		
		// Initialisation du Parent
		super("NoMapKeyPropertyException.message.code");
	}
}