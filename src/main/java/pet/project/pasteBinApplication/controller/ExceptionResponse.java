package pet.project.pasteBinApplication.controller;

import lombok.Data;

@Data
public class ExceptionResponse {
    private int statusCode;
    private String reason;
}
