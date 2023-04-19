package com.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 5265061942279470390L;

	// We can add extra properties
	
	public ResourceNotFoundException() {
		super("Resource not found on Server ...");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

}
