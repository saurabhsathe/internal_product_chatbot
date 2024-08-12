package com.springai.internal_product_chatbot.serviceImpl.chat;

import com.springai.internal_product_chatbot.services.chat.ChatService;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    public ChatServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @Override
    public String generateJokes(String topic) {
        return chatClient.prompt()
                .user(topic)
                .call()
                .content();
    }

    @Override
    public String generateAnswer(String question) {
        return null;
    }
}
