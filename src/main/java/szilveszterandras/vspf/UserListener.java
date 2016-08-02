package szilveszterandras.vspf;

import javax.persistence.PostPersist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.dal.User;

public class UserListener {
	public static final Logger logger = LoggerFactory.getLogger(UserListener.class); 
	@PostPersist
	public void PostInsert(User u) {
		Notifier.getInstance().publish("user/new", u);
	}
}
