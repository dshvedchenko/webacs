package org.shved.webacs.dto;

import lombok.Getter;

/**
 * @author dshvedchenko on 6/24/16.
 */
public class FieldErrorDTO {
    @Getter
    private String field;
    @Getter
    private String message;

    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
