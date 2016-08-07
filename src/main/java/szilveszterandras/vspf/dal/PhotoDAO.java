package szilveszterandras.vspf.dal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhotoDAO {
	public static final Logger logger = LoggerFactory.getLogger(PhotoDAO.class);

	private GetAllDAO<Photo> gadao = new GetAllDAO<>(Photo.class);
	private GetOneDAO<Photo> godao = new GetOneDAO<>(Photo.class);
	private FindByDAO<Photo, String> fbhdao = new FindByDAO<>(Photo.class, "hash");
	private FilterByDAO<Photo, Long> fbuiddao = new FilterByDAO<Photo, Long>(Photo.class, "userId");
	private InsertDAO<Photo> idao = new InsertDAO<Photo>(); 
	private UpdateDAO<Photo> udao = new UpdateDAO<Photo>();
	private DeleteDAO<Photo> ddao = new DeleteDAO<Photo>(Photo.class);
	
	public List<Photo> getAllPhotos() {
		return gadao.getAll();
	}
	public Photo getPhoto(Long id) {
		return godao.getObject(id);
	}
	public Photo findByHash(String hash) {
		return fbhdao.findBy(hash);
	}
	public List<Photo> filterByUserId(Long uid) {
		return fbuiddao.filterBy(uid);	
	}
	public void insertPhoto(Photo s) {
		idao.insertObject(s);
	}
	public void updatePhoto(Photo s) {
		udao.updateObject(s);
	}
	public void deletePhoto(Long id) {
		ddao.deleteObject(id);
	}
}
