package com.quiz.server.service.impl;

import com.quiz.server.service.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void sendMessage(String topicSuffix) {
        messagingTemplate.convertAndSend("/topic/" + topicSuffix, "Default message from WebSocketService");
    }
}
