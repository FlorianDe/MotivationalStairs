package de.motivational.stairs.game.general.timestep.listener;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.game.general.timestep.data.GameResult;

import java.util.List;

/**
 * Created by viktorspadi on 29.12.16.
 */
public interface GameEndedListener {
    void gameEnded(GameEntity game, List<GameResult> results);
}
