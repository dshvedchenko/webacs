package org.shved.webacs.dao;

import org.shved.webacs.model.ResType;
import org.shved.webacs.model.Resource;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public interface IResTypeDAO {

    List<ResType> findAll();

    ResType findById(Long id);

    void saveResType(ResType resType);

    void delete(ResType resType);

    void deleteById(Long id);

    ResType findByName(String name);

}
