package org.shved.webacs.controller;

import org.shved.webacs.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dshvedchenko on 6/7/16.
 */
@RestController
@RequestMapping("first")
public class FirstController {

    Logger logger = LoggerFactory.getLogger(FirstController.class);

    // /first/hello
    @RequestMapping(method = RequestMethod.GET, value = "hello/{valueP}")
    public
    @ResponseBody
    User m1(@PathVariable String valueP, Model model) {
        logger.error("Privet M1 : " + valueP);
        model.addAttribute("mest", "12344");
        User user = new User();
        user.setUsername("kukareku");
        user.setPassword("23ejkdjfkwjef");

        return user;
    }

    // /first/hi
    @RequestMapping(method = RequestMethod.GET, path = "hi")
    public void m2() {
        logger.error("Privet M2 AAAA");
    }
}
