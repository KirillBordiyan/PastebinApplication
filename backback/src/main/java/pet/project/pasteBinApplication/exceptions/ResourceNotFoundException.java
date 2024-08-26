package pet.project.pasteBinApplication.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    //когда в БД не нашли

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
