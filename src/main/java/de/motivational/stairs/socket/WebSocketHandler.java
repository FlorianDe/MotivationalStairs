package de.motivational.stairs.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.motivational.stairs.database.entity.UserEntity;
import de.motivational.stairs.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by Florian on 03.01.2017.
 */
@Service
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    BiMap<UserEntity, WebSocketSession> connectionHandles = HashBiMap.create();

    public enum EVENT {
        NEXT_TICKET,
        QUEUE_CHANGED
    }

    public WebSocketHandler() {

    }

    public void notifyUsers(EVENT eventType, String payload) {
        this.notifyUsers(this.connectionHandles.keySet(), eventType, payload);
    }

    public void notifyUsers(Collection<UserEntity> users, EVENT eventType, String payload) {
        TextMessage message = wrapMessage(eventType, payload);
        for(UserEntity user: users) {
            this.notifyUser(user, message);
        }
    }

    public void notifyUser(UserEntity user, EVENT eventType, String payload) {
        this.notifyUser(user, wrapMessage(eventType, payload));
    }

    public void notifyUser(UserEntity user, TextMessage message) {
        try {
            this.connectionHandles.get(user).sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TextMessage wrapMessage(EVENT eventType, String payload) {
        return  new TextMessage(String.format("{ \"event\": \"%s\", \"payload\":\"%s\" }", eventType, payload == null?"":payload));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error "+ exception.getMessage());
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.deleteSession(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String cookie = message.getPayload();
        try {
            Optional<UserEntity> user = userService.findOneByCookie(cookie);
            if(user.isPresent()) {
                this.connectionHandles.put(user.get(), session);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteSession(WebSocketSession session ){
        if(this.connectionHandles.containsValue(session)){
            UserEntity user = this.connectionHandles.inverse().get(session);
            this.connectionHandles.remove(user);
        }
    }
}
