package de.motivational.stairs.beamer;

import de.motivational.stairs.game.general.BeamerGameFrame;
import de.motivational.stairs.game.general.IBeamerFrame;
import de.motivational.stairs.game.pong.PongGame;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

/**
 * Created by Florian on 11.11.2016.
 */
@Component
public class AppPrincipalFrame{
    BeamerGameFrame beamerFrame;

    public AppPrincipalFrame() {
        beamerFrame = new BeamerGameFrame(800,600);
        PongGame pongGame = new PongGame();
        pongGame.setGameFrame(beamerFrame);
        pongGame.start();
    }

    public String setTestMethod(String strTitle, String strColor){
        beamerFrame.setTitle(strTitle);

        Color cColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(strColor);
            cColor = (Color)field.get(null);
            beamerFrame.getContentPane().setBackground(cColor);
        } catch (Exception e) {
            cColor = null; // Not defined
        }

        return "Title: "+strTitle+" Color:"+strColor;
    }
}