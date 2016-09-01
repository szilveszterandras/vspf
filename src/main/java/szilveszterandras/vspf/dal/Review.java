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
import szilveszterandras.vspf.payload.ReviewFilter;

@Entity
@Table(name="reviews")
public class Review {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;
	private Long userId;
	private Long photoId;
	private Long rating;
	private String title;
	private String description;
	@Column(name="addedAt", columnDefinition="DATETIME")
	private Date addedAt;
	
	public Review() {};
	public Review(ReviewFilter r, Long photoId, Long userId) {
		this.userId = userId;
		this.photoId = photoId;
		this.rating = r.getRating();
		this.title = r.getTitle();
		this.description = r.getDescription();
		this.addedAt = new Date();
	};
	
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

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
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

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
	
	// Entity Listeners
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
		Notifier.getInstance().publish("review/remove", this);
	}
}
