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
        this.width = width;
        this.height = height;

        this.ball = new Ball();
        this.ball.setPosX(width/2);
        this.ball.setPosY(height/2);
        this.ball.setRadius(10);
        this.ball.setVelocityY(-100);
        this.ball.setVelocityX(150);

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
}
