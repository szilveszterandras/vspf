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
@Table(name="tags")
public class Tag {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	private Long photoId;
	private String name;
	
	public Tag() {};
	public Tag(String name, Long photoId) {
		this.name = name;
		this.photoId = photoId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Entity listeners
	@PostPersist
	public void PostInsert() {
		Notifier.getInstance().publish("tag/persist", this);
	}

	@PostRemove
	public void PostRemove() {
		Notifier.getInstance().publish("tag/remove", this);
	}
}
