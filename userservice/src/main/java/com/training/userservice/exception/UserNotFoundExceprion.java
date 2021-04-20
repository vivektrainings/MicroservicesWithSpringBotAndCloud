package com.training.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundExceprion extends RuntimeException{
	
	public UserNotFoundExceprion(String msg) {
		super(msg);
	}

}
