package org.dsi33.tp4agent_ai.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import org.dsi33.tp4agent_ai.tools.AgentTools;

@Service
public class AIAgent {

    private final ChatClient chatClient;

    public AIAgent(ChatClient.Builder chatClient,
                   ChatMemory chatMemory,
                   AgentTools agentTools,
                   ToolCallbackProvider callbackProvider,
                   VectorStore vectorStore) {

        this.chatClient = chatClient
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new QuestionAnswerAdvisor(vectorStore)
                )
                .defaultToolCallbacks(callbackProvider)
                .defaultTools(agentTools)
                .build();
    }

    public String onQuery(String query) {
        return chatClient.prompt()
                .system("""
                        Tu es un assistant IA spécialisé en santé.
                        - Ne donne jamais de diagnostic médical définitif.
                        - Réponds avec un langage simple et clair.
                        - Conseille toujours de consulter un médecin si nécessaire.
                """)
                .user(query)
                .call()
                .content();
    }
}