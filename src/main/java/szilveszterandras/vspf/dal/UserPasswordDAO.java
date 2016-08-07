package szilveszterandras.vspf.dal;

public class UserPasswordDAO {
	private FindByDAO<UserPassword, String> fbundao = new FindByDAO<UserPassword, String>(UserPassword.class, "username");
	
	public UserPassword findByUsername(String username) {
		return fbundao.findBy(username);
	}
}
