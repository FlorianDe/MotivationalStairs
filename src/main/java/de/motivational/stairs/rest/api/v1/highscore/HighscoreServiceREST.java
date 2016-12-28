package de.motivational.stairs.rest.api.v1.highscore;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.service.HighscoreService;
import de.motivational.stairs.rest.dto.HighscoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping(value="/api/v1.0/highscore")
public class HighscoreServiceREST {

    @Autowired
    HighscoreService highscoreService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody HighscoreDto[] getAll() {
        return highscoreService
                .findAll().stream()
                .map(HighscoreDto::new)
                .toArray(HighscoreDto[]::new);
    }

    @RequestMapping(value="/{highscoreId}", method= RequestMethod.GET)
    @ResponseBody HighscoreDto getOneById(@PathVariable int highscoreId) {
        Optional<HighscoreDto> highscoreDto = highscoreService.findOne(highscoreId).map(HighscoreDto::new);
        return highscoreDto.isPresent()?highscoreDto.get():null;
    }


    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody boolean create(@RequestBody HighscoreDto highscoreDto) {
        return highscoreService.create(highscoreDto);
    }

    @RequestMapping(value="/{highscoreId}", method= RequestMethod.DELETE)
    @ResponseBody void delete(@PathVariable int highscoreId) {
        highscoreService.delete(highscoreId);
    }
}