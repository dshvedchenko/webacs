package org.shved.webacs.exception;

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
