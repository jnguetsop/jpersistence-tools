package com.yashiro.persistence.utils.dao.entities.idclass.generators;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.sql.Insert;
import org.hibernate.sql.Update;
import org.hibernate.type.CharacterType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;
import org.hibernate.util.PropertiesHelper;

import com.yashiro.persistence.utils.dao.entities.idclass.generators.exceptions.NoSubjectClassNameException;
import com.yashiro.persistence.utils.dao.entities.idclass.generators.exceptions.UnknownGeneratorErrorException;

/**
 * Classe de generation d'un ID auto incremente de type Long par Classe
 * @author Jean-Jacques
 * @version 1.0
 */
public class LongKeyClassGenerator implements IdentifierGenerator, Configurable {

	/**
	 *  Map contenant les derni�res valeurs g�n�r�e par Classe persistantes
	 */
	protected static Map<String, Long> generatedValues = new HashMap<String, Long>();
	
	/**
	 *  La Table de g�n�ration
	 */
	protected static String generatorTable;
	
	/**
	 *  La Classe concern�e par la g�n�ration
	 */
	protected static String subject;
	
	/**
	 *  La Colonne contenant la derni�re valeur d'index g�n�r�e pour cette classe
	 */
	protected static String lastGeneratedValueColumn;
	
	/**
	 *  La Colonne contenant le nom de la classe pour laquelle on veut g�n�rer l'identifiant
	 */
	protected static String subjectClassNameColumn; 
	
	/**
	 *  Dernier index g�n�r� pour la classe en cours
	 */
	protected Long lastGeneratedValue = 0L;
	
	/**
	 *  Requ�te de recherche de la derni�re valeur g�n�r�e
	 */
	protected String lastValueLoadRequest;
	
	/**
	 *  Requ�te de recherche du dernier ID Genere
	 */
	protected String idLoadRequest;
	
	/**
	 *  Requ�te de mise � jour
	 */
	protected String lastValueUpdateRequest;
	
	/**
	 *  Le Dialect
	 */
	protected Dialect dialect;
	
	/**
	 *  La classe de generation du prefixe
	 */
	private String prefixGeneratorClassName;

	/**
	 *  Generateur de prefixe
	 */
	private IPrefixGenerator prefixGenerator = null;
	
	/**
	 *  Le Prefix
	 */
	protected String prefix = "";
	
	/**
	 *  Un Logger
	 */
	protected static Log logger = LogFactory.getLog(StringKeyClassGenerator.class);
	
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		
		// Un Log
		logger.debug("LongKeyClassGenerator#generate");
		
		// Obtention du nom de la classe pour laquelle la g�n�ration doit �tre effectu�e
		subject = object.getClass().getName();
		
		// Si le nom de la classe est vide : Exception
		if(subject == null || subject.trim().length() == 0) throw new NoSubjectClassNameException();
		
		// D�but de la G�n�ration
		logger.debug("LongKeyClassGenerator#generate - BEGIN ID GENERATION [For " + subject + "]");
		
		// Logs
		logger.debug("LongKeyClassGenerator#generate - Generator Class : " + this.getClass().getName());
		logger.debug("LongKeyClassGenerator#generate - Subject Class   : " + subject);
		logger.debug("LongKeyClassGenerator#generate - Generator Table : " + generatorTable);
		logger.debug("LongKeyClassGenerator#generate - Discriminator   : " + subjectClassNameColumn);
		logger.debug("LongKeyClassGenerator#generate - Value Column    : " + lastGeneratedValueColumn);
		logger.debug("LongKeyClassGenerator#generate - Search of Last Generated ID...");
				
		// Requ�te de recherche de la Derni�re valeur g�n�r�e en m�moire
		lastGeneratedValue = generatedValues.get(subject);

		// Chargement du PrefixGenerator
		prefixGenerator = loadPrefixGenerator();
		
		// Une session Hibernate
		Session s;
		
		// Etat de non trouvaille
		boolean entryNotFound = false;
		
		// La cl�
		Long key = 0L;
		
