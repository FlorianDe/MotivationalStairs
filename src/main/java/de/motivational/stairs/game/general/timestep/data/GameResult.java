package de.motivational.stairs.game.general.timestep.data;

import de.motivational.stairs.database.entity.UserEntity;

/**
 * Created by viktorspadi on 29.12.16.
 */
public class GameResult {
    private final UserEntity user;
    private final int score;

    public GameResult(UserEntity user, int score) {
        this.user = user;
        this.score = score;
    }

    public UserEntity getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }
}
