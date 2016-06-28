package org.shved.webacs.dao.impl;

import org.shved.webacs.dao.AbstractDao;
import org.shved.webacs.dao.IResTypeDAO;
import org.shved.webacs.model.ResType;

import java.util.List;

/**
 * @author dshvedchenko on 6/28/16.
 */
public class ResTypeDAOImpl extends AbstractDao<Integer, ResType> implements IResTypeDAO {
    @Override
    public List<ResType> findAll() {
        return null;
    }

    @Override
    public ResType findById(Long id) {
        return null;
    }

    @Override
    public void saveResType(ResType resType) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public ResType findByName(String name) {
        return null;
    }
}
