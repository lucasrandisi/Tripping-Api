package Triping.utils.exceptions;

public class AlredyEnabledException extends BusinessLogicException {

    public AlredyEnabledException(String message) {
        super(message);
    }

    public AlredyEnabledException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
