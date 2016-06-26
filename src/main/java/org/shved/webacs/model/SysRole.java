package org.shved.webacs.model;

/**
 * @author dshvedchenko on 6/26/16.
 */
public enum SysRole {
    ADMIN(0),
    GENERIC(1);

    private Integer code;

    private SysRole(Integer code) {
        this.code = code;
    }
}
