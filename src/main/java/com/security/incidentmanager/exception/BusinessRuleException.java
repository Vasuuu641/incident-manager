package com.security.incidentmanager.exception;

// Used when an operation is blocked by a business rule
// e.g. deleting an SLA policy that is still referenced by incidents
public class BusinessRuleException extends RuntimeException {
    public BusinessRuleException(String message) {
        super(message);
    }
}