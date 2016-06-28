package org.shved.webacs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/17/16.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserExistsException extends AppException {
    public UserExistsException() {
        super();
    }

    public UserExistsException(String message, Throwable e) {
        super(message, e);
    }
}
