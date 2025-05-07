package Kopylov.webrise.advice;

import Kopylov.webrise.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static Kopylov.webrise.constants.ConfigurationConstants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        log.error(exception.getMessage());
        return new ErrorResponse(DATA_NOT_FOUND, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptionBadRequest(BindException exception, HttpServletRequest request) {
        log.error("Ошибка входных данных. " +  exception.getMessage());
        return new ErrorResponse(INPUT_DATA_ERROR, exception.getFieldError().getDefaultMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFeignExceptionBadRequest(ConstraintViolationException exception, HttpServletRequest request) {
        log.error(exception.getMessage());
        return new ErrorResponse(INPUT_DATA_ERROR, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }


    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleSQLException(SQLException exception, HttpServletRequest request) {
        log.error(exception.getMessage());
        return new ErrorResponse(SQL_EXCEPTION, exception.getMessage(), request.getMethod() + " " + request.getRequestURI(), getTime());
    }

    public record ErrorResponse(String title, String detail, String request, String time) {
    }
    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
