package org.shved.webacs.dao;

import org.shved.webacs.model.ClaimState;

/**
 * @author dshvedchenko on 6/21/16.
 */
public interface ClaimStateDAO {
    ClaimState getById(Integer id);
}
