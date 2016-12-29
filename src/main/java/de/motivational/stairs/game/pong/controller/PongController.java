package de.motivational.stairs.game.pong.controller;

import de.motivational.stairs.game.general.timestep.GameTimeStepController;
import de.motivational.stairs.game.pong.model.Ball;
import de.motivational.stairs.game.pong.model.Paddle;
import de.motivational.stairs.game.pong.model.PongModel;
import de.motivational.stairs.game.pong.view.PongView;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongController extends GameTimeStepController {
    PongModel pongModel;
    PongView pongView;

    enum BallState{
        IN_CENTER, HIT_LEFT_WALL, HIT_RIGHT_WALL, HIT_LEFT_PADLE, HIT_RIGHT_PADLE, HIT_TOP_WALL, HIT_BOTTOM_WALL;
    }

    public PongController(PongModel pongModel){
        super();
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

        /*

        if(b.getPosX()<=pongModel.getPaddleLeft().getPosX()+pongModel.getPaddleLeft().getWidth()+b.getRadius()
                && (pongModel.getPaddleLeft().getPosY()<=b.getPosY()
                    && b.getPosY()<=pongModel.getPaddleLeft().getPosY()+pongModel.getPaddleLeft().getHeight())){

        } else if(b.getPosX()>=pongModel.getPaddleRight().getPosX()-pongModel.getPaddleRight().getWidth()+b.getRadius()
                && (pongModel.getPaddleRight().getPosY()<=b.getPosY()
                && b.getPosY()<=pongModel.getPaddleRight().getPosY()+pongModel.getPaddleRight().getHeight())){

        } else if (b.getPosX() >= pongModel.getWidth() - b.getRadius()) {

        } else if (b.getPosX() <= b.getRadius()) {

        } else if (b.getPosY() >= pongModel.getHeight() - b.getRadius()) {

        } else if (b.getPosY() <= b.getRadius()) {
            b.setVelocityY(b.getVelocityY()*-1);
        }
        */
        BallState state = this.trackBall(elapsedTime);

        switch (state) {
            case IN_CENTER:
                break;
            case HIT_LEFT_WALL:
                b.setVelocityX(b.getVelocityX()*-1);
                this.pongModel.incrementPointsRight(5);
                this.pongModel.decrementTries();
                break;
            case HIT_RIGHT_WALL:
                b.setVelocityX(b.getVelocityX()*-1);
                this.pongModel.incrementPointsLeft(5);
                this.pongModel.decrementTries();
                break;
            case HIT_LEFT_PADLE:

                b.setVelocityX(b.getVelocityX()*-1);
                this.pongModel.incrementPointsLeft(1);
                break;
            case HIT_RIGHT_PADLE:
                b.setVelocityX(b.getVelocityX()*-1);
                this.pongModel.incrementPointsRight(1);
                break;
            case HIT_TOP_WALL:
            case HIT_BOTTOM_WALL:
                b.setVelocityY(b.getVelocityY()*-1);
                break;
        }
        b.setPosX(b.getPosX()+b.getVelocityX() * elapsedTime);
        b.setPosY(b.getPosY()+b.getVelocityY() * elapsedTime);
    }

    private BallState trackBall(float elapsedTime) {
        Ball b = new Ball(pongModel.getBall());

        b.setPosX(b.getPosX()+b.getVelocityX() * elapsedTime);
        b.setPosY(b.getPosY()+b.getVelocityY() * elapsedTime);

        if(b.getPosX()<=pongModel.getPaddleLeft().getPosX()+pongModel.getPaddleLeft().getWidth()+b.getRadius()
                && (pongModel.getPaddleLeft().getPosY()<=b.getPosY()
                && b.getPosY()<=pongModel.getPaddleLeft().getPosY()+pongModel.getPaddleLeft().getHeight())){
            return BallState.HIT_LEFT_PADLE;
        } else if(b.getPosX()>=pongModel.getPaddleRight().getPosX()-pongModel.getPaddleRight().getWidth()+b.getRadius()
                && (pongModel.getPaddleRight().getPosY()<=b.getPosY()
                && b.getPosY()<=pongModel.getPaddleRight().getPosY()+pongModel.getPaddleRight().getHeight())){
            return BallState.HIT_RIGHT_PADLE;
        } else if (b.getPosX() >= pongModel.getWidth() - b.getRadius()) {
           return BallState.HIT_RIGHT_WALL;
        } else if (b.getPosX() <= b.getRadius()) {
            return BallState.HIT_LEFT_WALL;
        } else if (b.getPosY() >= pongModel.getHeight() - b.getRadius()) {
            return BallState.HIT_BOTTOM_WALL;
        } else if (b.getPosY() <= b.getRadius()) {
            return BallState.HIT_TOP_WALL;
        }

        return BallState.IN_CENTER;
    }

    public void movePaddleUp(Paddle paddle){
        pongModel.setPaddlePosY(paddle, paddle.getPosY()-pongModel.getHeight()/20);
    }

    public void movePaddleDown(Paddle paddle){
        pongModel.setPaddlePosY(paddle, paddle.getPosY()+pongModel.getHeight()/20);
    }
}
