package org.shved.webacs.dto;

import lombok.Data;

/**
 * @author dshvedchenko on 7/1/16.
 */
@Data
public class PermissionDTO {
    private Long id;
    private String title;
    private String description;
    private ResourceDTO resource;
    private AppUserDTO appUsers;
}
