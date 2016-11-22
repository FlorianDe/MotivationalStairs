package de.motivational.stairs.rest.api.v1.setup.beamer;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.repository.BeamerService;
import de.motivational.stairs.rest.dto.setup.BeamerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value="/api/v1.0/settings/beamer")
public class BeamerServiceREST {

    @Autowired
    BeamerService beamerService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody BeamerDto[] getAll() {
        BeamerDto[] beamers = beamerService
                .findAll().stream()
                .map(BeamerDto::new)
                .toArray(BeamerDto[]::new);
        return beamers;
    }

    @RequestMapping(value="/{beamerId}", method= RequestMethod.GET)
    @ResponseBody BeamerDto getOneById(@PathVariable int beamerId) {
        Optional<BeamerDto> beamerDto = beamerService.findOne(beamerId).map(BeamerDto::new);
        return beamerDto.isPresent()?beamerDto.get():null;
    }


    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody BeamerDto beamerDto) {
        return beamerService.create(beamerDto);
    }

    @RequestMapping(value="/{beamerId}", method= RequestMethod.DELETE)
    @ResponseBody boolean delete(@PathVariable int beamerId) {
        return beamerService.delete(beamerId);
    }

    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody void update(@RequestBody BeamerDto beamerDto) {
        beamerService.update(beamerDto);
    }
}