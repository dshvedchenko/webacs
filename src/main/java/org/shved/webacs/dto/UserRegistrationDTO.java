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
public class UserRegistrationDTO {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters long.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric with no spaces")
    private String username;
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;

    private String confirmPassword;
    private String firstName;
    private String lastName;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", message = "Invalid email address.")
    private String email;
    private Integer sysrole;
    private List<Permission> permissions;
}
