package szilveszterandras.vspf.dal;

public abstract class DAOFactory {
	public abstract UserPasswordDAO getUserPasswordDAO();
	public abstract SessionDAO getSessionDAO();
	public abstract UserDAO getUserDAO();
	public abstract PhotoDAO getPhotoDAO();
	
	public static DAOFactory getInstance() {
		return new HibernateDAOFactory();
	}
}