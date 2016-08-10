package org.shved.webacs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.shved.webacs.constants.MessageConversion;
import org.shved.webacs.model.SysRole;

import java.util.Date;

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
            (shape = JsonFormat.Shape.STRING, pattern = MessageConversion.JsonTimestampPattern)
    private Date created_at;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = MessageConversion.JsonTimestampPattern)
    private Date updated_at;
    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = MessageConversion.JsonTimestampPattern)
    private Date disabled_at;

}
