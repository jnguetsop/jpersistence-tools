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
package com.yashiro.persistence.utils.annotations.validator.engine;

import javax.persistence.EntityManager;

/**
 * Interface definissant le contrat des classes de validation des contraintes
 * d'integrites et referentielles sur un objet metier
 * @author Jean-Jacques
 * @version 1.0
 */
public interface IJPAConstraintValidator {
	
	/**
	 * Initialisation du Validateur
	 */
	public void init(EntityManager entityManager, Object entity);
	
	/**
	 * Methode de valiodation des contraintes d'integrites
	 */
	public void validateIntegrityConstraint();
	
	/**
	 * Methode de validation des contraintes referentielles
	 */
	public void validateReferentialConstraint();
}
