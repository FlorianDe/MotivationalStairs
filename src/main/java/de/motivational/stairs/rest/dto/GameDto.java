package de.motivational.stairs.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.UserEntity;

/**
 * Created by fdecker on 28.12.16.
 */
public class GameDto {
    @JsonProperty("gameId")
    private int gameId;

    @JsonProperty("name")
    private String name;

    public GameDto(){}

    public GameDto(GameEntity gameEntity) {
        this.gameId = gameEntity.getGameId();
        this.name = gameEntity.getName();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}