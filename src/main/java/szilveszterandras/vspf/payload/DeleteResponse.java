package szilveszterandras.vspf.payload;

public class DeleteResponse {
	private final Boolean isDeleted = true;
	private Long id;
	
	public DeleteResponse(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
}
