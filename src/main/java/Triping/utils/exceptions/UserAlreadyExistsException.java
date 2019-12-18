package Triping.utils.exceptions;

public class UserAlreadyExistsException extends BusinessLogicException {
    public UserAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }

}
