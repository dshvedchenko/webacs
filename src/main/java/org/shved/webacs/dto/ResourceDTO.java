package org.shved.webacs.dto;

import lombok.Data;
import org.shved.webacs.model.Permission;

/**
 * @author dshvedchenko on 6/26/16.
 */
@Data
public class ResourceDTO {
    private Long id;
    private String kind;
    private String name;
    private String detail;
    //TODO clarify
    private Permission ownerPermission;
}
