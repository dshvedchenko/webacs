package org.shved.webacs.response;

import lombok.Data;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Data
public class Error {
    private int status;
    private String message;
}
