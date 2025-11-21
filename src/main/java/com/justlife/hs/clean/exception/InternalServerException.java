package com.justlife.hs.clean.exception;

public class InternalServerException extends RuntimeException{
	
	private static final long serialVersionUID = 1023493967075529730L;

	public InternalServerException(String message) {
        super(message);
    }

}
