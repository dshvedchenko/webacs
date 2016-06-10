package org.shved.webacs.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    private T data;
    private Error error;
}
