package Triping.utils.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlredyAddedException extends  Exception {
    public AlredyAddedException(final String message) {
        super(message);
    }

    public AlredyAddedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
