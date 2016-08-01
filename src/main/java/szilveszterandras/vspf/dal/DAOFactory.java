package szilveszterandras.vspf.dal;

public abstract class DAOFactory {
	public abstract UserPasswordDAO getUserPasswordDAO();
	public abstract SessionDAO getSessionDAO(); 
	
	public static DAOFactory getInstance() {
		return new HibernateDAOFactory();
	}
}