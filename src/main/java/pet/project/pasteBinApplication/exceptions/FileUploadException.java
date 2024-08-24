package pet.project.pasteBinApplication.exceptions;

public class FileUploadException extends RuntimeException{

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException() {
    }
}
