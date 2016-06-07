package org.shved.webacs.dto;

import lombok.Data;

/**
 * @author dshvedchenko on 6/7/16.
 */
@Data
public class Home {
    private Address address;
    private Door door;
    private Window[] windows;
}
