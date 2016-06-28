package org.shved.webacs.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<DATA> {
    private DATA data;
    private Error error;

    public ResponseData() {
    }

    public ResponseData(DATA data) {
        this.data = data;
    }
}
