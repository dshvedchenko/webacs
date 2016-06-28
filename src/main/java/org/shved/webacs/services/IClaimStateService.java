package org.shved.webacs.services;

import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.model.ClaimState;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/28/16.
 */
public interface IClaimStateService {
    @Transactional
    List<ClaimStateDTO> getAll();

    @Transactional
    ClaimStateDTO getById(Integer id);
}
