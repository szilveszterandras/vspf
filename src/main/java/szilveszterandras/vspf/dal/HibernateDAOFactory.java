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
	@Override
	public UserDAO getUserDAO() {
		return new UserDAO();
	}
	@Override
	public PhotoDAO getPhotoDAO() {
		return new PhotoDAO();
	}
	@Override
	public TagDAO getTagDAO() {
		return new TagDAO();
	}
	@Override
	public StarDAO getStarDAO() {
		return new StarDAO();
	}
}