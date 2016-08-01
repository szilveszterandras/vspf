package szilveszterandras.vspf.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserPasswordDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserPasswordDAO.class);
	private FindByDAO<UserPassword, String> fbundao = new FindByDAO<UserPassword, String>(UserPassword.class, "username");
	
	public UserPassword findByUsername(String username) {
		return fbundao.findBy(username);
	}
}
