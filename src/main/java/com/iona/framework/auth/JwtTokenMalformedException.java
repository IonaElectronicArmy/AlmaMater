package com.iona.framework.auth;

public class JwtTokenMalformedException extends RuntimeException{
	
	private static final long serialVersionUID = 8988656234360432346L;

	public JwtTokenMalformedException(String message){
		super(message);
	}
}
