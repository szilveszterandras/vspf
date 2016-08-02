package szilveszterandras.vspf.payload;

public class ValidateResponse {
	private Boolean isValid;
	
	public ValidateResponse(Boolean isValid) {
		this.isValid = isValid;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
