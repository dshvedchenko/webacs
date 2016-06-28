package org.shved.webacs.dao;

import org.hibernate.Session;
import org.shved.webacs.model.AppUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/10/16.
 */
public interface IAppUserDAO {

    List<AppUser> findAllAppUsers();

    List<AppUser> findAllEnabled();

    List<AppUser> findAllDisabled();

    AppUser findById(Long id);

    void save(AppUser user);

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);
}
