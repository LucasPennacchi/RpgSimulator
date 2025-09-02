package managers;

import logs.LogManager;

import java.util.*;

public class WarManager {
    private LogManager logManager;
    private double lossFactorBase = 0.1; // perda base de 10%

    public WarManager(LogManager logManager) {
        this.logManager = logManager;
    }

    /**
     * Calcula o "poder" de um nódulo considerando população, recursos e posses.
     */
    private double calculatePower(HierarchyNode node) {
        double population = node.getAllPopulations().values().stream().mapToDouble(Double::doubleValue).sum();
        double resources = node.getResources().stream().mapToDouble(Resource::getAmount).sum();
        double childrenPower = node.getChildren().stream().mapToDouble(this::calculatePower).sum();
        return population + (resources * 0.5) + childrenPower;
    }

    /**
     * Marca o status de guerra para o nódulo e todos os filhos.
     */
    private void markWarStatus(HierarchyNode node) {
        node.setInWar(true);
        for (HierarchyNode child : node.getChildren()) {
            markWarStatus(child);
        }
    }

    /**
     * Inicia guerra entre múltiplos nódulos.
     */
    public void startWar(List<HierarchyNode> participants, int iteration) {
        if (participants.size() < 2) {
            logManager.log("WAR", "managers.WarManager", "Guerra precisa de pelo menos dois nódulos.");
            return;
        }

        // Calcula poder total
        Map<HierarchyNode, Double> powerMap = new HashMap<>();
        for (HierarchyNode node : participants) {
            markWarStatus(node);
            double power = calculatePower(node);
            powerMap.put(node, power);
        }

        logManager.log("WAR", "managers.WarManager", "Guerra iniciada entre: " +
                participants.stream().map(HierarchyNode::getName).reduce((a, b)->a+", "+b).orElse(""));

        // Determina perdas proporcionais ao poder
        double totalPower = powerMap.values().stream().mapToDouble(Double::doubleValue).sum();

        for (HierarchyNode node : participants) {
            double power = powerMap.get(node);
            double lossFactor = lossFactorBase * (power / totalPower + 1.0); // mais poder → mais perda

            // População
            for (Map.Entry<Species, Double> entry : node.getAllPopulations().entrySet()) {
                Species s = entry.getKey();
                double lost = entry.getValue() * lossFactor;
                node.addPopulation(s, -lost);
                node.addLog(iteration, String.format("Perdeu %.2f da espécie %s durante guerra", lost, s.getName()));
            }

            // Recursos
            for (Resource r : node.getResources()) {
                double newAmount = r.getAmount() * (1 - lossFactor);
                r.setAmount(newAmount);
            }
        }
    }

    /**
     * Encerra guerra para um nódulo e seus filhos.
     */
    public void endWar(HierarchyNode node) {
        node.setInWar(false);
        for (HierarchyNode child : node.getChildren()) {
            endWar(child);
        }
        logManager.log("WAR", "managers.WarManager", "Guerra terminou para " + node.getName());
    }
}
