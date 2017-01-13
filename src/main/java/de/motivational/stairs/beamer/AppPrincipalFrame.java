package de.motivational.stairs.beamer;

import de.motivational.stairs.MotivationalStairsApplication;
import de.motivational.stairs.config.AppConfig;
import de.motivational.stairs.database.entity.GameEntity;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.database.service.HighscoreService;
import de.motivational.stairs.game.general.BeamerGameFrame;
import de.motivational.stairs.database.entity.BeamerSetupEntity;
import de.motivational.stairs.database.service.BeamerSetupService;
import de.motivational.stairs.beamer.opengl.StairsTransformFrame;
import de.motivational.stairs.game.general.timestep.data.GameTicket;
import de.motivational.stairs.game.general.timestep.gpio.RaspberryPIHandler;
import de.motivational.stairs.game.general.timestep.gpio.SwingHandler;
import de.motivational.stairs.game.general.timestep.listener.GameEndedListener;
import de.motivational.stairs.game.general.timestep.data.GameResult;
import de.motivational.stairs.game.general.timestep.GameTimeStep;
import de.motivational.stairs.game.pong.PongGame;
import de.motivational.stairs.game.pong.model.Paddle;
import de.motivational.stairs.rest.dto.GameStartResponseDto;
import de.motivational.stairs.socket.WebSocketHandler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Florian on 11.11.2016.
 */
@Component
public class AppPrincipalFrame implements GameEndedListener {
    private BeamerGameFrame beamerFrame;
    private GameTimeStep currentGame;
    private StairsTransformFrame transformFrame;
    private ConcurrentLinkedQueue<GameTicket> gameTickets;
    private Thread dispatcher;

    @Autowired
    BeamerSetupService beamerSetupService;

    @Autowired
    HighscoreService highscoreService;

    @Autowired
    WebSocketHandler socketHandler;

    @Autowired
    SwingHandler swingHandler;

    @Autowired
    RaspberryPIHandler gpioHandler;

    @Autowired
    AppConfig appConfig;

    Logger logger = Logger.getLogger(AppPrincipalFrame.class);

    public AppPrincipalFrame(@Autowired AppConfig appConfig) {
        this.gameTickets = new ConcurrentLinkedQueue<GameTicket>();
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
       beamerFrame = new BeamerGameFrame();
    }

    public GameStartResponseDto queueGame(GameEntity game, List<UserEntity> users) {
        GameTicket ticket = new GameTicket(game, users);

        GameStartResponseDto response = new GameStartResponseDto(ticket.getGameId());
        response.setUsersBefore(this.getPlayerQueue());
        response.setTicket(ticket.getTicket());

        this.gameTickets.add(ticket);

        this.socketHandler.notifyUsers(users, WebSocketHandler.EVENT.GAME_QUEUEING, "");

        this.dispatchGameQueue();

        this.logger.info(String.format("Creating ticket #%s for game '%s'", ticket.getTicket(), game.getName()));
        return response;
    }

    private void dispatchGameQueue() {
        this.logger.info("Starting redeem timer");
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
                        int redeemSeconds = appConfig.getGameConfig().gameRedeemSeconds;

                        if(currentGame == null || !currentGame.isRunning()) {
                            GameTicket ticket = gameTickets.peek();
                            for(int i = 0; i < redeemSeconds; i++) {
                                if(ticket != null)
                                    socketHandler.notifyUsers(ticket.getUsers(), WebSocketHandler.EVENT.NEXT_TICKET, ""+(redeemSeconds-i));
                            }
                            Thread.sleep(redeemSeconds* 1000);
                            logger.info(String.format("Ticket %s was not redeemed, queueing next ticket", ticket.getTicket()));
                            socketHandler.notifyUsers(ticket.getUsers(), WebSocketHandler.EVENT.TICKET_EXPIRED, "");
                            gameTickets.poll();
                            notifyQueueChanged();
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

    public boolean redeemTicket(String ticket) {
        if(this.gameTickets.size() > 0 &&
                (this.currentGame == null || !this.currentGame.isRunning()) &&
                this.gameTickets.peek().getTicket().equals(ticket)) {
            this.logger.info(String.format("Starting game with ticket %s", ticket));
            notifyQueueChanged();
            this.startNextGame();
            return true;
        } else {
            return false;
        }
    }

    private void notifyQueueChanged() {
        for(GameTicket ticket: this.gameTickets) {
            GameStartResponseDto gameStartResponseDto = new GameStartResponseDto(ticket.getGameId());
            gameStartResponseDto.setTicket(ticket.getTicket());
            gameStartResponseDto.setUsersBefore(getPlayerQueue(ticket.getTicket()));
            socketHandler.notifyUsers(ticket.getUsers(), WebSocketHandler.EVENT.QUEUE_CHANGED, gameStartResponseDto);
        }
    }

    private void startNextGame() {
        GameTicket ticket = this.gameTickets.poll();
        switch(ticket.getGame().getGameId()) {
            case 1:
                this.currentGame = new PongGame(this, ticket, appConfig, socketHandler);
                this.currentGame.setGameFrame(this.beamerFrame);
                try {
                    this.gpioHandler.setInputHandlers(this.currentGame);
                } catch(Exception e){
                    logger.log(Level.ERROR, e.getMessage());
                }
                this.swingHandler.setInputHandlers(this.currentGame, this.beamerFrame);

                this.currentGame.start();

                break;
        }
    }

    @Override
    public void gameEnded(GameEntity game, List<GameResult> results) {
        for(GameResult result: results) {
            this.highscoreService.create(result.getScore(), game.getGameId(), result.getUser().getUserId());
        }

        //RECHTE PADLE VERLIERT, LINKE GPIO LEISTE ROT (NUN R)
        if(results.size()>1){
            if(results.get(0).getScore()>results.get(1).getScore()){
                gpioHandler.setAllColorLedStripLeft(Color.red);

                socketHandler.notifyUserGameEnded(results.get(0).getUser(), true);
                socketHandler.notifyUserGameEnded(results.get(1).getUser(), false);
            } else {
                gpioHandler.setAllColorLedStripRight(Color.red);
                socketHandler.notifyUserGameEnded(results.get(1).getUser(), true);
                socketHandler.notifyUserGameEnded(results.get(0).getUser(), false);
            }
        }

        this.dispatchGameQueue();
    }

    private String getPlayerNames(GameTicket ticket) {
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < ticket.getUsers().size(); i++) {
            name.append(ticket.getUsers().get(i).getName());
            if(i < ticket.getUsers().size() - 1) {
                name.append(" und ");
            }
        }
        return name.toString();
    }

    public String[] getPlayerQueue() {
        return this.gameTickets.stream().map(t -> {
            return this.getPlayerNames(t);
        }).toArray(String[]::new);
    }

    public String[] getPlayerQueue(String ticketId) {
        ArrayList<String> players = new ArrayList<>();
        for(GameTicket ticket: this.gameTickets) {
            if(ticket.getTicket().equals(ticketId))
                break;
            players.add(this.getPlayerNames(ticket));
        }

        return players.toArray(new String[players.size()]);
    }

    public void abortTicket(String ticketId) {
        this.gameTickets.remove(new GameTicket(ticketId));
    }

    public GameTimeStep getCurrentGame() {
        return currentGame;
    }
}