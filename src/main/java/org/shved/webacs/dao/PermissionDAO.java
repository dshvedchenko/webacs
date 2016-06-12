package org.shved.webacs.dao;

import org.shved.webacs.model.AppUser;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.Resource;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public interface PermissionDAO {
    List<Permission> findAllPermissions();

    Permission findById(Long id);

    void savePermission(Permission permission);

    void delete(Permission permission);

    void deleteById(Long id);

    Permission findByTitle(String title);

    List<Permission> findAllByAppUser(AppUser appUser);

    List<Permission> findAllByResource(Resource resource);

}
