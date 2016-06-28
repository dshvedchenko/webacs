package org.shved.webacs.dao;

import org.shved.webacs.model.ResType;
import org.shved.webacs.model.Resource;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public interface IResTypeDAO {

    List<ResType> findAll();

    ResType findById(Integer id);

    void save(ResType resType);

    void delete(ResType resType);

    void deleteById(Integer id);

    ResType findByName(String name);

}
