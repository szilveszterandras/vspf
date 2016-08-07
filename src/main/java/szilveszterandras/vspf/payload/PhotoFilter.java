package szilveszterandras.vspf.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Tag;

public class PhotoFilter {
	private Long id;
	private String username;
	private String title;
	private String description;
	private String hash;
	private String path;
	private Date uploadedAt;
	private List<String> tags;
	
	public PhotoFilter(Photo p, String username, List<Tag> tags) {
		this.id = p.getId();
		this.username = username;
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.hash = p.getHash();
		this.uploadedAt = p.getUploadedAt();
		this.tags = new ArrayList<String>();
		for (Tag t : tags) {
			this.tags.add(t.getName());
		}
		
		this.path = "http://localhost:9093/" + p.getPath();
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
