package Triping.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TripDoesNotExistsException extends RuntimeException{

    public TripDoesNotExistsException() { super(); }

    public TripDoesNotExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TripDoesNotExistsException(final String message) {
        super(message);
    }

    public TripDoesNotExistsException(final Throwable cause) {
        super(cause);
    }
}

