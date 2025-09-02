package managers;

import java.util.Random;

public class EconomyManager {

    private static Random random = new Random();

    public static void updateMarket(HierarchyNode node, int iteration) {
        for(Resource r : node.getResources()) {
            double demandFactor = 1 + random.nextDouble() * 0.5;
            double supplyFactor = r.getAmount() > 0 ? 1 : 0.5;
            double priceChange = demandFactor / supplyFactor;

            node.addLog(iteration, "Recurso " + r.getName() + " ajustado no mercado. Fator: " + String.format("%.2f", priceChange));
        }

        for(HierarchyNode child : node.getChildren()) {
            updateMarket(child, iteration);
        }
    }
}
