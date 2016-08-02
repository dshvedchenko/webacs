package org.shved.webacs.controller;

import org.shved.webacs.constants.RestEndpoints;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dshvedchenko on 6/24/16.
 */
@RequestMapping(value = RestEndpoints.API_V1, consumes = "application/json", produces = "application/json")
public abstract class AbstractAPIV1Controller {
}
