package com.era.tofate.ws;

import com.era.tofate.ws.command.SocketCommandHelper;
import com.era.tofate.ws.sessionpool.SocketSessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketSessionPool socketSessionPool;
    private final SocketCommandHelper socketCommandHelper;

    @Autowired
    public WebSocketConfig(SocketSessionPool socketSessionPool, SocketCommandHelper socketCommandHelper) {
        this.socketSessionPool = socketSessionPool;
        this.socketCommandHelper = socketCommandHelper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myMessageHandler(), "/ws").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myMessageHandler() {
        return new SocketHandler(socketSessionPool, socketCommandHelper);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
}
