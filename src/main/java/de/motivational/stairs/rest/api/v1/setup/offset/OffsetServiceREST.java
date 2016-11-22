package de.motivational.stairs.rest.api.v1.setup.offset;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.repository.OffsetService;
import de.motivational.stairs.rest.dto.setup.OffsetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value="/api/v1.0/settings/offset")
public class OffsetServiceREST {

    @Autowired
    OffsetService offsetService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody OffsetDto[] getAll() {
        OffsetDto[] offsetDtos = offsetService
                .findAll().stream()
                .map(OffsetDto::new)
                .toArray(OffsetDto[]::new);
        return offsetDtos;
    }

    @RequestMapping(value="/{offsetId}", method= RequestMethod.GET)
    @ResponseBody OffsetDto getOneById(@PathVariable int offsetId) {
        Optional<OffsetDto> stairsDto = offsetService.findOne(offsetId).map(OffsetDto::new);
        return stairsDto.isPresent()?stairsDto.get():null;
    }


    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody OffsetDto offsetDto) {
        return offsetService.create(offsetDto);
    }

    @RequestMapping(value="/{offsetId}", method= RequestMethod.DELETE)
    @ResponseBody boolean delete(@PathVariable int offsetId) {
        return offsetService.delete(offsetId);
    }

    @RequestMapping(value="/", method= RequestMethod.PUT)
    @ResponseBody void update(@RequestBody OffsetDto offsetDto) {
        offsetService.update(offsetDto);
    }
}