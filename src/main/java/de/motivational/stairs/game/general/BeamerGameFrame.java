package de.motivational.stairs.game.general;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Florian on 13.11.2016.
 */
public class BeamerGameFrame extends JFrame implements IBeamerFrame{
    JPanel canvas;

    public BeamerGameFrame(int width, int height){
        this.setSize(width, height);
        this.setPreferredSize(new Dimension(width, height));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.canvas = new JPanel();
        this.add(this.canvas);

        this.setVisible(true);
    }

    public void draw(BufferedImage bufferedImage){
        Graphics2D g2d = (Graphics2D) this.canvas.getGraphics();
        g2d.drawImage(bufferedImage,0,0,bufferedImage.getWidth(),bufferedImage.getHeight(), null);
        g2d.dispose();
    }

    public JPanel getCanvas() {
        return canvas;
    }
}
