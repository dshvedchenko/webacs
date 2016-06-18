package org.shved.webacs.exception;

/**
 * @author dshvedchenko on 6/17/16.
 */
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token already expired");
    }
}
