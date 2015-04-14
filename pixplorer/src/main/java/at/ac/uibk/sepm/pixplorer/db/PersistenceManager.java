package at.ac.uibk.sepm.pixplorer.db;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Perisstance manager that provides access to the PostgreSQL database using Hibernate. This
 * class offers methods to read, update or delete entries from the database.
 * 
 * @author cbo
 */
public class PersistenceManager {
	/** logger */
	private static final Logger logger = Logger.getLogger(PersistenceManager.class.getName());

	/** hibernate session factory reference */
	private static SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from standard (hibernate.cfg.xml) config file.
			Configuration configuration = new Configuration().configure();
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
			applySettings(configuration.getProperties());
			sessionFactory = configuration.buildSessionFactory(builder.build());
		} catch (Exception ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * get all from database
	 * 
	 * @param type - the class type representing the hibernate entity
	 * @return database content or null
	 */
	public <T> List<T> getAll(Class<T> type) {
		if (sessionFactory == null) {
			logger.log(Level.SEVERE, "No session factory set!");
			return null;
		}

		Session session = sessionFactory.getCurrentSession();

		List<T> result = null;
		try {
			session.beginTransaction();
			result = session.createQuery("from " + type.getSimpleName() + " order by id asc").list();
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			logger.log(Level.SEVERE,
					"Could not execute query from " + type.getSimpleName(), e);
		}

		return result;
	}

	/**
	 * Stores the passed objects to the database.
	 *
	 * @param objects - object to store
	 * @return true if stored, false if not
	 */
	public boolean save(Object... objects) {
		if (objects == null) {
			return false;
		}

		if (sessionFactory == null) {
			logger.log(Level.SEVERE, "No session factory set!");
			return false;
		}

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			for (Object object : objects) {
				session.saveOrUpdate(object);
			}
			session.getTransaction().commit();
			return true;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			logger.log(Level.SEVERE, null, e);
		}

		return false;
	}

	/**
	 * Deletes the passed objects to the database.
	 *
	 * @param objects - object to delete
	 * @return true if stored, false if not
	 */
	public boolean delete(Object... objects) {
		if (objects == null) {
			return false;
		}

		if (sessionFactory == null) {
			logger.log(Level.SEVERE, "No session factory set!");
			return false;
		}

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();
			for (Object object : objects) {
				if (object != null) {
					session.delete(object);
				}
			}
			session.getTransaction().commit();

			return true;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			logger.log(Level.SEVERE, null, e);
		}

		return false;
	}
	

	/**
	 * private constructor
	 */
	private PersistenceManager() {
		// do not instantiate
	}
}
