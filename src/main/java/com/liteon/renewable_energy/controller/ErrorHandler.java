package com.liteon.renewable_energy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse handleServerException(Exception exception) {
        String message = exception.getMessage();
        return new ExceptionRestResponse(500, message == null ? "Internal Server Error" : message);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRestResponse handleRequestException(HttpClientErrorException exception) {
        String message = exception.getMessage();
        return new ExceptionRestResponse(exception.getStatusCode().value(), message == null ? "Processing Error" : message);
    }
}

