package szilveszterandras.vspf.dal;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	private GetAllDAO<User> gadao = new GetAllDAO<>(User.class);
	private GetOneDAO<User> godao = new GetOneDAO<>(User.class);
	private InsertDAO<User> idao = new InsertDAO<User>(); 
	private UpdateDAO<User> udao = new UpdateDAO<User>();
	private DeleteDAO<User> ddao = new DeleteDAO<>(User.class);
	
	public List<User> getAllUsers() {
		return gadao.getAll();	
	}
	public User getUser(Long id) {
		return godao.getObject(id);
	}
	public void insertUser(User u) {
		idao.insertObject(u);
	}
	public void updateUser(User u) {
		udao.updateObject(u);
	}
	public void deleteUser(Long id) {
		ddao.deleteObject(id);
	}
	
}
