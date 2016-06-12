package org.shved.webacs.dao;

import junitx.framework.Assert;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.shved.webacs.AbstractRepositoryTest;
import org.shved.webacs.model.Permission;
import org.shved.webacs.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dshvedchenko on 6/12/16.
 */
public class TestPermissionDAO extends AbstractRepositoryTest {

    @Autowired
    PermissionDAO permissionDAO;

    @Autowired
    ResourceDAO resourceDAO;

    @Test
    public void checkPermissionDAO() {
        Assert.assertNotNull(permissionDAO);
    }

    @Test
    public void findAllPermissions() {
        List<Permission> permissions = permissionDAO.findAllPermissions();
        Assert.assertNotNull(permissions);
        Assert.assertTrue(permissions.size() > 0);
    }

    @Test
    public void savePermission() {
        Resource resource = resourceDAO.findAllResources().get(0);
        Permission permission = new Permission();
        permission.setResource(resource);
        permission.setDescription("some description");
        permission.setTitle("Any Kind of Rights");

        permissionDAO.savePermission(permission);
    }

    @Test
    public void delete() {
        Resource resource = resourceDAO.findAllResources().get(0);
        Permission permission = new Permission();
        permission.setResource(resource);
        permission.setDescription("some description");
        permission.setTitle("Any Kind of Rights");

        permissionDAO.savePermission(permission);

        permissionDAO.delete(permission);

        Permission pr = permissionDAO.findById(permission.getId());
        Assert.assertNull(pr);
    }

    @Test
    public void findByResource() {
        Resource resource = resourceDAO.findAllResources().get(0);
        List<Permission> permissions = permissionDAO.findAllByResource(resource);
        Assert.assertNotNull(permissions);
        Assert.assertTrue(permissions.size() > 0);
    }

    @Test
    public void findByTitle() {
        List<Permission> permissions = permissionDAO.findAllByTitle("Editor");
        Assert.assertNotNull(permissions);
        Assert.assertNotNull(permissions.stream().filter(p -> p.getTitle().equals("Editor")).findAny().get());
    }
}
