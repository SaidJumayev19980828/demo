package com.era.tofate.ws;

import com.era.tofate.payload.GeneralSocketError;
import com.era.tofate.payload.socket.GeneralSocketResponse;
import com.era.tofate.ws.command.GeneralSocketCommand;
import com.era.tofate.ws.command.SocketCommandHelper;
import com.era.tofate.ws.command.impl.AuthSmsFirebaseSocketCommand;
import com.era.tofate.ws.command.impl.AuthSocketCommand;
import com.era.tofate.ws.exception.AuthSocketException;
import com.era.tofate.ws.exception.BadDataSocketException;
import com.era.tofate.ws.exception.GeneralSocketException;
import com.era.tofate.ws.exception.UnknownActionException;
import com.era.tofate.ws.sessionpool.SocketSessionPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(SocketHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final SocketSessionPool socketSessionPool;
    private final SocketCommandHelper socketCommandHelper;

    @Autowired
    public SocketHandler(SocketSessionPool socketSessionPool,
                         SocketCommandHelper socketCommandHelper) {
        this.socketSessionPool = socketSessionPool;
        this.socketCommandHelper = socketCommandHelper;

    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.trace("message : " + message);
        GeneralSocketCommand socketCommand = null;

        try {
            socketCommand = socketCommandHelper.getCommand(message);
            System.out.println("socketCommand.getClass() "  + socketCommand.getClass());

            if (!(socketCommand instanceof AuthSocketCommand)) {
        //        socketSessionPool.checkAuth(session);
            }

            if (!(socketCommand instanceof AuthSmsFirebaseSocketCommand)) {
        //        socketSessionPool.checkAuth(session);
            }

            GeneralSocketResponse result = socketCommand.execute(session, message);

            sendMessage(session, result);
        } catch (GeneralSocketException ex) {
            log.error(ex.getMessage(), ex.getCause());
            sendMessage(session, new GeneralSocketError(socketCommand.getActionType(), ex.getMessage()));
        } catch (UnknownActionException | BadDataSocketException ex) {
            log.error(ex.getMessage(), ex.getCause());
            sendMessage(session, new GeneralSocketError(null, ex.getMessage()));
        } catch (AuthSocketException ex) {
            log.error(ex.getMessage(), ex.getCause());
            sendMessage(session, new GeneralSocketError(null, ex.getMessage()));
            socketSessionPool.closeSession(session, CloseStatus.NORMAL);
        }
    }

    private void sessionSendMessage(WebSocketSession session, HashMap<String, String> result) {
        try {
            session.sendMessage(new TextMessage(new Gson().toJson(result)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) {
        log.error(throwable.getMessage(), throwable.getCause());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }


    private void sendMessage(WebSocketSession session, Object object) throws IOException {
        if (session.isOpen()) {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(object)));
        }
    }
}
