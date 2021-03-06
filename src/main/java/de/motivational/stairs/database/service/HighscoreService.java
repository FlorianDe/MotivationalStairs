package de.motivational.stairs.database.service;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.HighscoreEntity;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.rest.dto.HighscoreDto;
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

    @Autowired
    private UserRepository userRepository;

    public Optional<HighscoreDto> findAllByUserIdAndGameId(int userId, int gameId) {
        return highscoreRepository.findOneByUserIdAndGameId(userId, gameId);
    }

    public Collection<HighscoreDto> findAllByGameId(int gameId) {
        return highscoreRepository.findListByGameId(gameId);
    }

    public Collection<HighscoreEntity> findAll() {
        return highscoreRepository.findAll();
    }

    public Optional<HighscoreEntity> findOne(int highscoreId) {
        return Optional.ofNullable(highscoreRepository.findOne(highscoreId));
    }

    public boolean create(HighscoreDto highscoreDto) {
        return create((int)highscoreDto.getHighscore(), highscoreDto.getGameId(), highscoreDto.getUserId());
    }

    public boolean create(int highscore, int gameId) {
        return create(highscore, gameId, -1);
    }

    public boolean create(int highscore, int gameId, int userId){
        GameEntity gameEntity = gameRepository.getOne(gameId);
        if(gameEntity!=null) {
            HighscoreEntity highscoreEntity = new HighscoreEntity();
            highscoreEntity.setHighscore(highscore);
            highscoreEntity.setGameByGameId(gameEntity);
            if(userId > 0){
                UserEntity userEntity = userRepository.getOne(userId);
                if(userEntity!=null) {
                    highscoreEntity.setUserByUserId(userEntity);
                }
            }
            highscoreRepository.save(highscoreEntity);
            return true;
        }
        return false;
    }

    /*
    public boolean updateHighscore(int highscore, int gameId, int userId) {
        HighscoreEntity score = this.highscoreRepository.findOneByUserIdAndGameId(userId, gameId);
        if(score == null) {
            create(highscore, gameId, userId);
            return false;
        } else {
            score.setHighscore(score.getHighscore()+highscore);
            this.highscoreRepository.save(score);
            return true;
        }
    }
    */

    public void delete(int highscoreId) {
        highscoreRepository.delete(highscoreId);
    }
}

