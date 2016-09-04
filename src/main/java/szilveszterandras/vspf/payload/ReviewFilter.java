package szilveszterandras.vspf.payload;

import java.util.Date;

import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.dal.User;

public class ReviewFilter {
	private Long id;
	private String username;
	private String hash;
	private int rating;
	private String title;
	private String description;
	private Date addedAt;
	
	public ReviewFilter() {};
	public ReviewFilter(Review r, Photo p, User u) {
		this.id = r.getId();
		this.rating = r.getRating();
		this.title = r.getTitle();
		this.description = r.getDescription();
		this.hash = p.getHash();
		this.username = u.getUsername();
		this.addedAt = r.getAddedAt();
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
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
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
}
