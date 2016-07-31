package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPasswordDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserPasswordDAO.class);
	private EntityManager em;
	
	public UserPassword findUserPassword(String username) throws NoResultException {
		em = HibernateUtilJpa.getEntityManager();
		UserPassword up = null;
		try {
			em.getTransaction().begin();
			up = em.createQuery("from UserPassword where username=:un", UserPassword.class)
				.setParameter("un", username)
				.getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new NoResultException();
		} finally {
			em.close();
		}
		return up;
	}
}
