package org.shved.webacs.services.impl;

import org.shved.webacs.dao.IResourceDAO;
import org.shved.webacs.services.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dshvedchenko on 6/13/16.
 */
@Service
public class ResourceServiceImpl implements IResourceService {

    @Autowired
    IResourceDAO resourceDAO;

}
