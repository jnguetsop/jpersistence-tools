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
package com.bulk.persistence.tools.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * Interface de la DAO Generique compatible JPA
 * @author Jean-Jacques ETUNÈ NGI
 */
public interface JPAGenericDAO<T extends Object> {
	
	/**
	 * Nom du Service DAO
	 */
	public static final String SERVICE_NAME = "JPAGenericDAO";
	
	
	/**
	 * Méthode de mise à jour de l'Etat de validation des constraintes d'integrites en mode SAVE
	 * @param validateIntegrityConstraint Etat de validation des constraintes d'integrites en mode SAVE
	 */
	public void setValidateIntegrityConstraintOnSave(boolean validateIntegrityConstraintOnSave);
	
	/**
	 * Méthode de mise à jour de l'Etat de validation des constraintes d'integrites en mode UPDATE
	 * @param validateIntegrityConstraint Etat de validation des constraintes d'integrites en mode UPDATE
	 */
	public void setValidateIntegrityConstraintOnUpdate(boolean validateIntegrityConstraintOnUpdate);

	/**
	 * Méthode de mise à jour de l'Etat de pré-validation des contraintes referentielles en mode SAVE
	 * @param preValidateReferentialConstraintOnSave Etat de pré-validation des contraintes referentielles en mode SAVE
	 */
	public void setPreValidateReferentialConstraintOnSave(boolean preValidateReferentialConstraintOnSave);

	/**
	 * Méthode de mise à jour de l'Etat de post-validation des contraintes referentielles en mode SAVE
	 * @param validateReferentialConstraint Etat de postvalidation des contraintes referentielles en mode SAVE
	 */
	public void setPostValidateReferentialConstraintOnSave(boolean postValidateReferentialConstraintOnSave);

	/**
	 * Méthode de mise à jour de l'Etat de pré-validation des contraintes referentielles en mode UPDATE
	 * @param preValidateReferentialConstraintOnUpdate Etat de pré-validation des contraintes referentielles en mode UPDATE
	 */
	public void setPreValidateReferentialConstraintOnUpdate(boolean preValidateReferentialConstraintOnUpdate);

	/**
	 * Méthode de mise à jour de l'Etat de post-validation des contraintes referentielles en mode UPDATE
	 * @param postValidateReferentialConstraintOnUpdate Etat de postvalidation des contraintes referentielles en mode UPDATE
	 */
	public void setPostValidateReferentialConstraintOnUpdate(boolean postValidateReferentialConstraintOnUpdate);

	/**
	 * Méthode de mise à jour de l'Etat de pré-validation des contraintes referentielles en mode DELETE
	 * @param preValidateReferentialConstraintOnDelete Etat de pré-validation des contraintes referentielles en mode DELETE
	 */
	public void setPreValidateReferentialConstraintOnDelete(boolean preValidateReferentialConstraintOnDelete);

	/**
	 * Méthode de mise à jour de l'Etat de post-validation des contraintes referentielles en mode DELETE
	 * @param postValidateReferentialConstraintOnDelete Etat de postvalidation des contraintes referentielles en mode DELETE
	 */
	public void setPostValidateReferentialConstraintOnDelete(boolean postValidateReferentialConstraintOnDelete);
	
	/**
	 * Methode generique d'enregistrement d'une entite JPA annotee
	 * @param entity	Entite a enregistrer
	 * @return	Entite enregistree
	 */
	public T save(T entity);

	/**
	 * Methode generique d'enregistrement d'une entite JPA annotee
	 * @param entity	Entite a enregistrer
	 * @param validateIntegrityConstraint Etat de validation des contraintes d'integrites
	 * @param preValidateReferentialConstraint Etat de pré-validation des contraintes référentielles
	 * @param postValidateReferentialConstraint Etat de post-validation des contraintes référentielles
	 * @return	Entite enregistree
	 */
	public T save(T entity, boolean validateIntegrityConstraint, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint);
	
	/**
	 * Methode generique de mise a jour d'une entite JPA annotee
	 * @param entity	Entite a mettre a jour
	 * @return	Entite mise a jour
	 */
	public T update(T entity);

	/**
	 * Methode generique de mise a jour d'une entite JPA annotee
	 * @param entity	Entite a mettre a jour
	 * @param validateIntegrityConstraint Etat de validation des contraintes d'integrites
	 * @param preValidateReferentialConstraint Etat de pré-validation des contraintes référentielles
	 * @param postValidateReferentialConstraint Etat de post-validation des contraintes référentielles
	 * @return	Entite mise a jour
	 */
	public T update(T entity, boolean validateIntegrityConstraint, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint);
	
