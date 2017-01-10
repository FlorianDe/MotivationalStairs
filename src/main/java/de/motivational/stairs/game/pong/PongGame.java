package de.motivational.stairs.game.pong;

import de.motivational.stairs.game.general.timestep.data.GameTicket;
import de.motivational.stairs.game.general.IBeamerFrame;
import de.motivational.stairs.game.general.timestep.listener.GameEndedListener;
import de.motivational.stairs.game.general.timestep.data.GameResult;
import de.motivational.stairs.game.general.timestep.GameTimeStep;
import de.motivational.stairs.game.pong.controller.PongController;
import de.motivational.stairs.game.pong.model.PongModel;
import de.motivational.stairs.game.pong.view.PongView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian on 13.11.2016.
 */
public class PongGame extends GameTimeStep {
    private PongModel pongModel;
    private PongController pongController;
    private PongView pongView;
    private IBeamerFrame gameFrame;

    public PongController getPongController() {
        return pongController;
    }

    public PongGame(GameEndedListener gameController, GameTicket ticket){
        super(gameController, ticket);

        this.pongModel = new PongModel(800,600);
        this.pongController = new PongController(this.pongModel);
        this.pongView = new PongView(this.pongController);
        this.gameInputListener = this.pongController; //fucked up but should work!
    }

    @Override
    protected void update(float elapsedTime) {
        pongController.update(elapsedTime);
        if(!pongModel.triesLeft()) {
            quitGame();
        }
    }

    @Override
    protected void render() {
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

    @Override
    public void setGameFrame(IBeamerFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @Override
    protected List<GameResult> getResults() {
        ArrayList<GameResult> results = new ArrayList<>();
        results.add(new GameResult(this.ticket.getUsers().get(0), this.pongModel.getPointsLeft()));
        results.add(new GameResult(this.ticket.getUsers().get(1), this.pongModel.getPointsRight()));
        return results;
    }
}
