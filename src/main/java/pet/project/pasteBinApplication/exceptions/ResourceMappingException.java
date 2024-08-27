package pet.project.pasteBinApplication.exceptions;

public class ResourceMappingException extends RuntimeException{

    public ResourceMappingException(String message) {
        super(message);
    }

    public ResourceMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
