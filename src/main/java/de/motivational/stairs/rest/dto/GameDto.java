package de.motivational.stairs.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.UserEntity;

/**
 * Created by fdecker on 28.12.16.
 */
public class GameDto {
    @JsonProperty("id")
    private int gameId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("gameModes")
    private int gameModes;

    @JsonProperty("imagePath")
    private String imagePath;

    public GameDto(){}

    public GameDto(GameEntity gameEntity) {
        this.gameId = gameEntity.getGameId();
        this.name = gameEntity.getName();
        this.subtitle = gameEntity.getSubtitle();
        this.description = gameEntity.getDescription();
        this.gameModes = gameEntity.getGameModes();
        this.imagePath = gameEntity.getImagePath();
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
