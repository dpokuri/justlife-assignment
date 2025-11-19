package com.justlife.hs.clean.exception;

public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -1541938604942530036L;

	public ResourceNotFoundException(String message) {
        super(message);
    }

}
