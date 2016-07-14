package org.shved.webacs.services;

import org.shved.webacs.model.AppUser;

/**
 * @author dshvedchenko on 7/14/16.
 */
public interface IContextUserService {
    AppUser getContextUser();
}
