package org.shved.webacs.dto;

import lombok.Data;
import org.shved.webacs.model.SysRole;

/**
 * @author dshvedchenko on 6/17/16.
 */
@Data
public class LoggedUserDTO {
    private String username;
    private SysRole sysrole;
    private String token;
}
