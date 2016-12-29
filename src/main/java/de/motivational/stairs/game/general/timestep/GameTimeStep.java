package de.motivational.stairs.game.general.timestep;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.game.general.BeamerGameFrame;
import de.motivational.stairs.game.general.GameTicket;
import de.motivational.stairs.game.general.IBeamerFrame;
import de.motivational.stairs.game.general.IBeamerGame;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by Florian on 13.11.2016.
 */
public abstract class GameTimeStep implements IBeamerGame {
    private final GameEndedEventListener gameController;
    protected final GameTicket ticket;
    private boolean isRunning = false;
    private Logger logger;

    public GameTimeStep(GameEndedEventListener gameController, GameTicket ticket) {
        this.logger = Logger.getLogger(this.getClass());
        this.gameController = gameController;
        this.ticket = ticket;
    }

    protected void quitGame() {
        this.logger.info(String.format("Game with ticket %s ended", ticket.getTicket()));
        this.isRunning = false;
        this.gameController.gameEnded(this.ticket.getGame(), this.getResults());
    }

    /* difference between time of update and world step time */
    protected float localTime = 0f;

    /**
     * Starts the game loop in a new Thread.
     * @param fixedTimeStep
     * @param maxSubSteps maximum steps that should be processed to catch up with real time.
     */
    protected final void start(final float fixedTimeStep, final int maxSubSteps) {
        this.isRunning = true;
        //init();
        new Thread() {
            {
                setDaemon(true);
            }
            @Override
            public void run() {
                long start = System.nanoTime();

                while (isRunning) {
                    long now = System.nanoTime();
                    float elapsed = (now - start) / 1000000000f;
                    start = now;
                    internalUpdateWithFixedTimeStep(elapsed, maxSubSteps, fixedTimeStep);
                    render();
                    if (1000000000 * fixedTimeStep - (System.nanoTime() - start) > 1000000) {
                        try {
                            Thread.sleep(0, 999999);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * Updates game state if possible and sets localTime for interpolation.
     * @param elapsedSeconds
     * @param maxSubSteps
     * @param fixedTimeStep
     */
    protected void internalUpdateWithFixedTimeStep(float elapsedSeconds, int maxSubSteps, float fixedTimeStep) {
        int numSubSteps = 0;
        if (maxSubSteps != 0) {
            // fixed timestep with interpolation
            localTime += elapsedSeconds;
            if (localTime >= fixedTimeStep) {
                numSubSteps = (int) (localTime / fixedTimeStep);
                localTime -= numSubSteps * fixedTimeStep;
            }
        }
        if (numSubSteps != 0) {
            // clamp the number of substeps, to prevent simulation grinding spiralling down to a halt
            int clampedSubSteps = (numSubSteps > maxSubSteps) ? maxSubSteps : numSubSteps;
            for (int i = 0; i < clampedSubSteps; i++) {
                update(fixedTimeStep);
            }
        }
    }

    public abstract void start();


    protected abstract void render();
    protected abstract void update(float elapsedTime);


    public abstract void setGameFrame(IBeamerFrame beamerFrame);
    public boolean isRunning() {
        return this.isRunning;
    }
    protected abstract List<GameResult> getResults();

}

