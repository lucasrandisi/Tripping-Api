package Triping.utils.exceptions;

public class AlredyEnabledException extends BadRequestException {

    public AlredyEnabledException(String message) {
        super(message);
    }

    public AlredyEnabledException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
