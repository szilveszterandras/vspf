package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindByDAO<T, S> {
	public static final Logger logger = LoggerFactory.getLogger(InsertDAO.class);
	private Class<T> itemClass;
	private String field;
	private EntityManager em;

	public FindByDAO(Class<T> itemClass, String field) {
		this.itemClass = itemClass;
		this.field = field;
	}

	public T findBy(S param) {
		em = HibernateUtilJpa.getEntityManager();
		T s = null;
		try {
			em.getTransaction().begin();
			s = em.createQuery("from " + itemClass.getName() + " where " + field + " = :param", itemClass)
					.setParameter("param", param).getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return s;
	}
}