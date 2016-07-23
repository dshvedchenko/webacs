package org.shved.webacs.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.shved.webacs.model.Permission;

/**
 * @author dshvedchenko on 6/26/16.
 */
@Data
public class ResourceDTO {
    private Long id;
    private ResTypeDTO resType;
    private String name;
    private String detail;
    private PermissionTitleDTO ownerPermission;

}
