package com.security.incidentmanager.exception;

// Dedicated exception for "entity not found" — replaces plain RuntimeException
// This lets the handler catch it specifically without catching everything
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}