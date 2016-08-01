package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertDAO<T> {
	public static final Logger logger = LoggerFactory.getLogger(InsertDAO.class);
	private EntityManager em;
	
	public void insertObject(T object) {
		em = HibernateUtilJpa.getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.debug("Exception", e);
		}
		em.close();
	}
}