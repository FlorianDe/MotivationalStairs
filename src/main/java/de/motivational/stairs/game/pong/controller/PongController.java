package de.motivational.stairs.game.pong.controller;

import de.motivational.stairs.game.pong.model.Ball;
import de.motivational.stairs.game.pong.model.Paddle;
import de.motivational.stairs.game.pong.model.PongModel;
import de.motivational.stairs.game.pong.view.PongView;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongController {
    PongModel pongModel;
    PongView pongView;

    public PongController(PongModel pongModel){
        this.pongModel = pongModel;
    }

    public PongModel getPongModel() {
        return pongModel;
    }

    public void setPongModel(PongModel pongModel) {
        this.pongModel = pongModel;
    }

    public PongView getPongView() {
        return pongView;
    }

    public void setPongView(PongView pongView) {
        this.pongView = pongView;
    }

    public void update(float elapsedTime) {
        //TODO BACKTRACK LOGIC!!
        Ball b = pongModel.getBall();

        b.setPosX(b.getPosX()+b.getVelocityX() * elapsedTime);
        b.setPosY(b.getPosY()+b.getVelocityY() * elapsedTime);
        if(b.getPosX()<=pongModel.getPaddleLeft().getPosX()+pongModel.getPaddleLeft().getWidth()+b.getRadius()
                && (pongModel.getPaddleLeft().getPosY()<=b.getPosY()
                    && b.getPosY()<=pongModel.getPaddleLeft().getPosY()+pongModel.getPaddleLeft().getHeight())){
            b.setVelocityX(b.getVelocityX()*-1);
        } else if(b.getPosX()>=pongModel.getPaddleRight().getPosX()-pongModel.getPaddleRight().getWidth()+b.getRadius()
                && (pongModel.getPaddleRight().getPosY()<=b.getPosY()
                && b.getPosY()<=pongModel.getPaddleRight().getPosY()+pongModel.getPaddleRight().getHeight())){
            b.setVelocityX(b.getVelocityX()*-1);
        } else if (b.getPosX() >= pongModel.getWidth() - b.getRadius()) {
            b.setVelocityX(b.getVelocityX()*-1);
        } else if (b.getPosX() <= b.getRadius()) {
            b.setVelocityX(b.getVelocityX()*-1);
        } else if (b.getPosY() >= pongModel.getHeight() - b.getRadius()) {
            b.setVelocityY(b.getVelocityY()*-1);
        } else if (b.getPosY() <= b.getRadius()) {
            b.setVelocityY(b.getVelocityY()*-1);
        }
    }

    public void movePaddleUp(Paddle paddle){
        pongModel.setPaddlePosY(paddle, paddle.getPosY()-pongModel.getHeight()/50);
    }

    public void movePaddleDown(Paddle paddle){
        pongModel.setPaddlePosY(paddle, paddle.getPosY()+pongModel.getHeight()/50);
    }
}
