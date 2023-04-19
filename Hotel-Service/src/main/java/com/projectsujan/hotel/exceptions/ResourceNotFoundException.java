package com.projectsujan.hotel.exceptions;

public class ResourceNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1501039173521291729L;

	public ResourceNotFoundException(String s) {
		super(s);
	}
	
	public ResourceNotFoundException() {
		super("Resource Not Found..");
	}
	
}
