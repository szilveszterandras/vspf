package szilveszterandras.vspf.dal;

public class HibernateDAOFactory extends DAOFactory {

//	@Override
//	public GenericDAO<UserPassword> getUserPasswordDAO() {
//		return new GenericHibernateDAO<UserPassword>(UserPassword.class);
//	}
	
	@Override
	public UserPasswordDAO getUserPasswordDAO() {
		return new UserPasswordDAO();
	}
}