package org.example.spring_quiz_application.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(int id) {
        super("Could not find resource with id " + id);
    }
}
