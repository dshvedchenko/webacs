package org.shved.webacs.dto;

import lombok.Data;

/**
 * @author dshvedchenko on 6/7/16.
 */
@Data
public class User {
    private String username;
    private String password;

    private Address address = new Address();

    public User() {
        address.setCity("Mexico");
    }
}
