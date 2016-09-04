package szilveszterandras.vspf.payload;

import java.util.ArrayList;
import java.util.List;

public class DeleteResponse {
	private final Boolean isDeleted = true;
	private List<Long> ids;
	
	public DeleteResponse(Long id) {
		this.ids = new ArrayList<Long>();
		this.ids.add(id);
	}
	public DeleteResponse(List<Long> ids) {
		this.ids = ids;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
}
