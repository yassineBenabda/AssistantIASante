package org.dsi33.tp4agent_ai.controllers;

import org.dsi33.tp4agent_ai.agents.AIAgent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
@CrossOrigin("*")
public class AIAgentController {

    private final AIAgent agent;

    public AIAgentController(AIAgent agent) {
        this.agent = agent;
    }

    // GET — pour tester facilement dans un navigateur
    @GetMapping("/chat")
    public String askLLM(@RequestParam String query) {
        return agent.onQuery(query);
    }

    // POST — pour le frontend Vue.js (corps JSON)
    @PostMapping("/chat")
    public String askLLMPost(@RequestBody UserQuery body) {
        return agent.onQuery(body.query());
    }
}

record UserQuery(String query) {}
