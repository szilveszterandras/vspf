package szilveszterandras.vspf.dal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import szilveszterandras.vspf.Notifier;

@Entity
@Table(name="photos")
public class Photo {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;
	private Long userId;
	private String title;
	private String description;
	private String hash;
	private String path;
	@Column(name="uploadedAt", columnDefinition="DATETIME")
	private Date uploadedAt;
	
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

	// Entity listeners
	@PostPersist
	public void PostInsert() {
		Notifier.getInstance().publish("photo/persist", this);
	}

	@PostUpdate
	public void PostUpdate() {
		Notifier.getInstance().publish("photo/update", this);
	}

	@PostRemove
	public void PostRemove() {
		Notifier.getInstance().publish("photo/remove", this);
	}
}
