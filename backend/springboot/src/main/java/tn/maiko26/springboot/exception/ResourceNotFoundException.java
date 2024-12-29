package tn.maiko26.springboot.exception;


public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException() {
        super("Endpoint not found.", 404);

    }
}