package org.shved.webacs.dto;

import lombok.Data;

/**
 * @author dshvedchenko on 6/26/16.
 */
@Data
public class UserCreationDTO extends AppUserDTO {
    private String password;
}
