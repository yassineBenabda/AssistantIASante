package org.dsi33.tp4agent_ai.controllers;

import org.dsi33.tp4agent_ai.agents.AIAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIAgentController {
	private AIAgent agent;

	public AIAgentController(AIAgent agent) {
		super();
		this.agent = agent;
	}
	@GetMapping("/chat")
	public String askLLM(String query) {
		return agent.onQuery(query);
		
	}

}
