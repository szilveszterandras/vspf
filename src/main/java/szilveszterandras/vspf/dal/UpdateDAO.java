package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateDAO<T> {
	public static final Logger logger = LoggerFactory.getLogger(UpdateDAO.class);
	private EntityManager em;
	
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
}