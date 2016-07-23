package org.shved.webacs.dto;

import lombok.Data;

/**
 * @author dshvedchenko on 6/26/16.
 */
@Data
public class ResourceCreationDTO {
    private Long id;
    private ResTypeDTO resType;
    private String name;
    private String detail;
}
