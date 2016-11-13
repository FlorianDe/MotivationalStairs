package de.motivational.stairs.game.pong.view;

import de.motivational.stairs.game.pong.controller.PongController;
import de.motivational.stairs.game.pong.model.Ball;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongView {
    BufferedImage bufferedImage;

    PongController pongController;

    public PongView(PongController pongController){
        this.pongController = pongController;
        this.pongController.setPongView(this);

        bufferedImage = new BufferedImage(800,600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
    public void render(float interpolationTime){
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        Ball ball = pongController.getPongModel().getBall();
        g2d.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());

        //NOT INTERPOLATED
        g2d.setColor(Color.RED);
        g2d.drawOval((int) (ball.getPosX() - ball.getRadius()), (int) (ball.getPosY() -  ball.getRadius()), (int)  ball.getRadius() * 2, (int)  ball.getRadius() * 2);
        //INTERPOLATED!!!
        g2d.setColor(Color.WHITE);
        g2d.drawOval((int) (ball.getPosX() -  ball.getRadius() + interpolationTime * ball.getVelocityX()), (int) (ball.getPosY() -  ball.getRadius() + interpolationTime * ball.getVelocityY()), (int)  ball.getRadius() * 2, (int)  ball.getRadius() * 2);

        g2d.dispose();

        //TEST WHETHER THIS GIVES BETTER RESULTS
        Toolkit.getDefaultToolkit().sync();
    }
}
