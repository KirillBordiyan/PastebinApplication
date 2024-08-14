package pet.project.pasteBinApplication.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionResponse {

    private int statusCode;
    private String message;
    private Map<String, String> errors;

    public ExceptionResponse(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }

//    public ExceptionResponse(String message) {
//        this.message = message;
//    }


}
