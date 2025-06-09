package com.devs.product.api.exception;

import com.devs.product.api.dto.ResponseExceptionDTO;
import com.devs.product.api.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotFoundProductException.class)
    public ResponseEntity<ResponseExceptionDTO> handleProductNotFound(NotFoundProductException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AppBaseException.class)
    public ResponseEntity<ResponseExceptionDTO> handleAppBase(AppBaseException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseExceptionDTO> handleParametertNotFound(MissingServletRequestParameterException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(Constants.NOT_FOUND_PARAMETER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseExceptionDTO> handlePathNotFound(NoResourceFoundException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(Constants.PATH_NOT_FOUND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseExceptionDTO> handleIllegalArgument(IllegalArgumentException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseExceptionDTO> handleArgumentType(MethodArgumentTypeMismatchException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(Constants.ERROR_ARGUMENT_TYPE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exceptionResponse);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex) {
        log.error("Error al acceder a BBDD", ex);
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Constants.SERVICE_UNAVAILABLE);
    }
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<ResponseExceptionDTO> handleProductDataException(ServiceUnavailableException ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(exceptionResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseExceptionDTO> handleGlobalExceptions(Exception ex) {
        ResponseExceptionDTO exceptionResponse = new ResponseExceptionDTO();
        exceptionResponse.setError(Constants.UNEXPECTED_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponse);
    }
}
