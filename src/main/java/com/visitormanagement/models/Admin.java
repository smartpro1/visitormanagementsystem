package com.visitormanagement.models;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
public class Admin{

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotBlank(message = "fullname is required")
	 @Size(min = 3, message="characters must be more than three letters")
	 private String fullname;
	 @NotBlank(message = "username is required")
	 @Size(min =3, message="username cannot be empty or less than three characters")
	 @Column(updatable = false, unique = true)
	 private String username;
	

	 @NotBlank(message = "email is required")
	 @Size(min =3, message="email cannot be empty or less than three characters")
	 @Column(updatable = false, unique = true)
	 private String email;
	 @NotBlank(message = "password is required")
	 @Size(min =6, message="password cannot be empty or less than six characters")
	 private String password;
	 @JsonFormat(pattern = "yyyy-mm-dd")
	 @Column(updatable = false)
	 private Date created_At;
	 
	 @ManyToMany(fetch = FetchType.EAGER)
	 @JoinTable(name = "users_roles", 
			 joinColumns=@JoinColumn(name = "user_id", referencedColumnName = "id"), 
		     inverseJoinColumns=@JoinColumn(name = "role_id", referencedColumnName = "id")) 
	private Set<Role> roles = new HashSet<>();
	 
	 
	 
	 public Admin() {
		 
	 }
	 
	  public Admin(
			@NotBlank(message = "fullname is required") @Size(min = 3, message = "characters must be more than three letters") String fullname,
			@NotBlank(message = "username is required") @Size(min = 3, message = "username cannot be empty or less than three characters") String username,
			@NotBlank(message = "email is required") @Size(min = 3, message = "email cannot be empty or less than three characters") String email,
			@NotBlank(message = "password is required") @Size(min = 6, message = "password cannot be empty or less than six characters") String password) {
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}
	
	
	 public Set<Role> getRoles() {
			return roles;
		}

		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}
	

	@PrePersist
	protected void onCreate() {
		this.created_At = new Date();
	}

	
	@Override
	public String toString() {
		return "Admin [id=" + id + ", fullname=" + fullname + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", created_At=" + created_At + "]";
	}
	
	
	 
}
