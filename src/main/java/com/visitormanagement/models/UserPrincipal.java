package com.visitormanagement.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class UserPrincipal implements UserDetails{
    
	private Long id;
	private String fullname;
	private String username;
	@JsonIgnore
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(Long id, String fullname, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserPrincipal grantedUser(Admin admin) {
		List<GrantedAuthority> authorities = admin.getRoles()
				          .stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				          .collect(Collectors.toList());
		
		return new UserPrincipal(
				admin.getId(), 
				admin.getFullname(), 
				admin.getUsername(),
				admin.getEmail(), 
				admin.getPassword(), 
				authorities);
	}

	
	public Long getId() {
		return id;
	}

	public String getFullname() {
		return fullname;
	}

	public String getEmail() {
		return email;
	}
	
	
	
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password ;
	}

	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
