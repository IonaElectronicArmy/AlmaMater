package com.iona.framework.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = -1937608776284260841L;
	private String principal;
	
	public JwtAuthenticationToken(Object principal) {
		super(principal, null);
		this.principal = (String)principal;
	}
	
	public String getToken(){
		return principal;
	}

}
