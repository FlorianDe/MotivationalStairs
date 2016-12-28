package de.motivational.stairs.rest.api.v1.setup.stairs;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.service.StairsService;
import de.motivational.stairs.rest.dto.setup.StairsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value="/api/v1.0/setup/stairs")
public class StairsServiceREST {

    @Autowired
    StairsService stairsService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody StairsDto[] getAll() {
        StairsDto[] stairs = stairsService
                .findAll().stream()
                .map(StairsDto::new)
                .toArray(StairsDto[]::new);
        return stairs;
    }

    @RequestMapping(value="/{stairsId}", method= RequestMethod.GET)
    @ResponseBody StairsDto getOneById(@PathVariable int stairsId) {
        Optional<StairsDto> stairsDto = stairsService.findOne(stairsId).map(StairsDto::new);
        return stairsDto.isPresent()?stairsDto.get():null;
    }


    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody StairsDto stairsDto) {
        return stairsService.create(stairsDto);
    }

    @RequestMapping(value="/{stairsId}", method= RequestMethod.DELETE)
    @ResponseBody boolean delete(@PathVariable int stairsId) {
        return stairsService.delete(stairsId);
    }

    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody void update(@RequestBody StairsDto stairsDto) {
        stairsService.update(stairsDto);
    }
}