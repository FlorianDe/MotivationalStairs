package de.motivational.stairs.rest;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.beamer.AppPrincipalFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/beamer")
public class BeamerSettingService {

    @Autowired
    AppPrincipalFrame appPrincipalFrame;

    @RequestMapping(value="/{title}", method= RequestMethod.GET)
    public void createConnection(@PathVariable String title) {
        appPrincipalFrame.setTitleTestMethod(title);
        System.out.println(appPrincipalFrame.getTitle());
    }
}