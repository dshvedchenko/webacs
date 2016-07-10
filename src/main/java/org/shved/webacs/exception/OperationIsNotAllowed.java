package org.shved.webacs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dshvedchenko on 6/26/16.
 */
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class OperationIsNotAllowed extends AppException {
    public OperationIsNotAllowed() {
        super();
    }

    public OperationIsNotAllowed(String message, Throwable e) {
        super(message, e);
    }

    public OperationIsNotAllowed(String message) {
        super(message);
    }

}
