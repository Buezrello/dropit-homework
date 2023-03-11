package com.example.dropit.exception;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
@Generated
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResolveAddressException.class)
    public void handleResolveAddressException(HttpServletResponse response, ResolveAddressException ex) throws IOException {
        log.error(ex.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(MongoDbNotFoundException.class)
    public void handleMongoDbNotFoundException(HttpServletResponse response, MongoDbNotFoundException ex) throws IOException {
        log.error(ex.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(BusinessCapacityException.class)
    public void handleBusinessCapacityException(HttpServletResponse response, BusinessCapacityException ex) throws IOException {
        log.error(ex.getMessage());
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }
}
