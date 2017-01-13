package de.motivational.stairs.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.motivational.stairs.database.entity.HighscoreEntity;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.database.service.UserService;
import de.motivational.stairs.rest.dto.UserDto;
import de.motivational.stairs.rest.dto.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

/**
 * Created by Florian on 03.01.2017.
 */
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    BiMap<UserEntity, WebSocketSession> connectionHandles = HashBiMap.create();
    HashMap<UserEntity, Boolean> gameResultMap = new HashMap<>();

    public synchronized void updateUsers() {
        for(UserEntity user: this.connectionHandles.keySet()){
            WebSocketSession session = this.connectionHandles.get(user);
            UserEntity refreshed = userService.findOne(user.getUserId()).get();
            this.connectionHandles.put(refreshed, session);
        }
    }


    public class MessageWrapper {
        public EVENT event;
        public Object payload;

        public MessageWrapper(EVENT event, Object payload) {
            this.event = event;
            this.payload = payload;
        }
    }

    public class PlayRequestResponseWrapper {
        public boolean value;
        public UserDto user;
        public PlayRequestResponseWrapper(UserEntity user, boolean value) {
            this.value = value;
            this.user = new UserDto(user);
        }
    }

    public enum EVENT {
        NEXT_TICKET,
        TICKET_EXPIRED,
        QUEUE_CHANGED,
        NEW_PLAYER_LIST,
        SOCKET_ESTABLISHED,
        PLAY_REQUEST,
        PLAY_RESPONSE,
        GAME_QUEUEING,
        GAME_STARTING,
        GAME_OVER,
        SOCKET_ERROR
    }

    public WebSocketHandler() {

    }

    public Set<UserEntity> getAllActiveUsers() {
        return this.connectionHandles.keySet();
    }

    private String getUserDtoJson(UserEntity user) {
        try {
            return this.objectMapper.writerWithView(View.class).writeValueAsString(new UserDto(user));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void notifyUsers(EVENT eventType, Object payload) {
        this.notifyUsers(this.connectionHandles.keySet(), eventType, payload);
    }

    public void notifyUsers(Collection<UserEntity> users, EVENT eventType, Object payload) {
        TextMessage message = wrapMessage(eventType, payload);
        for(UserEntity user: users) {
            this.notifyUser(user, message);
        }
    }

    public void notifyUser(UserEntity user, EVENT eventType, Object payload) {
        this.notifyUser(user, wrapMessage(eventType, payload));
    }

    public void notifyUser(UserEntity user, TextMessage message) {
        try {
            WebSocketSession webSocketSession = this.connectionHandles.get(user);
            if(webSocketSession != null) {
                webSocketSession.sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TextMessage wrapMessage(EVENT eventType, Object payload) {
        try {
            return  new TextMessage(this.objectMapper.writeValueAsString(new MessageWrapper(eventType, payload)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new TextMessage(String.format("{ \"event\": \"%s\", \"payload\":\"%s\" }", EVENT.SOCKET_ERROR, "Internal Server Error"));
    }

    public void notifyUserGameEnded(UserEntity user, boolean b) {
        WebSocketSession webSocketSession = this.connectionHandles.get(user);
        if(webSocketSession == null) {
            this.gameResultMap.put(user, b);
        } else {
            this.notifyUser(user, EVENT.GAME_OVER, b);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        this.deleteSession(session);
        notifyUsers(EVENT.NEW_PLAYER_LIST, "");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.deleteSession(session);
        notifyUsers(EVENT.NEW_PLAYER_LIST, "");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = message.getPayload();
        try {
            switch(msg.substring(0,1)) {
                case "R":
                    try {
                        Optional<UserEntity> user = userService.findOneByCookie(msg.substring(1));
                        if(user.isPresent()) {
                            this.connectionHandles.put(user.get(), session);
                            session.sendMessage(wrapMessage(EVENT.SOCKET_ESTABLISHED, this.objectMapper.writeValueAsString(true)));
                            for(UserEntity other : this.connectionHandles.keySet()) {
                                if(!user.get().equals(other)) {
                                    notifyUser(other, EVENT.NEW_PLAYER_LIST, this.getUserDtoJson(user.get()));
                                }
                            }
                            if(this.gameResultMap.containsKey(user.get())) {
                                this.notifyUser(user.get(), EVENT.GAME_OVER, this.gameResultMap.get(user.get()));
                                this.gameResultMap.remove(user.get());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "U":
                    if(this.connectionHandles.values().contains(session)) {
                        // find out who wants to request
                        UserEntity requester = this.connectionHandles.inverse().get(session);

                        Optional<UserEntity> user = userService.findOne(msg.substring(1));
                        // user must be present
                        // user must be online
                        if( user.isPresent() &&
                                this.connectionHandles.containsKey(user.get()) ) {
                            notifyUser(user.get(), EVENT.PLAY_REQUEST, this.getUserDtoJson(requester));
                        }
                    }
                case "A":
                    if(this.connectionHandles.values().contains(session)) {
                        UserEntity requester = this.connectionHandles.inverse().get(session);
                        if(requester != null) {
                            Optional<UserEntity> user = userService.findOne(msg.substring(2));
                            if(user.isPresent() && this.connectionHandles.containsKey(user.get())) {
                                notifyUser(user.get(), EVENT.PLAY_RESPONSE,
                                        new PlayRequestResponseWrapper(requester, "T".equals(msg.substring(1,2))?true:false));
                            }
                        }
                    }
                    break;
            }
        } catch(NumberFormatException nfException) {
            session.sendMessage(wrapMessage(EVENT.SOCKET_ERROR, "Bad number format"));
        }
    }

    private void deleteSession(WebSocketSession session ){
        if(this.connectionHandles.containsValue(session)){
            UserEntity user = this.connectionHandles.inverse().get(session);
            this.connectionHandles.remove(user);
        }
    }
}
