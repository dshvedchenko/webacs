package org.shved.webacs.services;

import org.shved.webacs.dto.ResTypeDTO;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author dshvedchenko on 6/28/16.
 */
public interface IResTypeService {

    List<ResTypeDTO> getAll();


    ResTypeDTO getById(Integer id);


    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteById(Integer id);


    @PreAuthorize("hasAuthority('ADMIN')")
    ResTypeDTO save(ResTypeDTO resTypeDTO);
}
