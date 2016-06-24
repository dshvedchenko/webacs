package org.shved.webacs.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dshvedchenko on 6/24/16.
 */
public class ValidationErrorDTO {
    @Getter
    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public ValidationErrorDTO() {

    }

    public void addFieldError(String path, String message) {
        FieldErrorDTO error = new FieldErrorDTO(path, message);
        fieldErrors.add(error);
    }
}
