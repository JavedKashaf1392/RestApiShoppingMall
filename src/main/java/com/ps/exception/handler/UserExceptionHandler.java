package com.ps.exception.handler;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.ps.exception.UserNotFoundException;
import com.ps.model.ErrorType;

@RestControllerAdvice
public class UserExceptionHandler {

		@ExceptionHandler(UserNotFoundException.class)
		public ResponseEntity<ErrorType> handleUserNotFound(UserNotFoundException ex) {
			return new ResponseEntity<ErrorType>(new ErrorType(ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		
		
		
		@ExceptionHandler({
			HttpMessageNotReadableException.class 
			})
		public ResponseEntity<ErrorType> handleAll(Exception ex) {
		    return  new ResponseEntity<ErrorType>(
		      new ErrorType("Please check the request and payload and retry From Exception Method"),HttpStatus.BAD_REQUEST);
		}
		
	
		
		
		@ExceptionHandler({
			HttpRequestMethodNotSupportedException.class
			})
		public ResponseEntity<ErrorType> handleMethodNotAllow(Exception ex) {
		    return  new ResponseEntity<ErrorType>(
		      new ErrorType("Check Your Request! Or Contact with Admin Error From Exception Method"),HttpStatus.BAD_REQUEST);
		}
		
		
		@ExceptionHandler({
			SQLIntegrityConstraintViolationException.class
			})
		public ResponseEntity<ErrorType> UniqueFirstName(Exception ex) {
		    return  new ResponseEntity<ErrorType>(
		      new ErrorType("user already exist from exception method"),HttpStatus.BAD_REQUEST);
		}
		
		
		@ExceptionHandler({
			MethodArgumentTypeMismatchException.class
			})
		public ResponseEntity<ErrorType> MisMatchData(Exception ex) {
		    return  new ResponseEntity<ErrorType>(
		      new ErrorType("Please contact with admin ! and try after some time"),HttpStatus.BAD_REQUEST);
		}
}
