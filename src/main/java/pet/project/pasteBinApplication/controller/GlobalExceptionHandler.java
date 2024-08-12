package pet.project.pasteBinApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = GlobalExceptionHandler.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e){
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(500);
        response.setReason(e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgument(NoSuchElementException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(404);
        response.setReason(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleIOException(IOException e){
        ExceptionResponse response = new ExceptionResponse();
        response.setStatusCode(406);
        response.setReason(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    //TODO пополнять обработкой любого исключения, которое происходит в REST-контроллерах
    // не забыть переместить этот хендлер в папку с рестКонтроллерами
}
