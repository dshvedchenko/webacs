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
    private IResourceDAO resourceDAO;


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

    @Test
    public void findById() {
        Resource resource = new Resource();
        resource.setResType("Software Instance");
        resource.setName("Redhat Linux AS 32");
        resource.setDetail("some description");
        resourceDAO.save(resource);
        Resource foundRes = resourceDAO.findById(resource.getId());
        Assert.assertNotNull(foundRes);
    }

    @Test
    public void delete() {
        Resource resource = new Resource();
        resource.setResType("Software Instance");
        resource.setName("Redhat Linux AS 32");
        resource.setDetail("some description");
        resourceDAO.save(resource);
        resourceDAO.delete(resource);
        Assert.assertNotNull(resource);
    }


    @Test
    public void deleteById() {
        Resource resource = new Resource();
        resource.setResType("Software Instance");
        resource.setName("Redhat Linux AS 32");
        resource.setDetail("some description");
        resourceDAO.save(resource);
        resourceDAO.deleteById(resource.getId());
        Resource res = resourceDAO.findById(resource.getId());
        Assert.assertNull(res);
    }

    @Test
    public void findAllByKind() {
        List<Resource> resources = resourceDAO.findAllByKind("room");
        Assert.assertTrue(resources.size() > 0);
    }

    @Test
    public void findByName() {
        Resource resource = resourceDAO.findByName("xDep wiki space");
        Assert.assertNotNull(resource);
    }

}
