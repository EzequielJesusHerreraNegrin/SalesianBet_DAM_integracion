package com.accesodatos.exception;

public class NotEnoughPointsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NotEnoughPointsException(String message) {
		super(message);		
	}
}
