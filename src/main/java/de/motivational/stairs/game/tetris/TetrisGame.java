package de.motivational.stairs.game.tetris;

import de.motivational.stairs.game.general.GameTicket;
import de.motivational.stairs.game.general.IBeamerFrame;
import de.motivational.stairs.game.general.timestep.listener.GameEndedListener;
import de.motivational.stairs.game.general.timestep.GameResult;
import de.motivational.stairs.game.general.timestep.GameTimeStep;

import java.util.List;

/**
 * Created by Florian on 12.11.2016.
 */
public class TetrisGame  extends GameTimeStep {


    public TetrisGame(GameEndedListener gameController, GameTicket ticket) {
        super(gameController, ticket);
    }

    @Override
    public void start() {

    }

    @Override
    protected void render() {

    }

    @Override
    protected void update(float elapsedTime) {

    }

    @Override
    public void setGameFrame(IBeamerFrame beamerFrame) {

    }

    @Override
    protected List<GameResult> getResults() {
        return null;
    }
}
