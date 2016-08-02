package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetOneDAO<T> {
	public static final Logger logger = LoggerFactory.getLogger(GetOneDAO.class);
	private EntityManager em;
	private Class<T> itemClass;
	
	public GetOneDAO(Class<T> itemClass) {
		this.itemClass = itemClass;
	}
	
	public T getObject(Long id) {
		em = HibernateUtilJpa.getEntityManager();
		T item = null;
		try {
			em.getTransaction().begin();
			item = em.find(this.itemClass, id);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			logger.debug("Exception", e);
		}
		em.close();
		return item;
	}
}
