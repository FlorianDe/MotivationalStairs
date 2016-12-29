package de.motivational.stairs.game.pong.model;

import java.util.Random;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongModel {

    private Ball ball;
    private Paddle paddleLeft;
    private Paddle paddleRight;
    private float width;
    private float height;
    private int pointsLeft;
    private int pointsRight;
    private int tries;
    Random random;

    public PongModel(int width, int height){
        random = new Random();
        this.width = width;
        this.height = height;

        this.pointsLeft = 0;
        this.pointsRight = 0;
        this.tries = 10;

        this.ball = new Ball();
        this.ball.setRadius(10);
        this.centerBall();
        this.paddleLeft = new Paddle();
        this.paddleLeft.setHeight(100);
        this.paddleLeft.setWidth(20);
        this.paddleLeft.setPosY((height-this.paddleLeft.getHeight())/2);

        this.paddleRight = new Paddle();
        this.paddleRight.setHeight(100);
        this.paddleRight.setWidth(20);
        this.paddleRight.setPosY((height-this.paddleRight.getHeight())/2);
        this.paddleRight.setPosX(width-this.paddleRight.getWidth());
    }

    public void centerBall() {
        this.ball.setPosX(width/2);
        this.ball.setPosY(height/2);

        this.ball.setVelocityY((float) ( randomVelocity(150, 300) * Math.signum(Math.random()-0.5)));
        this.ball.setVelocityX((float) ( randomVelocity(150, 300) * Math.signum(Math.random()-0.5)));
    }

    public int randomVelocity(int min, int max) {
        return this.random.nextInt((max - min) + 1) + min;
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

    public void setPaddlePosY(Paddle paddle, float posY){
        if(posY < 0) {
            paddle.setPosY(0);
        } else if(posY > (this.height-paddle.height)) {
            paddle.setPosY(this.height-paddle.height);
        } else {
            paddle.setPosY(posY);
        }
    }

    public int getPointsLeft() {
        return pointsLeft;
    }

    public void setPointsLeft(int pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    public int getPointsRight() {
        return pointsRight;
    }

    public void setPointsRight(int pointsRight) {
        this.pointsRight = pointsRight;
    }

    public void incrementPointsLeft(int count) {
        this.pointsLeft += count;
    }

    public void incrementPointsRight(int count) {
        this.pointsRight += count;
    }

    public void decrementTries() {
        this.centerBall();
        this.tries--;
    }

    public boolean triesLeft() {
        return this.tries > 0;
    }
}
