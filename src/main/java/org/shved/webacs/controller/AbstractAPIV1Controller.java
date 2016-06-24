package org.shved.webacs.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dshvedchenko on 6/24/16.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1", consumes = "application/json", produces = "application/json")
public class AbstractAPIV1Controller {
}
