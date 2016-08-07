package szilveszterandras.vspf.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	private String password;
	private String avatar;
	
	public User() {}
	public User(String username, String name, String email, String password, String avatar) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
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
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
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
		Notifier.getInstance().publish("user/remove", this);
	}
}