		try {

			// Initialisation de la session Hibernate
			s = session.getFactory().openSession();

			// Initialisation du generateur de prefixe
			prefixGenerator.initialize(dialect, s);
			
			// Generation du Prefixe
			Object prefixObject = prefixGenerator.generate(object);
			
			// Si l'Objet Prefixe est null
			if(prefixObject == null) prefix = "";
			
			// Sinon
			else prefix = prefixObject.toString();
			
			// Si la valeur est nulle : On recherche en BD
			if(lastGeneratedValue == null){
				
				// Un Log
				logger.debug("LongKeyClassGenerator#generate - No In Map...");
				
				// Requ�te de recherche de la derni�re valeur g�n�r�e
				lastValueLoadRequest = "select max(@ValueColumn) as value from @GeneratorTable where @DiscriminatorColumn = '@DiscriminatorValue'"
									.replaceFirst("@ValueColumn", lastGeneratedValueColumn)
									.replaceFirst("@GeneratorTable", generatorTable)
									.replaceFirst("@DiscriminatorColumn", subjectClassNameColumn)
									.replaceFirst("@DiscriminatorValue", subject);
				
				// Logs
				logger.debug("LongKeyClassGenerator#generate - Load Request: " + lastValueLoadRequest);
				
				// Ex�cution de la requ�te
				lastGeneratedValue = (Long) s.createSQLQuery(lastValueLoadRequest).addScalar("value", Hibernate.LONG).uniqueResult();
				
				// On force
				s.flush();
				
				// Si la valeur est vide : on positionne -1
				if(lastGeneratedValue == null) {
					
					// On positionne la derni�re valeur � -1
					lastGeneratedValue = -1L;
					
					// On positionne l'�tat de non trouvaille
					entryNotFound = true;
				}
			}
			
			// Incr�mentation de la derni�re valeur
			lastGeneratedValue++;
			
			// Logs
			logger.debug("LongKeyClassGenerator#generate - Last Value : " + lastGeneratedValue);
						
			// Requ�te de mise � jour
			lastValueUpdateRequest = "update";
			
			// Mise en place de la cl�
			key = Long.parseLong(prefix.concat(Long.toString(lastGeneratedValue)));
			
			// Un Log pour la cl�
			logger.debug("LongKeyClassGenerator#generate - Generated Key : " + key);

			// Si l'entr�e nest pas en BD
			if(entryNotFound) {
				
				// Requete de chargement du dernier ID
				idLoadRequest = "select max(@IDColumn) as value from @GeneratorTable"
								.replaceFirst("@IDColumn", SXKeyClassGenerationStructure.GENERATION_ID_COLUMN_NAME)
								.replaceFirst("@GeneratorTable", generatorTable);
				
				// Logs
				logger.debug("LongKeyClassGenerator#generate - Load ID Request: " + idLoadRequest);
				
				// Ex�cution de la requ�te
				Integer lastID = (Integer) s.createSQLQuery(idLoadRequest).addScalar("value", Hibernate.INTEGER).uniqueResult();
				
				// On force
				s.flush();

				// Si le Dernier ID est null
				if(lastID == null) lastID = -1;
				
				// Logs
				logger.debug("LongKeyClassGenerator#generate - Last ID: " + lastID);
				
				// On incremente
				lastID++;
				
				// Un objet Insert
				Insert insert = new Insert(dialect);
				
				// Positionnement des Param�tres
				insert.setTableName(generatorTable);
				insert.addColumn(SXKeyClassGenerationStructure.GENERATION_ID_COLUMN_NAME, lastID.toString(), new IntegerType());
				insert.addColumn(subjectClassNameColumn, subject, new CharacterType());
				insert.addColumn(lastGeneratedValueColumn, lastGeneratedValue.toString(), new LongType());
				
				// Mise en place de la requ�te de mise � jour
				lastValueUpdateRequest = insert.toStatementString();
				
			} else {
				
				// Un objet Update
				Update update = new Update(dialect);
				
				// Mise en place des param�tres
				update.setTableName(generatorTable);
				update.addColumn(lastGeneratedValueColumn, lastGeneratedValue.toString(), new LongType());
				update.setWhere(subjectClassNameColumn + " = " + "'" + subject + "'");
				
				// Mise en place de la requ�te de mise � jour
				lastValueUpdateRequest = update.toStatementString();
			}
			
			// Un Log pour la requ�te de mise � jour
			logger.debug("LongKeyClassGenerator#generate - Update Request : " + lastValueUpdateRequest);
			
			// Ex�cution de la requ�te de mise � jour
			s.createSQLQuery(lastValueUpdateRequest).executeUpdate();
			
			// On force la synchro
			s.flush();
			
			// On enregistre cette valeur dans la Map
			generatedValues.put(subject, lastGeneratedValue);

			// Fin de la G�n�ration
			logger.debug("LongKeyClassGenerator#generate - END GENERATION [FOR " + subject + "]");
			
			// On retourne la cl�
			return key;
			
		} catch (Exception e) {
			
			// On affiche l'erreur
			e.printStackTrace();
			
			// On lance une erreur inconnue
			throw new UnknownGeneratorErrorException(e);
		}
	}

	@Override
	public void configure(Type type, Properties properties, Dialect dialect) throws MappingException {
		
		// Table de generation
		generatorTable = SXKeyClassGenerationStructure.GENERATION_TABLE_NAME;
		
		// Colonne discriminante
		subjectClassNameColumn = SXKeyClassGenerationStructure.GENERATION_DISCRIMINATOR_COLUMN_NAME;
		
		// Colonne de la derni�re valeur generee pour les classes d'un discriminant donn�
		lastGeneratedValueColumn = SXKeyClassGenerationStructure.GENERATION_LAST_VALUE_COLUMN_NAME;

		// R�cup�ration du Pr�fix
		prefixGeneratorClassName = PropertiesHelper.getString(SXKeyClassGenerationStructure.PREFIX_GENERATOR_CLASS_NAME, properties, "").trim();
		
		// Sauvegarde du Dialecte
		this.dialect = dialect;
	}
	
	/**
	 * Methode de chargement du Generateur de prefixe
	 * @return	Generateur de prefixe
	 */
	private IPrefixGenerator loadPrefixGenerator() {
		
		// Si le nom de la classe de generation du prefixe est vide
		if(prefixGeneratorClassName == null || prefixGeneratorClassName.trim().length() == 0) return new DefaultPrefixGenerator();
		
		try {
			
			// On charge la classe
			Class<?> generatorClass = Class.forName(prefixGeneratorClassName);
			
			// Si la classe n'implemente pas l'Interface
			if(!IPrefixGenerator.class.isAssignableFrom(generatorClass)) return new DefaultPrefixGenerator();
			
			// On retourne l'instance
			return (IPrefixGenerator) generatorClass.newInstance();
			
		} catch (Throwable e) {
			
			// On retourne le generateur par defaut
			return new DefaultPrefixGenerator();
		}
	}

}
