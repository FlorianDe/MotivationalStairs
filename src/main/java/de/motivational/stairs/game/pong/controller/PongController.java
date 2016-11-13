package de.motivational.stairs.game.pong.controller;

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
        pongModel.getBall().setPosX(pongModel.getBall().getPosX()+pongModel.getBall().getVelocityX() * elapsedTime);
        pongModel.getBall().setPosY(pongModel.getBall().getPosY()+pongModel.getBall().getVelocityY() * elapsedTime);
        if (pongModel.getBall().getPosX() > pongModel.getWidth() - pongModel.getBall().getRadius()) {
            pongModel.getBall().setVelocityX(pongModel.getBall().getVelocityX()*-1);
        }
        if (pongModel.getBall().getPosX() < pongModel.getBall().getRadius()) {
            pongModel.getBall().setVelocityX(pongModel.getBall().getVelocityX()*-1);
        }

        if (pongModel.getBall().getPosY() > pongModel.getHeight() - pongModel.getBall().getRadius()) {
            pongModel.getBall().setVelocityY(pongModel.getBall().getVelocityY()*-1);
        }
        if (pongModel.getBall().getPosY() < pongModel.getBall().getRadius()) {
            pongModel.getBall().setVelocityY(pongModel.getBall().getVelocityY()*-1);
        }
    }
}
