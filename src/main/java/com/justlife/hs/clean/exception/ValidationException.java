package com.justlife.hs.clean.exception;

public class ValidationException extends RuntimeException{
	
	private static final long serialVersionUID = -6340794754881808948L;

	public ValidationException(String msg) {
        super(msg);
    }

}
