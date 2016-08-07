package szilveszterandras.vspf.dal;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TagDAO {
	public static final Logger logger = LoggerFactory.getLogger(TagDAO.class);
	private EntityManager em;

	private FilterByDAO<Tag, Long> fbpiddao = new FilterByDAO<>(Tag.class, "photoId");
	private InsertDAO<Tag> idao = new InsertDAO<Tag>();
	private DeleteDAO<Tag> ddao = new DeleteDAO<Tag>(Tag.class);

	public List<TagCount> getAllTagCounts() {
		em = HibernateUtilJpa.getEntityManager();
		List<TagCount> l = null;
		try {
			em.getTransaction().begin();
			l = em.createQuery(
					"select new szilveszterandras.vspf.dal.TagCount(tag.name, count(tag)) from Tag tag group by tag.name",
					TagCount.class).getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return l;
	}

	public TagCount getTagCount(String tag) {
		em = HibernateUtilJpa.getEntityManager();
		TagCount t = null;
		try {
			em.getTransaction().begin();
			t = em.createQuery(
					"select new szilveszterandras.vspf.dal.TagCount(tag.name, count(tag)) from Tag tag where tag.name=:param group by tag.name",
					TagCount.class).setParameter(":param", tag).getSingleResult();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return t;
	}

	public void insertTag(Tag tag) {
		idao.insertObject(tag);
	}

	public void deleteTag(Long id) {
		ddao.deleteObject(id);
	}

	public List<Tag> filterByPhotoId(Long id) {
		return fbpiddao.filterBy(id);
	}
}
