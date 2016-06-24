package org.shved.webacs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/17/16.
 */

public class AppException extends RuntimeException {

    public AppException() {
        super();
    }

    public AppException(String message, Throwable e) {
        super(message, e);
    }
}
