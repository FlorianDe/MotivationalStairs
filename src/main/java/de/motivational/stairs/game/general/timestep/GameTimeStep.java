package de.motivational.stairs.game.general.timestep;

import de.motivational.stairs.game.general.IBeamerGame;

/**
 * Created by Florian on 13.11.2016.
 */
public abstract class GameTimeStep implements IBeamerGame {
    /* difference between time of update and world step time */
    protected float localTime = 0f;

    /**
     * Starts the game loop in a new Thread.
     * @param fixedTimeStep
     * @param maxSubSteps maximum steps that should be processed to catch up with real time.
     */
    protected final void start(final float fixedTimeStep, final int maxSubSteps) {
        //init();
        new Thread() {
            {
                setDaemon(true);
            }
            @Override
            public void run() {
                long start = System.nanoTime();
                while (true) {
                    long now = System.nanoTime();
                    float elapsed = (now - start) / 1000000000f;
                    start = now;
                    internalUpdateWithFixedTimeStep(elapsed, maxSubSteps, fixedTimeStep);
                    internalUpdateGraphicsInterpolated();
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
    protected abstract void  internalUpdateGraphicsInterpolated();
    protected abstract void update(float elapsedTime);
}
