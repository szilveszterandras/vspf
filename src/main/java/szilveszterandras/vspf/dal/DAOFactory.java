package szilveszterandras.vspf.dal;

public abstract class DAOFactory {
	public abstract UserPasswordDAO getUserPasswordDAO();
	public abstract SessionDAO getSessionDAO();
	public abstract UserDAO getUserDAO();
	public abstract PhotoDAO getPhotoDAO();
	public abstract TagDAO getTagDAO();
	public abstract StarDAO getStarDAO();
	
	public static DAOFactory getInstance() {
		return new HibernateDAOFactory();
	}
}