package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.example.exception.RateLimitExceededException;

@Slf4j
@ControllerAdvice
public class HelloWorldExceptionHandler {

  @ExceptionHandler(RateLimitExceededException.class)
  public ResponseEntity<Object> handleRateLimitExceededException(RateLimitExceededException e) {
    return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
  }
}
