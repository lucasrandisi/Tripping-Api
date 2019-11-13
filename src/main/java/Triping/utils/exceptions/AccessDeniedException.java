package Triping.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccessDeniedException extends RuntimeException{

    public AccessDeniedException() { super(); }

    public AccessDeniedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AccessDeniedException(final String message) {
        super(message);
    }

    public AccessDeniedException(final Throwable cause) {
        super(cause);
    }
}

