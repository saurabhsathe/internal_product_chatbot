package com.springai.internal_product_chatbot.serviceImpl.chat;

import com.springai.internal_product_chatbot.services.chat.ChatService;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {


    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("${prompt.template.path}")
    private Resource sbPromptTemplate;




    public ChatServiceImpl(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .build();
        this.vectorStore = vectorStore;
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
        PromptTemplate promptTemplate = new PromptTemplate(sbPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", question);
        promptParameters.put("documents", String.join("\n", findSimilarDocuments(question)));
        return chatClient.prompt().user((Resource) promptTemplate.create(promptParameters)).call().content();
    }


    private List<String> findSimilarDocuments(String message) {
        List<Document> similarDocuments = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(3));
        return similarDocuments.stream().map(Document::getContent).toList();
    }

}
