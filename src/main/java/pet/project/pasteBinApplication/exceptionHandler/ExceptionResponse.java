package pet.project.pasteBinApplication.exceptionHandler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionResponse {

    int statusCode;
    String message;
    Map<String, String> errors;

    public ExceptionResponse(int statusCode, String message) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
