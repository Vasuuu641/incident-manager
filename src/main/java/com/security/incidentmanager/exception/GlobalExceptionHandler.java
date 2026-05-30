package com.security.incidentmanager.exception;

import com.security.incidentmanager.exception.BusinessRuleException;
import com.security.incidentmanager.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handles entity not found — e.g. /incidents/999 where 999 doesn't exist
    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleEntityNotFound(EntityNotFoundException ex,
                                       HttpServletRequest request,
                                       Model model) {
        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
        }
        model.addAttribute("errorCode", 404);
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    // Handles business rule violations — e.g. deleting an SLA policy in use
    @ExceptionHandler(BusinessRuleException.class)
    public Object handleBusinessRule(BusinessRuleException ex,
                                     HttpServletRequest request,
                                     Model model) {
        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("CONFLICT", ex.getMessage()));
        }
        model.addAttribute("errorCode", 409);
        model.addAttribute("errorTitle", "Action Not Allowed");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    // Handles access denied — e.g. analyst trying to access admin-only page
    @ExceptionHandler(AccessDeniedException.class)
    public Object handleAccessDenied(AccessDeniedException ex,
                                     HttpServletRequest request,
                                     Model model) {
        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("FORBIDDEN",
                            "You do not have permission to perform this action"));
        }
        model.addAttribute("errorCode", 403);
        model.addAttribute("errorTitle", "Access Denied");
        model.addAttribute("errorMessage",
                "You do not have permission to perform this action");
        return "error";
    }

    // Catches anything else unexpected — last resort
    @ExceptionHandler(Exception.class)
    public Object handleGeneral(Exception ex,
                                HttpServletRequest request,
                                Model model) {
        if (isApiRequest(request)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("INTERNAL_ERROR",
                            "An unexpected error occurred"));
        }
        model.addAttribute("errorCode", 500);
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute("errorMessage", "An unexpected error occurred");
        return "error";
    }

    // Checks if request came from /api/** to decide JSON vs HTML response
    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api/");
    }

    // Simple record for API error responses
    public record ErrorResponse(String error, String message) {}
}