package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.ResType;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestResTypeDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestResTypeDAO.class);


    @Autowired
    private IResTypeDAO resTypeDAO;

    @Test
    public void findAllTest() {
        List<ResType> list = resTypeDAO.findAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() == 3);
    }

    @Test
    @Transactional
    public void findById() {
        ResType resType = resTypeDAO.findById(1);

        Assert.assertNotNull(resType);
        Assert.assertEquals("wiki", resType.getName());
    }

    @Test
    @Transactional
    public void delete() {
        ResType resType = resTypeDAO.findById(1);
        resTypeDAO.delete(resType);
        Assert.assertNull(resTypeDAO.findById(1));
    }

    @Test
    @Transactional
    public void deleteByIdTest() {
        ResType resType = resTypeDAO.findById(1);
        resTypeDAO.deleteById(resType.getId());
        Assert.assertNull(resTypeDAO.findById(1));
    }

    @Test
    public void findByNameTest() {
        ResType restype = resTypeDAO.findByName("wiki");
        Assert.assertNotNull(restype);
    }

}
