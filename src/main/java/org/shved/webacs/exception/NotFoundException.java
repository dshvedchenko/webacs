package org.shved.webacs.exception;

import org.shved.webacs.model.AppUser;

/**
 * @author dshvedchenko on 6/26/16.
 */
public class NotFoundException extends AppException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String message, Throwable e) {
        super(message, e);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
