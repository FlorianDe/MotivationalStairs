package de.motivational.stairs.game.pong.controller;

import de.motivational.stairs.game.general.timestep.listener.GameInputListener;
import de.motivational.stairs.game.pong.model.Ball;
import de.motivational.stairs.game.pong.model.Paddle;
import de.motivational.stairs.game.pong.model.PongModel;
import de.motivational.stairs.game.pong.view.PongView;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongController implements GameInputListener {
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
        Ball b = pongModel.getBall();
        BallState state = this.trackBall(elapsedTime);
        switch (state) {
            case IN_CENTER:
                break;
            case HIT_LEFT_WALL:
                this.pongModel.incrementPointsRight(5);
                this.pongModel.decrementTries();
                break;
            case HIT_RIGHT_WALL:
                this.pongModel.incrementPointsLeft(5);
                this.pongModel.decrementTries();
                break;
            case HIT_LEFT_PADLE:
                this.updateBallVelocityByHit(this.pongModel.getPaddleLeft(), BallState.HIT_LEFT_PADLE);
                this.pongModel.incrementPointsLeft(1);
                break;
            case HIT_RIGHT_PADLE:
                this.updateBallVelocityByHit(this.pongModel.getPaddleRight(), BallState.HIT_RIGHT_PADLE);
                this.pongModel.incrementPointsRight(1);
                break;
            case HIT_TOP_WALL:
            case HIT_BOTTOM_WALL:
                b.setVelocityY(b.getVelocityY()*-1);
                break;
        }

        this.updatePaddlePosition(this.pongModel.getPaddleLeft());
        this.updatePaddlePosition(this.pongModel.getPaddleRight());

        b.setPosX(b.getPosX()+b.getVelocityX() * elapsedTime);
        b.setPosY(b.getPosY()+b.getVelocityY() * elapsedTime);
    }

    private void updateBallVelocityByHit(Paddle paddle, BallState ballstate){
        double MAX_ANGLE = 20.0;

        float hitYPos = ((pongModel.getBall().getPosY()-paddle.getPosY())-paddle.getHeight());
        double hitYAngle = (hitYPos / (paddle.getHeight() / 2)) * MAX_ANGLE;
        if(ballstate.equals(BallState.HIT_RIGHT_PADLE)){
            hitYAngle*=-1;
        }
        pongModel.getBall().setVelocityX(pongModel.getBall().getVelocityX()*-1);

        this.rotateBallVelocity(hitYAngle);

        System.out.println(pongModel.getBall());
    }

    private void rotateBallVelocity(double theta){
        theta = Math.toRadians(theta);
        double rx = Math.cos(theta)*pongModel.getBall().getVelocityX()-Math.sin(theta)*pongModel.getBall().getVelocityY();
        double ry = Math.sin(theta)*pongModel.getBall().getVelocityX()+Math.cos(theta)*pongModel.getBall().getVelocityY();
        pongModel.getBall().setVelocityX((float)rx);
        pongModel.getBall().setVelocityY((float)ry);
    }

    private void updatePaddlePosition(Paddle paddle){
        float newYPos = paddle.getPosY()+paddle.getVelocity();
        if(this.getPongModel().getHeight()<(newYPos+paddle.getHeight())){
            newYPos = this.getPongModel().getHeight()-paddle.getHeight();
        } else if(newYPos<0){
            newYPos = 0;
        }
        paddle.setPosY(newYPos);
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

    @Override
    public void buttonL1Pressed() {
        pongModel.getPaddleLeft().setVelocity(10);
    }

    @Override
    public void buttonL1Released() {
        pongModel.getPaddleLeft().setVelocity(0);
    }

    @Override
    public void buttonL2Pressed() {
        //ignore for this game
    }

    @Override
    public void buttonL2Released() {
        //ignore for this game
    }

    @Override
    public void buttonL3Pressed() {
        pongModel.getPaddleLeft().setVelocity(-10);
    }

    @Override
    public void buttonL3Released() {
        pongModel.getPaddleLeft().setVelocity(0);
    }

    @Override
    public void buttonR1Pressed() {
        pongModel.getPaddleRight().setVelocity(10);
    }

    @Override
    public void buttonR1Released() {
        pongModel.getPaddleRight().setVelocity(0);
    }

    @Override
    public void buttonR2Pressed() {
        //ignore for this game
    }

    @Override
    public void buttonR2Released() {
        //ignore for this game
    }

    @Override
    public void buttonR3Pressed() {
        pongModel.getPaddleRight().setVelocity(-10);
    }

    @Override
    public void buttonR3Released() {
        pongModel.getPaddleRight().setVelocity(0);
    }
}
