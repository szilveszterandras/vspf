package szilveszterandras.vspf.payload;

import java.util.Date;

import szilveszterandras.vspf.dal.Photo;

public class PhotoFilter {
	private Long id;
	private Long userId;
	private String title;
	private String description;
	private String path;
	private Date uploadedAt;
	
	public PhotoFilter(Photo p) {
		this.id = p.getId();
		this.userId = p.getUserId();
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.uploadedAt = p.getUploadedAt();
		
		this.path = "http://localhost:8080/" + p.getPath();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(Date uploadedAt) {
		this.uploadedAt = uploadedAt;
	}
}
