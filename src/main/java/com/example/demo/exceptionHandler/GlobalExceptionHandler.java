package com.example.demo.exceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.demo.exception.EmptyResultException;
import com.example.demo.exception.ResourseNotFound;

import jakarta.validation.UnexpectedTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourseNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> resourceNotFoundException(ResourseNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> nullpointerException(NullPointerException ex){
    	return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }
  
    @ExceptionHandler(EmptyResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> emptyDataException(EmptyResultException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> methodArgumentException(MethodArgumentNotValidException ex){
    	Map<String, String> resp=new HashMap<>();
    	ex.getBindingResult().getAllErrors().forEach((error)->{
    		String fieldString=((FieldError)error).getField();
    	String massage=	error.getDefaultMessage();
    		resp.put(fieldString,massage);
    	});
    	return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> messageNotReadableException(HttpMessageNotReadableException ex){
    	return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, NoResourceFoundException.class})
    public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return new ResponseEntity<>("Enter the correct URL!!!", HttpStatus.BAD_REQUEST);
        } else if (ex instanceof NoResourceFoundException) {
            return new ResponseEntity<>("Resource not found!!!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred!");
    }
    
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<String> unexpectedTypeException(UnexpectedTypeException ex){
    	return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
    	return new ResponseEntity<String>("Enter the correct URL",HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ResponseEntity<String> unsupportedOperationException(UnsupportedOperationException ex){
    	return new ResponseEntity<String>(ex.getMessage(),HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
	/*
	 * //to handle all exception
	 * 
	 * @ExceptionHandler(Exception.class) public ResponseEntity<String>
	 * exception(Exception ex){ return new
	 * ResponseEntity<String>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR); }
	 */
    
}
