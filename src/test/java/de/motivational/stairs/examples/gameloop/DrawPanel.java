package de.motivational.stairs.examples.gameloop;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Florian on 13.11.2016.
 */
public class DrawPanel extends JPanel {

    PongGameLoop pongGame;

    public BufferedImage bufferedImage;

    public float getInterpolationTime() {
        return interpolationTime;
    }

    public void setInterpolationTime(float interpolationTime) {
        this.interpolationTime = interpolationTime;
    }

    float interpolationTime = 1.0f;

    public DrawPanel(PongGameLoop pongGame) {
        this.pongGame = pongGame;

        bufferedImage = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        Graphics2D g2dCanv = (Graphics2D) g.create();

        for (PongGameLoop.Ball ball : pongGame.balls) {
            //g2d.setColor(Color.RED);
            // g2d.drawOval((int) (ball.x - ball.r), (int) (ball.y - ball.r), (int) ball.r * 2, (int) ball.r * 2);

            //INTERPOLATED!!!
            g2d.setColor(Color.WHITE);
            g2d.drawOval((int) (ball.x - ball.r + interpolationTime * ball.vX), (int) (ball.y - ball.r + interpolationTime * ball.vY), (int) ball.r * 2, (int) ball.r * 2);
        }

        g2d.dispose();

        g2dCanv.drawImage(bufferedImage,0,0,bufferedImage.getWidth(),bufferedImage.getHeight(), null);
        g2dCanv.dispose();

        Toolkit.getDefaultToolkit().sync();
    }

}
