package szilveszterandras.vspf.payload;

import szilveszterandras.vspf.App;
import szilveszterandras.vspf.dal.User;

public class UserFilter {
	private Long id;
	private String name;
	private String username;
	private String email;
	private String avatar;
	
	public UserFilter(User u) {
		this.id = u.getId();
		this.name = u.getName();
		this.username = u.getUsername();
		this.email = u.getEmail();
		this.avatar = "http://" + App.config.getProperty("serverIp") + ":9093/" + u.getAvatar();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	@Override
	public boolean equals(Object obj) {
		try {
			UserFilter u = (UserFilter) obj;
			return id == u.getId() &&
				name.equals(u.getName()) &&
				username.equals(u.getUsername()) &&
				email.equals(u.getEmail()) &&
				avatar.equals(u.getAvatar());
		} catch (Exception e) {
			return false;
		}
	}
}
