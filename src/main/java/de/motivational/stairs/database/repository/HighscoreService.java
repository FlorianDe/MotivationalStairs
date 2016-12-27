package de.motivational.stairs.database.repository;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.HighscoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Florian on 29.08.2016.
 */
@Service
public class HighscoreService {
    @Autowired
    private HighscoreRepository highscoreRepository;

    @Autowired
    private GameRepository gameRepository;


    public Collection<HighscoreEntity> findAll() {
        return highscoreRepository.findAll();
    }

    public Optional<HighscoreEntity> findOne(int highscoreId) {
        return Optional.ofNullable(highscoreRepository.findOne(highscoreId));
    }

    public boolean create(int highscore, int gameId){
        GameEntity gameEntity = gameRepository.getOne(gameId);
        if(gameEntity!=null) {
            HighscoreEntity highscoreEntity = new HighscoreEntity();
            highscoreEntity.setHighscore(highscore);
            highscoreEntity.setGameByGameId(gameEntity);

            highscoreRepository.save(highscoreEntity);
            return true;
        }
        return false;
    }
}

