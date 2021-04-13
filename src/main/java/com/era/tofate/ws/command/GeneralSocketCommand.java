package com.era.tofate.ws.command;

import com.era.tofate.payload.socket.GeneralSocketResponse;
import com.era.tofate.ws.enums.ActionType;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface GeneralSocketCommand {
    ActionType getActionType();
    GeneralSocketResponse execute(WebSocketSession session, TextMessage message);
}
