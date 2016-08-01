package szilveszterandras.vspf.dal;

public class HibernateDAOFactory extends DAOFactory {
	@Override
	public UserPasswordDAO getUserPasswordDAO() {
		return new UserPasswordDAO();
	}
	@Override
	public SessionDAO getSessionDAO() {
		return new SessionDAO();
	}
}