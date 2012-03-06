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

import javax.persistence.EntityManager;

import com.bulksoft.persistence.utils.annotations.validator.IdentityDAOValidator;
import com.bulksoft.persistence.utils.dao.constant.DAOMode;
import com.bulksoft.persistence.utils.dao.constant.DAOValidatorEvaluationTime;

/**
 * Classe d'implementation de la regle de validation Identity
 * @author Jean-Jacques
 * @version 2.0
 */
public class IdentityDAOValidatorRule implements IDAOValidator<IdentityDAOValidator> {

	@Override
	public void initialize(IdentityDAOValidator annotation, EntityManager entityManager, DAOMode mode, DAOValidatorEvaluationTime evaluationTime) {}

	@Override
	public void processValidation(Object entity) {}

	@Override
	public Object[] getMessageParameters(Object entity) {
		
		// On retourne null
		return null;
	}

}
