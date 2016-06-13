package org.shved.webacs.dao;

import org.shved.webacs.model.Permission;
import org.shved.webacs.model.PermissionClaim;
import org.shved.webacs.model.UserPermission;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public interface UserPermissionDAO {

    List<UserPermission> findAllUserPermissions();

    UserPermission findById(Long id);

    List<UserPermission> findAllByUserId(Long Id);

    List<UserPermission> findAllByPermission(Long id);

    void deleteByClaim(PermissionClaim claim);

    void saveUserPermission(UserPermission userPermission);

    UserPermission findByClaim(PermissionClaim claim);

    List<UserPermission> findAllToBeRevoked();

}
