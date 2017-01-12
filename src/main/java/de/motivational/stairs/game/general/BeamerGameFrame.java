package de.motivational.stairs.game.general;

import de.motivational.stairs.game.general.IBeamerFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Florian on 13.11.2016.
 */
public class BeamerGameFrame extends JFrame implements IBeamerFrame {
    JPanel canvas;

    public BeamerGameFrame(){
        this((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
        );
    }

    public BeamerGameFrame(int width, int height){
        this.setSize(width, height);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setPreferredSize(new Dimension(width, height));
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        this.setBackground(Color.black);
        this.canvas = new JPanel();
        this.canvas.setBackground(Color.black);
        //this.canvas.setFocusable(true);
        this.add(this.canvas);

        this.setVisible(true);
    }

    public void draw(BufferedImage bufferedImage){
        Graphics2D g2d = (Graphics2D) this.canvas.getGraphics();
        g2d.drawImage(bufferedImage,(int)(this.getSize().getWidth()-bufferedImage.getWidth())/2,(int)(this.getSize().getHeight()-bufferedImage.getHeight())/2,bufferedImage.getWidth(),bufferedImage.getHeight(), null);
        g2d.dispose();
    }

    public JPanel getCanvas() {
        return canvas;
    }
}
