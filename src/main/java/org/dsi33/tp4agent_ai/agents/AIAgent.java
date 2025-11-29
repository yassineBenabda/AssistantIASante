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
	private ChatClient chatClient;

	public AIAgent(ChatClient.Builder chatClient, ChatMemory chatMemory,AgentTools agentTools,ToolCallbackProvider callbackProvider, VectorStore vectorStore) {
		super();
		this.chatClient = chatClient
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultToolCallbacks(callbackProvider)
                .defaultTools(agentTools)
                //.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
	}
	
	public String onQuery(String query) {
		return chatClient.prompt()
                .user(query)
                .call()
                .content();
		
	}

}
