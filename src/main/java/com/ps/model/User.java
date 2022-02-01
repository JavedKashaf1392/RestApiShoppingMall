package com.ps.model;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "users_new")
@Data
public class User {


	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@Column(length = 64, nullable = false)
	private String password;
	
	@Column(name = "first_name", length = 45, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 45, nullable = false)
	private String lastName;
	
	private double phoneNumber;
	
	@Column(length = 64)
	private String photos;
	
	private boolean enabled=true;

	 @ManyToMany(fetch = FetchType.EAGER)
	 @JoinTable(
	            name = "users_roles",
	            joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id")
	            )
	 private Set<Role> roles = new HashSet<>();
	 	 
	 public void addRole(Role role) {
			this.roles.add(role);
		}
	 
	public User(String email, String password, String firstName,double phoneNumber, String lastName, String photos, boolean enabled) {
		super();
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photos = photos;
		this.enabled = enabled;
		this.phoneNumber=phoneNumber;
	}
	
	

	

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", photos=" + photos
				+ ", enabled=" + enabled + ", roles=" + roles + "]";
	}
	
	
	
	
	
	
	 
}
