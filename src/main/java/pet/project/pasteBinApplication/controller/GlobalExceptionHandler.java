package pet.project.pasteBinApplication.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pet.project.pasteBinApplication.exceptions.AccessDeniedException;
import pet.project.pasteBinApplication.exceptions.ResourceMappingException;
import pet.project.pasteBinApplication.exceptions.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice//(basePackageClasses = GlobalExceptionHandler.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(Exception e) {
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    //TODO этот под вопросом, вроде как его можно заменить IllegalStateExc
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleIllegalArgument(NoSuchElementException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse handleIOException(IOException e) {
        return new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleIllegalStateException(IllegalStateException e) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ResourceMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleResourceMapping(ResourceMappingException e) {
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleAccessDenied() {
        return new ExceptionResponse(HttpStatus.FORBIDDEN.value(), "Access denied!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_GATEWAY.value(), "Some validation fail!");
        List<FieldError> errorsList = e.getBindingResult().getFieldErrors();
        response.setErrors(errorsList.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return response;
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleConstraintViolation(ConstraintViolationException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), "Some validation fail!");
        response.setErrors(e.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));

        return response;
    }
}
