package de.motivational.stairs.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by viktorspadi on 28.12.16.
 */
public class GameStartDto {
    @JsonProperty("gameId")
    private int gameId;

    @JsonProperty("users")
    private Integer[] users;

    public GameStartDto() {

    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Integer[] getUsers() {
        return users;
    }

    public void setUsers(Integer[] users) {
        this.users = users;
    }
}
