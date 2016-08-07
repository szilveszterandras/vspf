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
import org.slf4j.LoggerFactory;

import szilveszterandras.vspf.Notifier;

@Entity
@Table(name="reviews")
public class Review {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private Long userId;
	private Long photoId;
	private int percent;
	private String summary;
	private String details;
	@Column(name = "timestamp", columnDefinition = "DATETIME")
	private Date timestamp;

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

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	// Entity listeners
	@PostPersist
	public void PostInsert() {
		Notifier.getInstance().publish("review/persist", this);
	}

	@PostUpdate
	public void PostUpdate() {
		Notifier.getInstance().publish("review/update", this);
	}

	@PostRemove
	public void PostRemove() {
		LoggerFactory.getLogger("a").info("Post remove " + this.id);
		Notifier.getInstance().publish("review/remove", this.id);
	}
}
