package org.server.exception;

public class UserRepeatException extends RuntimeException {

	public UserRepeatException(String message) {
		super(message);
	}
}
