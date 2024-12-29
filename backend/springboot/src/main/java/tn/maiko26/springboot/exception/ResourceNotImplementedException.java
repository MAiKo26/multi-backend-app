package tn.maiko26.springboot.exception;

public class ResourceNotImplementedException extends CustomException {
    public ResourceNotImplementedException() {
        super("This feature is not Implemented", 501);
    }
}