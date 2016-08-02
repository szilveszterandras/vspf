package szilveszterandras.vspf.payload;

public class StatusResponse {
	private Boolean isSuccess;
	private String errorMessage;

	public StatusResponse(Boolean isSuccess) {
		this.isSuccess = isSuccess;
		this.errorMessage = null;
	}

	public StatusResponse(String errorMessage) {
		this.isSuccess = false;
		this.errorMessage = errorMessage;
	};

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}