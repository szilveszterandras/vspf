package szilveszterandras.vspf.dal;

public class SessionDAO {
	private FindByDAO<Session, String> fbtdao = new FindByDAO<Session, String>(Session.class, "token");
	private InsertDAO<Session> idao = new InsertDAO<Session>(); 
	private UpdateDAO<Session> udao = new UpdateDAO<Session>();
	private DeleteDAO<Session> ddao = new DeleteDAO<Session>(Session.class);
	
	public Session findByToken(String token) {
		return fbtdao.findBy(token);
	}
	public void insertSession(Session s) {
		idao.insertObject(s);
	}
	public void updateSession(Session s) {
		udao.updateObject(s);
	}
	public void deleteSession(Long id) {
		ddao.deleteObject(id);
	}
}
