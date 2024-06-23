package com.example.demo.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.annotation.Resource;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler{

	@Resource
    private MessageSource messageSource;
	
	
    private HttpHeaders headers(){
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
	
    @ExceptionHandler({Exception.class})
    private ResponseEntity<Object> handleGlobalException(Exception e, WebRequest request) {
    	if (e.getCause().getCause().getClass().isAssignableFrom(ConstraintViolationException.class)) {
    		var error = new ResponseError("Invalid fields", HttpStatus.BAD_REQUEST.value());
    		return handleExceptionInternal(e, error, headers(), HttpStatus.CONFLICT, request);
    	}
    	
//    	String message = messageSource.getMessage("error.server", new Object[]{e.getMessage()}, null);
//    	var error = new ResponseError(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
//    	return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    	return null;
    	
    }
	
	@ExceptionHandler({BusinessException.class})
    private ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request) {
        var error = new ResponseError(e.getMessage(),  e.getErrorCode());
        return handleExceptionInternal(e, error, headers(), HttpStatus.CONFLICT, request);
    }
}
