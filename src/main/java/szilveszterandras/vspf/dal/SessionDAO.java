package szilveszterandras.vspf.dal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionDAO {
	public static final Logger logger = LoggerFactory.getLogger(UserPasswordDAO.class);

	private FindByDAO<Session, Long> fbuiddao = new FindByDAO<Session, Long>(Session.class, "userId");
	private FindByDAO<Session, String> fbtdao = new FindByDAO<Session, String>(Session.class, "token");
	private InsertDAO<Session> idao = new InsertDAO<Session>(); 
	private UpdateDAO<Session> udao = new UpdateDAO<Session>();
	
	public Session findByUserId(Long uid) {
		return fbuiddao.findBy(uid);	
	}	
	public Session findByToken(String token) {
		return fbtdao.findBy(token);
	}
	public void insertSession(Session s) {
		idao.insertObject(s);
	}
	public void updateSession(Session s) {
		udao.updateObject(s);
	}
}
