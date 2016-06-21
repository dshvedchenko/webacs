package org.shved.webacs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/17/16.
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "email already used")
public class EmailExistsException extends AppException {
    public EmailExistsException() {
        super();
    }

    public EmailExistsException(String message, Throwable e) {
        super(message, e);
    }
}