	/**
	 * Methode generique de suppression d'une entite JPA annotee
	 * @param entityID	Identifiant de l'entité à supprimer
	 */
	public void delete(Object entityID);

	/**
	 * Methode generique de suppression d'une entite JPA annotee
	 * @param entityID	Identifiant de l'entité à supprimer
	 * @param preValidateReferentialConstraint Etat de pré-validation des contraintes référentielles
	 * @param postValidateReferentialConstraint Etat de post-validation des contraintes référentielles
	 */
	public void delete(Object entityID, boolean preValidateReferentialConstraint, boolean postValidateReferentialConstraint);
	
	/**
	 * Methode de nettoyage de la table de l'entité
	 */
	public void clean();
	
	/**
	 * Methode de filtre des entites d'une classe donnee en fonction des criteres de filtres donnees
	 * @param predicates	Liste des prédicats
	 * @param orders	Liste des Ordre de tri
	 * @param properties	Ensemble de propriétés à charger
	 * @param firstResult	Index du premier resultat retourne
	 * @param maxResult	Nombre maximum d'elements retournes
	 * @return	Liste des objet trouves
	 */
	public List<T> filter(List<Predicate> predicates, List<Order> orders, Set<String> properties, int firstResult, int maxResult);
	
	/**
	 * Methode de chargement immediat des proprietes d'une instance de classe
	 * @param entityIDName Nom de la propriété ID de l'entité
	 * @param entityID	ID de l'instance de l'entite
	 * @param properties	Ensemble de proprietes a charger
	 * @return	Instance de la classe avec les proprietes charges
	 */
	public T findByPrimaryKey(String entityIDName, Object entityID, HashSet<String>  properties);
		
	/**
	 * Methode d'obtention du gestionnaire d'entites
	 * @return	Gestionnaire d'entites
	 */
	public EntityManager getEntityManager();

	/**
	 * Méthode d'obtention de la classe de l'entité gérée par la DAO
	 * @return	Classe de l'entité gérée par la DAO
	 */
	public Class<T> getManagedEntityClass();
	
	/**
	 * Méthode de construction d'un chemin de propriété à partir de la racine
	 * @param stringPath	Chemin sous forme de chaine
	 * @return	Chemin recherché sous forme Path
	 */
	public <Y extends Comparable<Y>> Path<Y> buildPropertyPath(String stringPath);
	
	/**
	 * Méthode de construction du predicat "equal"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate eq(String property, Y value);

	/**
	 * Méthode de construction du predicat "notEqual"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate notEq(String property, Y value);
	
	/**
	 * Méthode de construction du predicat "greaterThan"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate gt(String property,  Y value);
	
	/**
	 * Méthode de construction du predicat "greaterThanOrEqualTo"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate ge(String property,  Y value);

	/**
	 * Méthode de construction du predicat "lessThan"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate lt(String property,  Y value);

	/**
	 * Méthode de construction du predicat "lessThanOrEqualTo"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate le(String property,  Y value);

	/**
	 * Méthode de construction du predicat "like"
	 * @param property	chemin de la proriété à comparer
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public Predicate like(String property,  String pattern);

	/**
	 * Méthode de construction du predicat "notLike"
	 * @param property	Chemim de la propriete
	 * @param value	Valeur de comparaison
	 * @return	Predicat construit
	 */
	public Predicate notLike(String property,  String pattern);

	/**
	 * Méthode de construction du predicat "between"
	 * @param <Y>	Parametre de type de la valeur a comparer
	 * @param property	Chemim de la propriete a comparer
	 * @param minValue	Borne inferieure
	 * @param maxValue	Borne superieure
	 * @return Predicat construit
	 */
	public <Y extends Comparable<Y>> Predicate between(String property,  Y minValue, Y maxValue);

	/**
	 * Méthode de construction du predicat "isNull"
	 * @param property	Chemim de la propriete a comparer
	 * @return Predicat construit
	 */
	public Predicate isNull(String property);

	/**
	 * Méthode de construction du predicat "isNotNull"
	 * @param property	Chemim de la propriete a comparer
	 * @return Predicat construit
	 */
	public Predicate isNotNull(String property);

	/**
	 * Méthode de construction du predicat "isTrue"
	 * @param property	Chemim de la propriete a comparer
	 * @return Predicat construit
	 */
	public Predicate isTrue(String property);

	/**
	 * Méthode de construction du predicat "isFalse"
	 * @param property	Chemim de la propriete a comparer
	 * @return Predicat construit
	 */
	public Predicate isFalse(String property);
}