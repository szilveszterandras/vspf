package szilveszterandras.vspf.payload;

import java.util.List;

public class StarResponse {
	private String operation;
	private List<String> stars;
	
	public StarResponse(String operation, List<String> stars) {
		this.operation = operation;
		this.stars = stars;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<String> getStars() {
		return stars;
	}

	public void setStars(List<String> stars) {
		this.stars = stars;
	}
}
