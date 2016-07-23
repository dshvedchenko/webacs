package org.shved.webacs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/17/16.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message, Throwable e) {
        super(String.format(message + " [ %s ]", e.getMessage()), e);
    }

    public AppException(String message) {
        super(message);
    }
}
