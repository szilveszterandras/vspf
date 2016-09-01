package szilveszterandras.vspf.dal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReviewDAO {
	public static final Logger logger = LoggerFactory.getLogger(ReviewDAO.class);
	private GetOneDAO<Review> grdao = new GetOneDAO<Review>(Review.class);
	private FilterByDAO<Review, Long> fbpiddao = new FilterByDAO<Review, Long>(Review.class, "photoId");
	private InsertDAO<Review> idao = new InsertDAO<Review>(); 
	private UpdateDAO<Review> udao = new UpdateDAO<Review>();
	private DeleteDAO<Review> ddao = new DeleteDAO<>(Review.class);
	
	public List<Review> filterByPhotoId(Long photoId) {
		return fbpiddao.filterBy(photoId);
	}
	public void insertReview(Review r) {
		idao.insertObject(r);
	}
	public void updateReview(Review r) {
		udao.updateObject(r);
	}
	public void deleteReview(Long id) {
		ddao.deleteObject(id);
	}
	public Review getReview(Long id) {
		return grdao.getObject(id);
	}
}
