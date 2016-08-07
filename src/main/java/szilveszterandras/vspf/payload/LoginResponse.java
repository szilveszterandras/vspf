package szilveszterandras.vspf.payload;

public class LoginResponse {
	private String token;
	private UserFilter user;

	public LoginResponse(String token, UserFilter user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserFilter getUser() {
		return user;
	}

	public void setUser(UserFilter user) {
		this.user = user;
	}
}
