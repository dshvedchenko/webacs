package org.shved.webacs.dao;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestResourceDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestResourceDAO.class);

    @Autowired
    private ResourceDAO resourceDAO;


    @Test
    public void daoExists() {
        Assert.assertNotNull(resourceDAO);
    }

    @Test
    public void findAllResources() {
        List<Resource> resourceList = resourceDAO.findAllResources();
        Assert.assertNotNull(resourceList);
        Assert.assertTrue(resourceList.size() > 0);
    }
}
