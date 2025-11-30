package org.dsi33.tp4agent_ai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class AgentTools {

    @Tool(description = "Calcule l'IMC et fournit un diagnostic santé simplifié")
    public ImcResponse calculerImc(
            @ToolParam(description = "poids en kilogrammes") double poids,
            @ToolParam(description = "taille en mètres") double taille) {

        double imc = poids / (taille * taille);
        String etat;

        if (imc < 18.5) {
            etat = "Insuffisance pondérale";
        } else if (imc < 25) {
            etat = "Corpulence normale";
        } else if (imc < 30) {
            etat = "Surpoids";
        } else {
            etat = "Obésité";
        }

        return new ImcResponse(imc, etat);
    }
}

record ImcResponse(double imc, String diagnostic) {}
