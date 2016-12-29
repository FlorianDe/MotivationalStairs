package de.motivational.stairs.game.pong.model;

/**
 * Created by Florian on 13.11.2016.
 */
public class Ball {
    float posX;
    float posY;
    float velocityX;
    float velocityY;
    float radius;

    public Ball() {

    }

    public Ball(Ball ball) {
        this.posX = ball.posX;
        this.posY = ball.posY;
        this.velocityX = ball.velocityX;
        this.velocityY = ball.velocityY;
        this.radius = ball.radius;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
