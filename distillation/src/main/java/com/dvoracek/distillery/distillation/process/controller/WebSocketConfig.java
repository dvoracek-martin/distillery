package com.dvoracek.distillery.distillation.process.controller;

import com.dvoracek.distillery.distillation.process.service.DistillationProcessService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String PATH = "/distillery-backend";
    private static final String FRONT_END_URL = "http://localhost:4200";

    private final DistillationProcessService distillationProcessService;

    public WebSocketConfig(DistillationProcessService distillationProcessService) {
        this.distillationProcessService = distillationProcessService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(tradeWebSocketHandler(), PATH).setAllowedOrigins(FRONT_END_URL);
    }

    @Bean
    public WebSocketHandler tradeWebSocketHandler() {
        return new DistillationProcessWebSocketHandler(this.distillationProcessService);
    }
}
