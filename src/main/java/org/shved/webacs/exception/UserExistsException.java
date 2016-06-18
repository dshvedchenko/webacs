package org.shved.webacs.exception;

/**
 * @author dshvedchenko on 6/17/16.
 */
public class UserExistsException extends AppException {
    public UserExistsException() {
        super();
    }

    public UserExistsException(String message, Throwable e) {
        super(message, e);
    }
}
