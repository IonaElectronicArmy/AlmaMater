package com.iona.framework.util;

public class RosalindException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RosalindException(String exception){
		super(exception);
	}
	
	public RosalindException(Exception exception){
		super(exception);
	}

}
