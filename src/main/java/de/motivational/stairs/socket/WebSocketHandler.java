package de.motivational.stairs.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Florian on 03.01.2017.
 */
public class WebSocketHandler extends TextWebSocketHandler {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HashMap<String, String> connectionHandles = new HashMap<>();

    public WebSocketHandler() {
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
        if(this.connectionHandles.containsValue(session.getId())){
           // UserSession userSession = this.connectionHandles.get(session.getId());
           // userSession.removeSocketSession(session);
           // this.connectionHandles.remove(session);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String body = message.getPayload();
        try {
            //EventHandshakeModel model = objectMapper.readValue(body, EventHandshakeModel.class);
            //model.checkRequiredFields();
            //UserSession userSession = authenticationManagement.getUserSession(model.getAccessToken());
            //userSession.putSocketSession(session, model.getAccessToken());
            //this.connectionHandles.put(session.getId(), userSession);
            //session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new SimpleMessageWrapper(true))));
        } catch (Exception e) {
            //e.printStackTrace();
            //session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new SimpleMessageWrapper(false))));
        }
    }
}
