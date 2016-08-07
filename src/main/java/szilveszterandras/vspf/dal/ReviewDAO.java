package szilveszterandras.vspf.dal;

import java.util.List;

public class ReviewDAO {
	private FilterByDAO<Review, Long> fbpiddao = new FilterByDAO<Review, Long>(Review.class, "photoId");
	private FilterByDAO<Review, Long> fbuiddao = new FilterByDAO<Review, Long>(Review.class, "userId");
	private InsertDAO<Review> idao = new InsertDAO<Review>();
	private UpdateDAO<Review> udao = new UpdateDAO<Review>();
	private DeleteDAO<Review> ddao = new DeleteDAO<Review>(Review.class);

	public List<Review> filterByPhotoId(Long photoId) {
		return fbpiddao.filterBy(photoId);
	}

	public List<Review> filterByUserId(Long photoId) {
		return fbuiddao.filterBy(photoId);
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
}