package Triping.utils.exceptions;

public class AlredyAddedException extends  BadRequestException {
    public AlredyAddedException(final String message) {
        super(message);
    }

    public AlredyAddedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
