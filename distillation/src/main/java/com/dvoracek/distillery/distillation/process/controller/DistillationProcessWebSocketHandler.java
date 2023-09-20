package com.dvoracek.distillery.distillation.process.controller;

import com.dvoracek.distillery.distillation.process.service.DistillationProcessService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DistillationProcessWebSocketHandler extends TextWebSocketHandler {
    private static final String FRONTEND_WS_CODE = "distillery-frontend";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final DistillationProcessService distillationProcessService;

    public DistillationProcessWebSocketHandler(DistillationProcessService distillationProcessService) {
        this.distillationProcessService = distillationProcessService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage textMessage) {
            this.handleTextMessage(session, textMessage);
            if (message.getPayload().toString().equals("\"" + FRONTEND_WS_CODE + "\"")) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(distillationProcessService.getDataForFrontend())));
            }
        } else {
            throw new IllegalStateException("Unexpected WebSocket message type: " + message);
        }
    }
}
