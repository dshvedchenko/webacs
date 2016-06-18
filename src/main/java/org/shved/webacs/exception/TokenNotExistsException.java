package org.shved.webacs.exception;

/**
 * @author dshvedchenko on 6/17/16.
 */
public class TokenNotExistsException extends RuntimeException {
    public TokenNotExistsException() {
        super("Token does not exists");
    }
}
