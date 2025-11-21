package com.justlife.hs.clean.exception;

public class BadRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 965230086354380081L;

	public BadRequestException(String message) {
        super(message);
    }

}
