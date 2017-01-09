package de.motivational.stairs.game.general.timestep.listener;


import de.motivational.stairs.database.entity.GameEntity;

import java.util.List;

/**
 * Created by Florian on 22.11.2016.
 */
public interface GameInputListener {
    void buttonL1Pressed();
    void buttonL1Released();

    void buttonL2Pressed();
    void buttonL2Released();

    void buttonL3Pressed();
    void buttonL3Released();



    void buttonR1Pressed();
    void buttonR1Released();

    void buttonR2Pressed();
    void buttonR2Released();

    void buttonR3Pressed();
    void buttonR3Released();
}
