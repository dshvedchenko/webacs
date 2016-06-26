package org.shved.webacs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.SysRole;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
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
    //  private String password;

    private String email;
    private SysRole sysrole;
    private boolean enabled;


    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date created_at;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date updated_at;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSSZ")
    private Date disabled_at;

}
