package szilveszterandras.vspf.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.Notifier;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;
	private String username;
	private String name;
	private String email;
	
	public User() {}
	public User(String username, String name, String email) {
		this.username = username;
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	};
	
	// Entity listeners

	@PostPersist
	public void PostInsert() {
		Notifier.getInstance().publish("user/persist", this);
	}
	@PostUpdate
	public void PostUpdate() {
		Notifier.getInstance().publish("user/update", this);
	}
	@PostRemove
	public void PostRemove() {
		LoggerFactory.getLogger("a").info("Post remove " + this.id);
		Notifier.getInstance().publish("user/remove", this.id);
	}
}
