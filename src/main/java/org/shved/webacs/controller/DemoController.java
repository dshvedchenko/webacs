package org.shved.webacs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dshvedchenko on 6/14/16.
 */
@Controller
public class DemoController {
    @RequestMapping(value = "/simple", produces = {"application/json"})
    public ModelAndView helloPublic() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Hello");
        model.addObject("message", "Public content");
        model.setViewName("hello");
        return model;
    }

    @RequestMapping(value = "/secured", produces = {"application/json"})
    public ModelAndView helloSecured() {
        ModelAndView model = new ModelAndView();
        model.addObject("title", "Hello");
        model.addObject("message", "Secured content");
        model.setViewName("secured");
        return model;
    }
}
