package de.motivational.stairs.rest.api.v1.logging;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.service.LoggingService;
import de.motivational.stairs.rest.dto.LoggingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api/v1.0/logging")
public class LoggingServiceREST {

    @Autowired
    LoggingService loggingService;

    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody LoggingDto loggingDto) {
        return loggingService.create(loggingDto);
    }
}