package de.motivational.stairs.rest.api.v1.game;

/**
 * Created by Florian on 11.07.2016.
 */

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.service.GameService;
import de.motivational.stairs.database.service.HighscoreService;
import de.motivational.stairs.rest.dto.GameDto;
import de.motivational.stairs.rest.dto.GameStartDto;
import de.motivational.stairs.rest.dto.GameStartResponseDto;
import de.motivational.stairs.rest.dto.HighscoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value="/api/v1.0/game")
public class GameServiceREST {

    @Autowired
    GameService gameService;

    @RequestMapping(value="/", method= RequestMethod.GET)
    @ResponseBody GameDto[] getAll() {
        return gameService
                .findAll().stream()
                .map(GameDto::new)
                .toArray(GameDto[]::new);
    }

    @RequestMapping(value="/{gameId}", method= RequestMethod.GET)
    @ResponseBody GameDto getOneById(@PathVariable int gameId) {
        Optional<GameDto> gameDto = gameService.findOne(gameId).map(GameDto::new);
        return gameDto.isPresent()?gameDto.get():null;
    }

    @RequestMapping(value="/", method= RequestMethod.POST)
    @ResponseBody Optional<GameEntity> create(@RequestBody GameDto gameDto) {
        return gameService.create(gameDto);
    }

    @RequestMapping(value="/{gameId}", method= RequestMethod.DELETE)
    @ResponseBody void delete(@PathVariable int gameId) {
        gameService.delete(gameId);
    }

    @RequestMapping(value="/start", method= RequestMethod.POST)
    @ResponseBody GameStartResponseDto start(@RequestBody GameStartDto gameDto) {
        return gameService.startGame(gameDto);
    }

    @RequestMapping(value="/redeem/{ticketId}", method= RequestMethod.GET)
    @ResponseBody boolean redeem(@PathVariable String ticketId) {
        return gameService.redeemTicket(ticketId);
    }

    @RequestMapping(value="/abort/{ticketId}", method= RequestMethod.GET)
    @ResponseBody void abort(@PathVariable String ticketId) {
        gameService.abortTicket(ticketId);
    }

    @RequestMapping(value="/queue", method= RequestMethod.GET)
    @ResponseBody String[] getPlayerQueue() {
        return this.gameService.getPlayerQueue();
    }

}