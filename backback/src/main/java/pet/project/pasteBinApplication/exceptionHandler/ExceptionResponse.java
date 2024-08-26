package pet.project.pasteBinApplication.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}

//FIXME 4 часть