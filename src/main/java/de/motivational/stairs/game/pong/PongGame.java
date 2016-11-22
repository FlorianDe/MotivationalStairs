package de.motivational.stairs.game.pong;

import de.motivational.stairs.game.general.IBeamerFrame;
import de.motivational.stairs.game.general.timestep.GameTimeStep;
import de.motivational.stairs.game.pong.controller.PongController;
import de.motivational.stairs.game.pong.model.PongModel;
import de.motivational.stairs.game.pong.view.PongView;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongGame extends GameTimeStep{
    private PongModel pongModel;
    private PongController pongController;
    private PongView pongView;
    private IBeamerFrame gameFrame;

    public PongController getPongController() {
        return pongController;
    }

    public PongGame(){
        this.pongModel = new PongModel(800,600);
        this.pongController = new PongController(this.pongModel);
        this.pongView = new PongView(this.pongController);
    }

    @Override
    protected void update(float elapsedTime) {
        pongController.update(elapsedTime);
    }

    @Override
    protected void internalUpdateGraphicsInterpolated() {
        pongView.render(localTime);
        //TODO @VIKTOR NON AFFINE IMG TRANSFORM
        //TODO SHOW
        if(gameFrame!=null) {
            gameFrame.draw(pongView.getBufferedImage());
        }
    }

    @Override
    public void start() {
        super.start(1 / 60f, 5);
    }


    public IBeamerFrame getGameFrame() {
        return gameFrame;
    }
    public void setGameFrame(IBeamerFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
    public static void main(String[] args){
        new PongGame().start();
    }

}
