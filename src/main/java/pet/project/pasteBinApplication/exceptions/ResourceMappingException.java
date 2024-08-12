package pet.project.pasteBinApplication.exceptions;

public class ResourceMappingException extends RuntimeException{
    //когда запрос ок, но с преобразованием что-то не так

    public ResourceMappingException(String message) {
        super(message);
    }

    public ResourceMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
