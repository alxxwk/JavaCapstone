package com.ntuc.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 3, max = 50)
	private String username;
	@Column(length = 64)
	private String password;
	private boolean enabled;
	@Column(nullable = true, length = 64)
	private String photos;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<UserRole> userRoles = new HashSet<>();
	
	
	
	public Users(Long id, @Size(min = 3, max = 50) String username, String password, boolean enabled, String photos,
			Set<UserRole> userRoles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.photos = photos;
		this.userRoles = userRoles;
	}
	
	

	public Users() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public Set<UserRole> getRoles() {
		return userRoles;
	}

	public void setRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Transient
	public String getPhotosImagePath() {
		if (photos == null || id == null)
			return null;

		return "/user-photos/" + id + "/" + photos;
	}
}
