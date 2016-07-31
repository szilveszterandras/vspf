package szilveszterandras.vspf.dal;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericHibernateDAO<T> implements GenericDAO<T> {
	
	public static final Logger logger = LoggerFactory.getLogger(GenericHibernateDAO.class);
	private Class<T> persistentClass;
	private EntityManager em;

	public GenericHibernateDAO(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public List<T> getAllDataRows() {
		List<T> list = null;
		em = HibernateUtilJpa.getEntityManager();
		try {
			em.getTransaction().begin();
			list = em.createQuery("from Song", persistentClass).getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.debug("Exception", e);
		}
		em.close();
		return list;
	}

	public void insertObjects(List<T> objects) {
		em = HibernateUtilJpa.getEntityManager();
		try {
			em.getTransaction().begin();
			for (T object : objects) {
				em.persist(object);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.debug("Exception", e);
		}
		em.close();
	}

	public void updateObject(T obj) {
		em = HibernateUtilJpa.getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.debug("Exception", e);
		}
		em.close();
	}

	public void deleteObject(int id) {
		em = HibernateUtilJpa.getEntityManager();
		try {
			T obj = em.find(persistentClass, id);
			em.getTransaction().begin();
			em.remove(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.debug("Exception", e);
		}
		em.close();
	}
}