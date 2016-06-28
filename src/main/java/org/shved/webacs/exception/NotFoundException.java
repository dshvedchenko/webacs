package org.shved.webacs.exception;

import org.shved.webacs.model.AppUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/26/16.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
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
