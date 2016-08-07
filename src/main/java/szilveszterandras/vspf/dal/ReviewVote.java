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
@Table(name="reviewVotes")
public class ReviewVote {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private Long userId;
	private Long reviewId;
	private Boolean isPositive;
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

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Boolean getIsPositive() {
		return isPositive;
	}

	public void setIsPositive(Boolean isPositive) {
		this.isPositive = isPositive;
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
		Notifier.getInstance().publish("reviewVote/persist", this);
	}

	@PostUpdate
	public void PostUpdate() {
		Notifier.getInstance().publish("reviewVote/update", this);
	}

	@PostRemove
	public void PostRemove() {
		LoggerFactory.getLogger("a").info("Post remove " + this.id);
		Notifier.getInstance().publish("reviewVote/remove", this.id);
	}
}
