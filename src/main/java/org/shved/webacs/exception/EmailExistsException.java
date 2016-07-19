package org.shved.webacs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/17/16.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailExistsException extends AppException {
    public EmailExistsException() {
        super("EMAIL already taken");
    }

    public EmailExistsException(String message, Throwable e) {
        super(message, e);
    }
}
