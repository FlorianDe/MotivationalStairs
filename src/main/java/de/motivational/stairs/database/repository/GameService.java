package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.BeamerEntity;
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.StairsEntity;
import de.motivational.stairs.rest.dto.setup.BeamerSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

