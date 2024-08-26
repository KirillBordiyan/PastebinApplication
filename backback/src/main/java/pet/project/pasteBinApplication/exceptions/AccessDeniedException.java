package pet.project.pasteBinApplication.exceptions;

public class AccessDeniedException extends RuntimeException{
    //когда пользователь закрыл свой текст/файл и посмотреть нельзя (приватное)

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException() {
    }
}
