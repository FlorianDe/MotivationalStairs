package de.motivational.stairs.rest.api.v1.setup.beamersetup;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.repository.BeamerSetupService;
import de.motivational.stairs.rest.dto.setup.BeamerSetupDto;
import de.motivational.stairs.rest.dto.setup.SetupsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;


@RestController
@RequestMapping(value="/api/v1.0/settings/beamersetup")
public class BeamerSetupServiceREST {

    @Autowired
    BeamerSetupService beamerSetupService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody SetupsDto getAll() {
        BeamerSetupDto[] beamerSetups = beamerSetupService
                .findAll().stream()
                .map(BeamerSetupDto::new)
                .toArray(size -> new BeamerSetupDto[size]);
        return new SetupsDto(beamerSetups);
    }

    @RequestMapping(value="/{beamerSetupId}", method= RequestMethod.GET)
    @ResponseBody BeamerSetupDto getOneById(@PathVariable int beamerSetupId) {
        Optional<BeamerSetupDto> beamerSetupDto = beamerSetupService.findOne(beamerSetupId).map(BeamerSetupDto::new);
        return beamerSetupDto.isPresent()?beamerSetupDto.get():null;
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody BeamerSetupDto beamerSetupDto) {
        //TODO @VIKTOR NOT POSSIBLE WITH BEAMERID AND STAIRSID ONLY
        //return beamerSetupService.create(beamerSetupDto);
        throw new NotImplementedException();
    }

    @RequestMapping(value="/{beamerSetupId}", method= RequestMethod.DELETE)
    @ResponseBody boolean delete(@PathVariable int beamerSetupId) {
        return beamerSetupService.delete(beamerSetupId);
    }

    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody void update(@RequestBody BeamerSetupDto beamerSetupDto) {
        beamerSetupService.update(beamerSetupDto);
    }
}