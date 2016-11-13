package de.motivational.stairs.game.pong.model;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongModel {
    private Ball ball;
    private Paddle paddleLeft;
    private Paddle paddleRight;
    private float width;
    private float height;

    public PongModel(int width, int height){
        this.ball = new Ball();
        this.ball.setPosX(width/2);
        this.ball.setPosY(height/2);
        this.ball.setRadius(10);
        this.ball.setVelocityY(-200);
        this.ball.setVelocityX(100);

        this.paddleLeft = new Paddle();
        this.paddleRight = new Paddle();
        this.width = width;
        this.height = height;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Paddle getPaddleLeft() {
        return paddleLeft;
    }

    public void setPaddleLeft(Paddle paddleLeft) {
        this.paddleLeft = paddleLeft;
    }

    public Paddle getPaddleRight() {
        return paddleRight;
    }

    public void setPaddleRight(Paddle paddleRight) {
        this.paddleRight = paddleRight;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
