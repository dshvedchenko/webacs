package org.shved.webacs.model;

/**
 * @author dshvedchenko on 6/26/16.
 */
public enum SysRole {
    GENERIC(1),
    ADMIN(0);

    private Integer code;

    private SysRole(Integer code) {
        this.code = code;
    }
}
