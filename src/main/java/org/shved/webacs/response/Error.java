package org.shved.webacs.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.Transient;
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

    ;

    public Error(Exception e, HttpServletRequest request) {
        this.setMessage(e.getMessage());
        if (request != null) {
            errorURL = request.getRequestURL().toString();
        }
    }
}
