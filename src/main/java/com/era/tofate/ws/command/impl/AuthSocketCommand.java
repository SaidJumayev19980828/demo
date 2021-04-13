package com.era.tofate.ws.command.impl;

import com.era.tofate.entities.user.User;
import com.era.tofate.payload.socket.GeneralSocketRequest;
import com.era.tofate.payload.socket.GeneralSocketResponse;
import com.era.tofate.payload.socket.auth.AuthSocketRequest;
import com.era.tofate.payload.socket.auth.AuthSocketResponse;
import com.era.tofate.security.TokenProvider;
import com.era.tofate.service.user.UserService;
import com.era.tofate.ws.command.GeneralSocketCommand;
import com.era.tofate.ws.enums.ActionType;
import com.era.tofate.ws.exception.AuthSocketException;
import com.era.tofate.ws.sessionpool.SocketSessionPool;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Qualifier(value = "auth")
public class AuthSocketCommand implements GeneralSocketCommand {

    private final ObjectMapper mapper = new ObjectMapper();
    private final SocketSessionPool socketSessionPool;
    private final UserService userService;
    private final TokenProvider tokenProvider;


    public AuthSocketCommand(
            SocketSessionPool socketSessionPool,
            UserService userService,
            TokenProvider tokenProvider) {
        this.socketSessionPool = socketSessionPool;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.AUTH;
    }

    @Override
    public GeneralSocketResponse execute(WebSocketSession session, TextMessage message) {
        try {
            GeneralSocketRequest<AuthSocketRequest> request = mapper.readValue(message.getPayload(),
                    new TypeReference<GeneralSocketRequest<AuthSocketRequest>>() {
                    });

            AuthSocketRequest authSocketRequest = request.getObject();
            User account;
            AuthSocketResponse authSocketResponse = new AuthSocketResponse("ERROR", null);

            if (tokenProvider.validateToken(authSocketRequest.getAccessToken())) {
                Long userId = tokenProvider.getUserIdFromToken(authSocketRequest.getAccessToken());

                if (userService.findById(userId).isPresent()) {
                    account = userService.findById(userId).get();
                    socketSessionPool.openSession(session, account);
                    authSocketResponse.setStatus("OK");
                    authSocketResponse.setAccountId(account.getId());
                } else {
                    throw new AuthSocketException("AUTHORIZATION_ERROR");
                }
            } else {
                throw new AuthSocketException("AUTHORIZATION_ERROR");
            }

            return new GeneralSocketResponse<>(getActionType(), authSocketResponse);
        } catch (Exception ex) {
            throw new AuthSocketException("AUTHORIZATION_ERROR", ex);
        }
    }

}
