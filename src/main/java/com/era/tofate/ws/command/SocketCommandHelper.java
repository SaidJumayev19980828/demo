package com.era.tofate.ws.command;

import com.era.tofate.payload.socket.GeneralSocketRequest;
import com.era.tofate.ws.enums.ActionType;
import com.era.tofate.ws.exception.BadDataSocketException;
import com.era.tofate.ws.exception.UnknownActionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.socket.TextMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketCommandHelper {
    private static final String BAD_DATA_FORMAT = "Некорректный формат входных данных";
    private static final String UNKNOWN_COMMAND = "Неизвестная команда";

    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<ActionType, GeneralSocketCommand> commands = new HashMap<>();
    private final BeanFactory beanFactory;

    public SocketCommandHelper(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @PostConstruct
    private void init() {
        commands.put(ActionType.AUTH, getCommandByQualifier(ActionType.AUTH.getAction()));
        commands.put(ActionType.AUTHSMSFIREBASE, getCommandByQualifier(ActionType.AUTHSMSFIREBASE.getAction()));
    }

    public GeneralSocketCommand getCommand(TextMessage message) {
        try {
            GeneralSocketRequest request = mapper.readValue(message.getPayload(), GeneralSocketRequest.class);
            GeneralSocketCommand socketCommand = commands.get(request.getAction());

            if (socketCommand != null) {
                return socketCommand;
            } else {
                throw new UnknownActionException(UNKNOWN_COMMAND);
            }
        } catch (UnknownActionException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BadDataSocketException(BAD_DATA_FORMAT, ex);
        }
    }

    private GeneralSocketCommand getCommandByQualifier(String qualifier) {
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(beanFactory, GeneralSocketCommand.class, qualifier);
    }
}
