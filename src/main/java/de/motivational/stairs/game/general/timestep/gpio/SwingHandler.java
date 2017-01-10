package de.motivational.stairs.game.general.timestep.gpio;

import de.motivational.stairs.game.general.timestep.GameTimeStep;
import org.springframework.stereotype.Component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Florian on 09.01.2017.
 */
@Component
public class SwingHandler implements KeyListener {

    GameTimeStep gameTimeStep;
    java.awt.Component component;

    public SwingHandler(){
    }

    public void setInputHandlers(GameTimeStep gameTimeStep, java.awt.Component component){
        if(this.component!=null){
            this.component.removeKeyListener(this);
        }
        this.gameTimeStep = gameTimeStep;
        this.component = component;

        this.component.removeKeyListener(this);
        this.component.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                gameTimeStep.getGameInputListener().buttonL1Pressed();
                break;
            case KeyEvent.VK_S:
                gameTimeStep.getGameInputListener().buttonL2Pressed();
                break;
            case KeyEvent.VK_D:
                gameTimeStep.getGameInputListener().buttonL3Pressed();
                break;

            case KeyEvent.VK_LEFT:
                gameTimeStep.getGameInputListener().buttonR1Pressed();
                break;
            case KeyEvent.VK_UP:
                gameTimeStep.getGameInputListener().buttonR2Pressed();
                break;
            case KeyEvent.VK_RIGHT:
                gameTimeStep.getGameInputListener().buttonR3Pressed();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_A:
                gameTimeStep.getGameInputListener().buttonL1Released();
                break;
            case KeyEvent.VK_S:
                gameTimeStep.getGameInputListener().buttonL2Released();
                break;
            case KeyEvent.VK_D:
                gameTimeStep.getGameInputListener().buttonL3Released();
                break;

            case KeyEvent.VK_LEFT:
                gameTimeStep.getGameInputListener().buttonR1Released();
                break;
            case KeyEvent.VK_UP:
                gameTimeStep.getGameInputListener().buttonR2Released();
                break;
            case KeyEvent.VK_RIGHT:
                gameTimeStep.getGameInputListener().buttonR3Released();
                break;
        }
    }
}
