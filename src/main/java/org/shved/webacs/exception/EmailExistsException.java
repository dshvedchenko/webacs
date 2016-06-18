package org.shved.webacs.exception;

/**
 * @author dshvedchenko on 6/17/16.
 */
public class EmailExistsException extends AppException {
    public EmailExistsException() {
        super();
    }

    public EmailExistsException(String message, Throwable e) {
        super(message, e);
    }
}
