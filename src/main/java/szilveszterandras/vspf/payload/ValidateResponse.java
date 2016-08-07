package szilveszterandras.vspf.payload;

public class ValidateResponse {
	private Boolean isValid;
	private UserFilter user;
	
	public ValidateResponse(Boolean isValid, UserFilter user) {
		this.isValid = isValid;
		this.user = user;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public UserFilter getUserFilter() {
		return user;
	}

	public void setUserFilter(UserFilter user) {
		this.user = user;
	}
}
