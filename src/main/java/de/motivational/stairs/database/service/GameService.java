package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.rest.dto.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

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
}

