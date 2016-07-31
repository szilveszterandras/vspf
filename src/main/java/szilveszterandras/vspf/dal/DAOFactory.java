package szilveszterandras.vspf.dal;

public abstract class DAOFactory {
	
//	public abstract GenericDAO<UserPassword> getUserPasswordDAO();
	public abstract UserPasswordDAO getUserPasswordDAO();
	
	public static DAOFactory getInstance() {
		return new HibernateDAOFactory();
	};
}