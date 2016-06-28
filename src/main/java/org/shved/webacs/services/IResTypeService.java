package org.shved.webacs.services;

import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.dto.ResTypeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/28/16.
 */
public interface IResTypeService {
    @Transactional
    List<ResTypeDTO> getAll();

    @Transactional
    ResTypeDTO getById(Integer id);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void save(ResTypeDTO resTypeDTO);
}
