package tn.maiko26.springboot.exception;

import jakarta.servlet.ServletException;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;

@Getter
public class CustomException extends RuntimeException {
    private final int statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return this.statusCode;
    }
}