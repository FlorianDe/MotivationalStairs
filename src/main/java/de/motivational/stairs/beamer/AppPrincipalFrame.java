package de.motivational.stairs.beamer;

import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.HighscoreEntity;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.database.service.HighscoreService;
import de.motivational.stairs.game.general.BeamerGameFrame;
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.service.BeamerSetupService;
import de.motivational.stairs.beamer.opengl.StairsTransformFrame;
import de.motivational.stairs.game.general.GameTicket;
import de.motivational.stairs.game.general.timestep.GameEndedEventListener;
import de.motivational.stairs.game.general.timestep.GameResult;
import de.motivational.stairs.game.general.timestep.GameTimeStep;
import de.motivational.stairs.game.pong.PongGame;
import de.motivational.stairs.game.pong.model.Paddle;
import de.motivational.stairs.rest.dto.GameStartResponseDto;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

/**
 * Created by Florian on 11.11.2016.
 */
@Component
public class AppPrincipalFrame implements GameEndedEventListener {
    BeamerGameFrame beamerFrame;

    GameTimeStep currentGame;
    StairsTransformFrame transformFrame;
    Queue<GameTicket> gameTickets;
    Thread dispatcher;

    @Autowired
    BeamerSetupService beamerSetupService;

    @Autowired
    HighscoreService highscoreService;

    Logger logger;

    public AppPrincipalFrame() {
        this.gameTickets = new LinkedList<GameTicket>();

        this.logger = Logger.getLogger(AppPrincipalFrame.class);
        this.init();
    }

    public void startFrame() {
        initGL();
        init();
    }

    private void initGL() {
        this.transformFrame = new StairsTransformFrame();
        Optional<BeamerSetupEntity> setup = beamerSetupService.findOne(3);
        if(setup.isPresent()) {
            this.transformFrame.setBeamerSetup(setup.get());
        }
    }

    private void init(){
        beamerFrame = new BeamerGameFrame(800,600);

        /*

        currentGame = new PongGame();
        currentGame.setGameFrame(this.beamerFrame);


        */
        //currentGame.start();
    }

    public GameStartResponseDto queueGame(GameEntity game, List<UserEntity> users) {
        GameTicket ticket = new GameTicket(game, users);

        GameStartResponseDto response = new GameStartResponseDto();
        response.setUsersBefore(this.gameTickets.stream().map(t -> {
            StringBuilder name = new StringBuilder();
            for(int i = 0; i < t.getUsers().size(); i++) {
                name.append(t.getUsers().get(i).getName());
                if(i < t.getUsers().size() - 1) {
                    name.append(" und ");
                }
            }
            return name.toString();
        }).toArray(String[]::new));
        response.setTicket(ticket.getTicket());

        this.gameTickets.add(ticket);

        this.dispatchGameQueue();


        this.logger.info(String.format("Creating ticket #%s for game '%s'", ticket.getTicket(), game.getName()));
        return response;
    }

    private void dispatchGameQueue() {
        this.logger.info("Starting redeem timer");
        if(this.dispatcher != null)
            System.out.println(this.dispatcher.isAlive());

        if(this.dispatcher == null || !this.dispatcher.isAlive()) {
            this.dispatcher = createDispatcher();
            this.dispatcher.start();
        }
    }

    private Thread createDispatcher() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(gameTickets.size() > 0) {
                        Thread.sleep(8000);
                        if(currentGame == null || !currentGame.isRunning()) {
                            logger.info(String.format("Ticket %s was not redeemed, queueing next ticket", gameTickets.peek().getTicket()));
                            gameTickets.poll();
                        } else {
                            logger.info(String.format("Game is Running, stopping redeem timer"));
                        }
                    }
                    logger.info(String.format("No more tickets available, waiting for enqueueing"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return t;
    }

    public void redeemTicket(String ticket) {
        if(this.gameTickets.size() > 0 &&
                (this.currentGame == null || !this.currentGame.isRunning()) &&
                this.gameTickets.peek().getTicket().equals(ticket)) {
            this.logger.info(String.format("Starting game with ticket %s", ticket));
            this.startNextGame();
        }
    }

    private void startNextGame() {
        GameTicket ticket = this.gameTickets.poll();
        switch(ticket.getGame().getGameId()) {
            case 1:
                this.currentGame = new PongGame(this, ticket);
                this.currentGame.setGameFrame(this.beamerFrame);
                // TODO BIND CONTROLS
                String PL_MOV_UP = "pl_mov_up";
                String PL_MOV_DOWN = "pl_mov_down";
                String PR_MOV_UP = "pr_mov_up";
                String PR_MOV_DOWN = "pr_mov_down";


                beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), PL_MOV_UP);
                beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), PL_MOV_DOWN);
                beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), PR_MOV_UP);
                beamerFrame.getCanvas().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), PR_MOV_DOWN);
                beamerFrame.getCanvas().getActionMap().put(PL_MOV_UP, new MoveAction(((PongGame)currentGame).getPongController().getPongModel().getPaddleLeft(),1));
                beamerFrame.getCanvas().getActionMap().put(PL_MOV_DOWN, new MoveAction(((PongGame)currentGame).getPongController().getPongModel().getPaddleLeft(),-1));
                beamerFrame.getCanvas().getActionMap().put(PR_MOV_UP, new MoveAction(((PongGame)currentGame).getPongController().getPongModel().getPaddleRight(),1));
                beamerFrame.getCanvas().getActionMap().put(PR_MOV_DOWN, new MoveAction(((PongGame)currentGame).getPongController().getPongModel().getPaddleRight(),-1));
                this.currentGame.start();
                break;
        }
    }

    @Override
    public void gameEnded(GameEntity game, List<GameResult> results) {
        for(GameResult result: results) {
            this.highscoreService.create(result.getScore(), game.getGameId(), result.getUser().getUserId());
        }

        this.dispatchGameQueue();
    }

    class MoveAction extends AbstractAction{
        Paddle paddle;
        int direction;
        MoveAction(Paddle paddle, int direction){
            this.paddle = paddle;
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(direction==1){
                ((PongGame)currentGame).getPongController().movePaddleUp(paddle);
            }
            else if(direction==-1){
                ((PongGame)currentGame).getPongController().movePaddleDown(paddle);
            }

        }
    }
}