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
public class TestResourceDAO extends AbstractRepositoryTest {

    Logger logger = LoggerFactory.logger(TestResourceDAO.class);

    @Autowired
    private IResourceDAO resourceDAO;

    @Autowired
    private IResTypeDAO resTypeDAO;

    @Test
    public void findAllResources() {
        List<Resource> resourceList = resourceDAO.findAllResources();
        Assert.assertNotNull(resourceList);
        Assert.assertTrue(resourceList.size() > 0);
    }

    @Test
    @Transactional
    public void findById() {
        initResType();
        ResType resType = resTypeDAO.findByName("Software Instance");

        Resource resource = new Resource();
        resource.setResType(resType);
        resource.setName("Redhat Linux AS 32");
        resource.setDetail("some description");
        resourceDAO.save(resource);
        Resource foundRes = resourceDAO.findById(resource.getId());
        Assert.assertNotNull(foundRes);
    }

    @Test
    @Transactional
    public void delete() {
        initResType();
        ResType resType = resTypeDAO.findByName("Software Instance");

        Resource resource = new Resource();
        resource.setResType(resType);
        resource.setName("Redhat Linux AS 32");
        resource.setDetail("some description");
        resourceDAO.save(resource);
        resourceDAO.delete(resource);
        Assert.assertNotNull(resource);
    }

    @Test
    @Transactional
    public void deleteById() {
        initResType();
        ResType resType = resTypeDAO.findByName("Software Instance");

        Resource resource = new Resource();
        resource.setResType(resType);
        resource.setName("Redhat Linux AS 32");
        resource.setDetail("some description");
        resourceDAO.save(resource);
        resourceDAO.deleteById(resource.getId());
        Resource res = resourceDAO.findById(resource.getId());
        Assert.assertNull(res);
    }


    @Test
    public void findAllByResTypeName() {
        List<Resource> resources = resourceDAO.findAllByTypeName("room");
        Assert.assertTrue(resources.size() == 1);
    }

    @Test
    public void findAllByResTypeId() {
        List<Resource> resources = resourceDAO.findAllByResTypeId(1);
        Assert.assertTrue(resources.size() == 1);
//        Assert.assertEquals();
    }


    @Test
    public void findByName() {
        Resource resource = resourceDAO.findByName("xDep wiki space");
        Assert.assertNotNull(resource);
    }

    public void initResType() {
        ResType resType = new ResType();
        resType.setName("Software Instance");
        resTypeDAO.save(resType);
    }

}
