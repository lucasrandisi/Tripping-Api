package Triping.utils.exceptions;

public class BusinessLogicException extends Exception{
    public BusinessLogicException(final String message) {
        super(message);
    }

    public BusinessLogicException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
