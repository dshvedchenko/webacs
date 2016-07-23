package org.shved.webacs.services;

import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.PermissionTitleDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 7/1/16.
 */
public interface IPermissionService {

    @Transactional
    PermissionDTO create(PermissionDTO permissionDTO);

    @Transactional
    PermissionDTO getById(Long id);

    @Transactional
    void update(PermissionDTO permissionDTO);

    @Transactional
    void deleteById(Long id);

    @Transactional
    List<PermissionDTO> getAll();

    @Transactional
    List<PermissionTitleDTO> getAllByResourceId(Long resourceId);

}
