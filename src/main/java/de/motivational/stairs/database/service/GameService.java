package de.motivational.stairs.database.service;

import de.motivational.stairs.beamer.AppPrincipalFrame;
import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.rest.dto.GameDto;
import de.motivational.stairs.rest.dto.GameStartDto;
import de.motivational.stairs.rest.dto.GameStartResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private AppPrincipalFrame principalFrame;

    @Autowired
    private UserRepository userRepository;

    public Collection<GameEntity> findAll() {
        return gameRepository.findAll();
    }

    public Optional<GameEntity> findOne(int gameId) {
        return Optional.ofNullable(gameRepository.findOne(gameId));
    }

    public Optional<GameEntity> create(GameDto gameDto) {
        Optional<GameEntity> resUser = Optional.empty();
        if(gameDto.getName() != null && !gameDto.getName().isEmpty()) {
            GameEntity game = new GameEntity();
            game.setName(game.getName());
            resUser = Optional.ofNullable(gameRepository.save(game));
        }
        return resUser;
    }

    public void delete(int gameId) {
        gameRepository.delete(gameId);
    }

    public GameStartResponseDto startGame(GameStartDto gameStartDto) {
        GameEntity game = gameRepository.findOne(gameStartDto.getGameId());
        List<UserEntity> users = userRepository.findAll(Arrays.asList(gameStartDto.getUsers()));
        return principalFrame.queueGame(game, users);
    }

    public boolean redeemTicket(String ticket) {
        return this.principalFrame.redeemTicket(ticket);
    }

    public String[] getPlayerQueue() {
        return this.principalFrame.getPlayerQueue();
    }

    public void abortTicket(String ticketId) {
        this.principalFrame.abortTicket(ticketId);
    }

    public void quitGame() {
        principalFrame.getCurrentGame().quitGame();
    }
}

