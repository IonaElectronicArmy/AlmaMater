package com.iona.framework.auth;

import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {

	private static final long serialVersionUID = -5725800221436778967L;

	public JwtTokenMissingException(String message) {
		super(message);
	}
}
