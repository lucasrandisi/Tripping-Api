package Triping.utils.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class AlredyAddedException extends  Exception {
    public AlredyAddedException(final String message) {
        super(message);
    }

    public AlredyAddedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
