package de.motivational.stairs.game.pong.model;

import de.motivational.stairs.config.PongConfig;

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

    PongConfig pongConfig;

    public PongModel(float width, float height, PongConfig pongConfig){
        this.pongConfig = pongConfig;
        random = new Random();
        this.width = width;
        this.height = height;

        this.pointsLeft = 0;
        this.pointsRight = 0;
        this.tries = pongConfig.pongTries;

        this.ball = new Ball();
        this.ball.setRadius(pongConfig.pongBallRadius);
        this.centerBall();
        this.paddleLeft = new Paddle();
        this.paddleLeft.setHeight(pongConfig.pongPaddleHeight);
        this.paddleLeft.setWidth(pongConfig.pongPaddleWidth);
        this.paddleLeft.setPosY((height-this.paddleLeft.getHeight())/2);

        this.paddleRight = new Paddle();
        this.paddleRight.setHeight(pongConfig.pongPaddleHeight);
        this.paddleRight.setWidth(pongConfig.pongPaddleWidth);
        this.paddleRight.setPosY((height-this.paddleRight.getHeight())/2);
        this.paddleRight.setPosX(width-this.paddleRight.getWidth());
    }

    public void centerBall() {
        this.ball.setPosX(width/2);
        this.ball.setPosY(height/2);

        double velX = random.nextDouble();
        double angle = Math.signum(Math.random()-0.5) * random.nextDouble() * pongConfig.pongBallMaxAngle;
        double velY = Math.toRadians(angle)*velX;
        double eFak = Math.sqrt(Math.pow(velX,2)+Math.pow(velY,2));

        this.ball.setVelocityY((float)(pongConfig.pongBallVelocity/eFak*velY));
        this.ball.setVelocityX((float)(pongConfig.pongBallVelocity/eFak*velX));
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
