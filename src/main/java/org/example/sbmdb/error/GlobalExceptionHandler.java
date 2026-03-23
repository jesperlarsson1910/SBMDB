package org.example.sbmdb.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_VIEW = "error";

    private String errorView(Model model, int status, String message) {
        model.addAttribute("status", status);
        model.addAttribute("message", message);
        return ERROR_VIEW;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Object handleNotFound(EntityNotFoundException e, HttpServletRequest request, Model model) {
        System.out.println("URI: " + request.getRequestURI());
        System.out.println("isApi: " + isApiRequest(request));
        if (isApiRequest(request)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        return errorView(model, 404, e.getMessage());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public Object handleDuplicate(DuplicateEntityException e, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        return errorView(model, 409, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        return errorView(model, 400, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidation(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleNoResourceFound(NoResourceFoundException e, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        return errorView(model, 404, "The page you're looking for doesn't exist.");
    }

    @ExceptionHandler(Exception.class)
    public Object handleGeneric(Exception e, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        return errorView(model, 500, "An unexpected error occurred.");
    }


    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/api");
    }
}