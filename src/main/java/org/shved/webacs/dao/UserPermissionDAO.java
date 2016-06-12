package org.shved.webacs.dao;

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

    void disable(UserPermission userPermission);

    void saveUserPermission(UserPermission userPermission);

    UserPermission findByClaimId(Long claimId);

}
