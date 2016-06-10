package org.shved.webacs.dao;

import org.shved.webacs.model.AppUser;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
public interface AppUserDAO {
    List<AppUser> list();
}
