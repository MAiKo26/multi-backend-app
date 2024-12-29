package tn.maiko26.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getStatusCode(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(ex.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(404, "Endpoint not found");

        return ResponseEntity
                .status(404)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalError(Exception ex) {
        ex.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse(500, "Internal server error");

        return ResponseEntity
                .status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
