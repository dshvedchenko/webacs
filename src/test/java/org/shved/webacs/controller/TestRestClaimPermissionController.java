package org.shved.webacs.controller;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author dshvedchenko on 7/5/16.
 */
public class TestRestClaimPermissionController extends AbstractAppTest {


    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }
}
