package de.motivational.stairs.beamer;

import com.sun.org.apache.xpath.internal.SourceTree;
import de.motivational.stairs.game.general.BeamerGameFrame;
import de.motivational.stairs.game.general.IBeamerFrame;
import de.motivational.stairs.game.pong.PongGame;
import de.motivational.stairs.game.pong.model.Paddle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

/**
 * Created by Florian on 11.11.2016.
 */
@Component
public class AppPrincipalFrame{
    BeamerGameFrame beamerFrame;
    PongGame pongGame;

    public AppPrincipalFrame() {
        beamerFrame = new BeamerGameFrame(800,600);
        pongGame = new PongGame();
        pongGame.setGameFrame(beamerFrame);
        String PL_MOV_UP = "pl_mov_up";
        String PL_MOV_DOWN = "pl_mov_down";
        String PR_MOV_UP = "pr_mov_up";
        String PR_MOV_DOWN = "pr_mov_down";
        beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), PL_MOV_UP);
        beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), PL_MOV_DOWN);
        beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), PR_MOV_UP);
        beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), PR_MOV_DOWN);
        beamerFrame.getCanvas().getActionMap().put(PL_MOV_UP, new MoveAction(pongGame.getPongController().getPongModel().getPaddleLeft(),1));
        beamerFrame.getCanvas().getActionMap().put(PL_MOV_DOWN, new MoveAction(pongGame.getPongController().getPongModel().getPaddleLeft(),-1));
        beamerFrame.getCanvas().getActionMap().put(PR_MOV_UP, new MoveAction(pongGame.getPongController().getPongModel().getPaddleRight(),1));
        beamerFrame.getCanvas().getActionMap().put(PR_MOV_DOWN, new MoveAction(pongGame.getPongController().getPongModel().getPaddleRight(),-1));

        pongGame.start();
    }

    public String setTestMethod(String strTitle, String strColor){
        beamerFrame.setTitle(strTitle);

        Color cColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(strColor);
            cColor = (Color)field.get(null);
            beamerFrame.getCanvas().setBackground(cColor);
        } catch (Exception e) {
            cColor = null; // Not defined
        }

        return "Title: "+strTitle+" Color:"+strColor;
    }

    class MoveAction extends AbstractAction{
        Paddle paddle;
        int direction;
        MoveAction(Paddle paddle, int direction){
            this.paddle = paddle;
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(direction==1){
                pongGame.getPongController().movePaddleUp(paddle);
            }
            else if(direction==-1){
                pongGame.getPongController().movePaddleDown(paddle);
            }
        }
    }
}