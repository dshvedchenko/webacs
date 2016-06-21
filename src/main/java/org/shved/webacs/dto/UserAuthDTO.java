package org.shved.webacs.dto;

import lombok.Data;

/**
 * @author dshvedchenko on 6/17/16.
 */
@Data
public class UserAuthDTO {
    private String username;
    private String password;
    private String token;
}
