package org.shved.webacs.dao;

import org.hibernate.Session;
import org.shved.webacs.model.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
public interface AppUserDAO {

    List<AppUser> findAllAppUsers();

    AppUser findById(Long id);

    void saveAppUser(AppUser user);

    void delete(AppUser user);

    void deleteById(Long id);
}
