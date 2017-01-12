package de.motivational.stairs.rest.api.v1.setup.config;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.config.AppConfig;
import de.motivational.stairs.database.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api/v1.0/setup/config")
public class ConfigServiceREST {

    @Autowired
    ConfigService configService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody AppConfig get() {
        return configService.getAppConfig();
    }

    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody void update(@RequestBody AppConfig appConfig) {
        configService.update(appConfig);
    }
}