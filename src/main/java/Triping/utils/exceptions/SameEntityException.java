package Triping.utils.exceptions;

public class SameEntityException extends BadRequestException {
    public SameEntityException(final String message) {
        super(message);
    }

    public SameEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}