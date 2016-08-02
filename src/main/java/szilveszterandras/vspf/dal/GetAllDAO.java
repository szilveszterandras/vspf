package szilveszterandras.vspf.dal;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetAllDAO<T> {
	public static final Logger logger = LoggerFactory.getLogger(GetAllDAO.class);
	private Class<T> itemClass;
	private EntityManager em;

	public GetAllDAO(Class<T> itemClass) {
		this.itemClass = itemClass;
	}

	public List<T> getAll() {
		em = HibernateUtilJpa.getEntityManager();
		List<T> s = null;
		try {
			em.getTransaction().begin();
			s = em.createQuery("from " + itemClass.getName(), itemClass).getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return s;
	}
}