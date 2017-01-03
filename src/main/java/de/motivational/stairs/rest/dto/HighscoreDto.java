package de.motivational.stairs.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.HighscoreEntity;
import java.sql.Timestamp;

/**
 * Created by fdecker on 28.12.16.
 */
public class HighscoreDto {
    @JsonProperty("highscoreId")
    private int highscoreId;

    @JsonProperty("highscore")
    private long highscore;

    @JsonProperty("created")
    private Timestamp created;

    @JsonProperty("gameId")
    private int gameId;

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("userName")
    private String userName;

    public HighscoreDto(){}

    public HighscoreDto(Long highscore, Integer gameId, Integer userId) {
        if(highscore!=null) {
            this.highscore = highscore;
        }
        if(gameId!=null) {
            this.gameId = gameId;
        }
        if(userId!=null) {
            this.userId = userId;
        }
    }

    public HighscoreDto(Long highscore, Integer gameId, String userName) {
        if(highscore!=null) {
            this.highscore = highscore;
        }
        if(gameId!=null) {
            this.gameId = gameId;
        }
        if(userName!=null) {
            this.userName = userName;
        }
    }

    public HighscoreDto(HighscoreEntity highscoreEntity) {
        this.highscoreId = highscoreEntity.getHighscoreId();
        this.highscore = highscoreEntity.getHighscore();
        this.created = highscoreEntity.getCreated();
        this.gameId = highscoreEntity.getGameByGameId().getGameId();
        this.userId = highscoreEntity.getUserByUserId().getUserId();
    }

    public int getHighscoreId() {
        return highscoreId;
    }

    public void setHighscoreId(int highscoreId) {
        this.highscoreId = highscoreId;
    }

    public long getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
