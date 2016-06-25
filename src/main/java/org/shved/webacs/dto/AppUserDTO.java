package org.shved.webacs.dto;

import lombok.Data;
import org.shved.webacs.model.Permission;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
@Data
public class AppUserDTO {

    private Long id;
    private String username;

    private String firstname;
    private String lastname;

    private String email;
    private Integer sysrole;
    private boolean enabled;

}
