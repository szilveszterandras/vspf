package szilveszterandras.vspf.dal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import szilveszterandras.vspf.Notifier;

@Entity
@Table(name="stars")
public class Star {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;
	private Long userId;
	private Long photoId;
	
	public Star() {};

	public Star(Long userId, Long photoId) {
		this.userId = userId;
		this.photoId = photoId;
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
	public Long getPhotoId() {
		return photoId;
	}
	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}
	
	@PostPersist
	public void PostPersist() {
		Notifier.getInstance().publish("star/persist", this);
	}
	@PostRemove
	public void PostRemove() {
		Notifier.getInstance().publish("star/remove", this);
	}
}
