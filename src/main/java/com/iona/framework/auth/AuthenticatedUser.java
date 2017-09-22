package com.iona.framework.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthenticatedUser implements UserDetails {
	
	private static final long serialVersionUID = 1606247964327680363L;
	private final String id;
	private final String username;
	private final String token;
	private String password;
	private boolean enabled;
	private final Collection<? extends GrantedAuthority> authorities;

	public AuthenticatedUser(String id, String username, String token,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.token = token;
		this.authorities = authorities;
	}

	@JsonIgnore
	public String getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
