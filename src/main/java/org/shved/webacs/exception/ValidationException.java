package org.shved.webacs.exception;

/**
 * @author dshvedchenko on 6/24/16.
 */
public class ValidationException extends RuntimeException {

    public ValidationException() {
        super();
    }

    public ValidationException(String message, Throwable e) {
        super(message, e);
    }
}
