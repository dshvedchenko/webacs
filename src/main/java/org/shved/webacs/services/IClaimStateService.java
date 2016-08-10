package org.shved.webacs.services;

import org.shved.webacs.dto.ClaimStateDTO;

import java.util.List;

/**
 * @author dshvedchenko on 6/28/16.
 */
public interface IClaimStateService {

    List<ClaimStateDTO> getAll();

    ClaimStateDTO getById(Integer id);
}
