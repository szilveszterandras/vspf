package szilveszterandras.vspf.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import szilveszterandras.vspf.App;
import szilveszterandras.vspf.dal.Photo;
import szilveszterandras.vspf.dal.Review;
import szilveszterandras.vspf.dal.Tag;
import szilveszterandras.vspf.dal.User;

public class PhotoFilter {
	private Long id;
	private UserFilter user;
	private String title;
	private String description;
	private String hash;
	private String path;
	private Date uploadedAt;
	private List<String> tags;
	private int rating;
	private int reviewCount;
	
	public PhotoFilter(Photo p) {
		this.id = p.getId();
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.hash = p.getHash();
		this.uploadedAt = p.getUploadedAt();
		this.path = "http://" + App.config.getProperty("serverIp") + ":9093/" + p.getPath();
	}

	public PhotoFilter(Photo p, User user, List<Tag> tags, List<Review> reviews) {
		this.id = p.getId();
		this.user = new UserFilter(user);
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.hash = p.getHash();
		this.uploadedAt = p.getUploadedAt();
		this.path = "http://" + App.config.getProperty("serverIp") + ":9093/" + p.getPath();
		this.tags = new ArrayList<String>();
		for (Tag t : tags) {
			this.tags.add(t.getName());
		}
		this.reviewCount = reviews.size();
		if (this.reviewCount > 0) {
			int sum = 0;
			for (Review r : reviews) {
				sum += r.getRating();
			}
			this.rating = sum / this.reviewCount;
		} else {
			this.rating = -1;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserFilter getUser() {
		return user;
	}

	public void setUser(UserFilter user) {
		this.user = user;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
}
