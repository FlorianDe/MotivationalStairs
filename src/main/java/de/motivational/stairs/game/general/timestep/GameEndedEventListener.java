package de.motivational.stairs.game.general.timestep;

import de.motivational.stairs.database.entity.GameEntity;

import java.util.List;

/**
 * Created by viktorspadi on 29.12.16.
 */
public interface GameEndedEventListener {
    void gameEnded(GameEntity game, List<GameResult> results);
}
