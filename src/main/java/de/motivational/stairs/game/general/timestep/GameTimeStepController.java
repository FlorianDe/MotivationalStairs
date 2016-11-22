package de.motivational.stairs.game.general.timestep;

import de.motivational.stairs.game.general.timestep.action.GameOverAction;
import de.motivational.stairs.game.general.timestep.action.GameStartedAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian on 22.11.2016.
 */
public abstract class GameTimeStepController {
    List<GameOverAction> gameOverListener;
    List<GameStartedAction> gameStartedListener;


    protected GameTimeStepController(){
        gameOverListener = new ArrayList<>();
    }

    public void gameOver(Player winner){

    }

}
