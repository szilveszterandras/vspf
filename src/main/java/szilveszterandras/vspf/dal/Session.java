package szilveszterandras.vspf.dal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="sessions")
public class Session {
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private Long id;
	private Long userId;
	private String token;
	@Column(name="lastUpdated", columnDefinition="DATETIME")
	private Date lastUpdated;
	@Column(name="expiresAt", columnDefinition="DATETIME")
	private Date expiresAt;
	
	public Session() {};
	
	public Session(String token, Long userId, Date lastUpdated, Date exiresAt) {
		this.token = token;
		this.userId = userId;
		this.lastUpdated = lastUpdated;
		this.expiresAt = exiresAt;
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Date getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Date expiresAt) {
		this.expiresAt = expiresAt;
	}
}
