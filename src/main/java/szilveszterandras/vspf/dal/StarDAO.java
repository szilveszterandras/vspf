package szilveszterandras.vspf.dal;

import java.util.List;

import javax.persistence.EntityManager;

public class StarDAO {
	private EntityManager em;
	private FilterByDAO<Star, Long> fbuiddao = new FilterByDAO<>(Star.class, "userId");
	private InsertDAO<Star> idao = new InsertDAO<Star>();
	private DeleteDAO<Star> ddao = new DeleteDAO<Star>(Star.class);

	public List<Star> getByUserId(Long userId) {
		return fbuiddao.filterBy(userId);
	}

	public Star findByIds(Long userId, Long photoId) {
		em = HibernateUtilJpa.getEntityManager();
		Star s = null;
		try {
			em.getTransaction().begin();
			s = em.createQuery("from Star where userId = :userId and photoId = :photoId", Star.class)
					.setParameter("userId", userId).setParameter("photoId", photoId).getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return s;
	}

	public void insertStar(Star s) {
		idao.insertObject(s);
	}

	public void deleteStar(Long id) {
		ddao.deleteObject(id);
	}
}
