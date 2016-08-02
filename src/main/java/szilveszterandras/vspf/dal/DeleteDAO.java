package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteDAO<T> {
	public static final Logger logger = LoggerFactory.getLogger(DeleteDAO.class);
	private Class<T> itemClass;
	private EntityManager em;
	
	public DeleteDAO(Class<T> itemClass) {
		this.itemClass = itemClass;
	}
	
	public void deleteObject(Long id) {
		em = HibernateUtilJpa.getEntityManager();
		try {
			T obj = em.find(this.itemClass, id);
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
