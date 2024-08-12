package com.springai.internal_product_chatbot.controllers.chat;

import com.springai.internal_product_chatbot.services.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
public class ChatController {

    @Autowired
    ChatService chatService;


    @GetMapping("/getjokes")
    String generateJokes(@RequestParam(value = "message", defaultValue = "Tell me about your favorite city") String message) {
        try {
            return chatService.generateJokes(message);
        } catch (RestClientException e) {
            System.err.println("Error while extracting response: " + e.getMessage());
            return "Error: Could not generate a joke.";
        }
    }

    @GetMapping("/answerProductQuery")
    String generateProductAnswer(@RequestParam(value = "question", defaultValue = "Tell me about your favorite city") String question) {
        try {
            return chatService.generateAnswer(question);
        } catch (RestClientException e) {
            System.err.println("Error while extracting response: " + e.getMessage());
            return "Error: Could not generate a joke.";
        }
    }

}