package org.shved.webacs.controller;

import org.shved.webacs.dto.Address;
import org.shved.webacs.dto.Door;
import org.shved.webacs.dto.Home;
import org.shved.webacs.dto.Window;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author dshvedchenko on 6/7/16.
 */
@RestController
@RequestMapping("home")
public class HomeController {

    @RequestMapping(value = "win/{window}")
    public
    @ResponseBody
    Home
    getHome(@PathVariable(value = "window") Integer winNum) {
        Home home = new Home();
        home.setAddress(new Address());
        home.getAddress().setCity("London");
        home.setDoor(new Door());
        home.getDoor().setName("Ulet");
        if (winNum == null) winNum = 10;
        home.setWindows(new Window[winNum]);
        for (int i = 0; i < winNum; i++) {
            home.getWindows()[i] = new Window();
            home.getWindows()[i].setHeight(10);
            home.getWindows()[i].setWidtg(5);
        }
        return home;
    }
}
