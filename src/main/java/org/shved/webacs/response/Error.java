package org.shved.webacs.response;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Data
public class Error {
    private String message;
    private String errorURL;

    public Error() {
    }

    public Error(Exception e, HttpServletRequest request) {
        this.setMessage(e.getMessage());
        if (request != null) {
            errorURL = request.getRequestURL().toString();
        }
    }
}
