package org.shved.webacs.services;

import org.shved.webacs.dto.PermissionDTO;
import org.shved.webacs.dto.PermissionTitleDTO;

import java.util.List;

/**
 * @author dshvedchenko on 7/1/16.
 */
public interface IPermissionService {


    PermissionDTO create(PermissionDTO permissionDTO);


    PermissionDTO getById(Long id);


    void update(PermissionDTO permissionDTO);


    void deleteById(Long id);


    List<PermissionDTO> getAll();


    List<PermissionTitleDTO> getAllByResourceId(Long resourceId);

}
