package de.motivational.stairs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Florian on 15.11.2016.
 */
@Controller
@RequestMapping(value="/settings")
public class BeamerConfiguration {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        System.out.println("INDEX CALL " + mav);
        return mav;
    }

    @RequestMapping(value = "/1/", method = RequestMethod.GET)
    public String index1() {
        System.out.println("INDEX CALL 1");
        return "index";
    }
}
