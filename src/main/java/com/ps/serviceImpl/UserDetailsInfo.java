package com.ps.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ps.model.Role;
import com.ps.model.User;

public class UserDetailsInfo implements UserDetails{
	
	private User user;
	
public UserDetailsInfo(User user) {
		this.user = user;
	}

    private Integer id;
    private  String username;
	private  String password;
	private  Collection<? extends GrantedAuthority> authories;
	private  boolean enabled;
	
	
	
	
	    public UserDetailsInfo(Integer id, String username, String password,
			Collection<? extends GrantedAuthority> authories, boolean enabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authories = authories;
		this.enabled = enabled;
	}

		
        public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Integer getId() {
			return id;
		}

		public Collection<? extends GrantedAuthority> getAuthories() {
			return authories;
		}

	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Role> roles = user.getRoles();
		
		List<SimpleGrantedAuthority> authories = new ArrayList<>();
		
		for (Role role : roles) {
			authories.add(new SimpleGrantedAuthority(role.getName()));
		}
		
		return authories;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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
		return user.isEnabled();
	}
	
	public String getFullname() {
		return this.user.getFirstName() + " " + this.user.getLastName();
	}
	
	public String getFirstName() {
		return this.user.getFirstName();
	}
	
	public void setFirstName(String firstName) {
		this.user.setFirstName(firstName);
	}

	public void setLastName(String lastName) {
		this.user.setLastName(lastName);
	}	
	
//	public boolean hasRole(String roleName) {
//		return user.getRoles(roleName)
//	}

}
