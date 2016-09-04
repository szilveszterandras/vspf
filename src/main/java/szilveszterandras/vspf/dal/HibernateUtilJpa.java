package szilveszterandras.vspf.dal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtilJpa {
	private static EntityManagerFactory entityManagerFactory;
	public static final Logger logger = LoggerFactory.getLogger(HibernateUtilJpa.class);
	static {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("vspf.hibernatejpa");
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}