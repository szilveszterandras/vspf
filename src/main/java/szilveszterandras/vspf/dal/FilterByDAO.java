package szilveszterandras.vspf.dal;

import java.util.List;

import javax.persistence.EntityManager;

public class FilterByDAO<T, S> {
	private Class<T> itemClass;
	private String field;
	private EntityManager em;

	public FilterByDAO(Class<T> itemClass, String field) {
		this.itemClass = itemClass;
		this.field = field;
	}

	public List<T> filterBy(S param) {
		em = HibernateUtilJpa.getEntityManager();
		List<T> l = null;
		try {
			em.getTransaction().begin();
			l = em.createQuery("from " + itemClass.getName() + " where " + field + " = :param", itemClass)
					.setParameter("param", param).getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return l;
	}
}