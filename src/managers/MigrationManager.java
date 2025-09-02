package managers;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MigrationManager {

    private static Random random = new Random();

    public static void handleMigration(HierarchyNode node, int iteration) {
        for(HierarchyNode child : node.getChildren()) {
            for(Map.Entry<Species, Double> entry : child.getAllPopulations().entrySet()) {
                Species species = entry.getKey();
                double pop = entry.getValue();

                double satisfaction = calculateSatisfaction(child);
                double migrants = pop * (1 - satisfaction) * 0.05; // até 5% migram
                if(migrants > pop) migrants = pop;

                HierarchyNode target = chooseMigrationTarget(node, child);
                if(target != null) {
                    child.addPopulation(species, -migrants);
                    target.addPopulation(species, migrants);
                    child.addLog(iteration, String.format("%.2f pessoas da espécie %s migraram para %s", migrants, species.getName(), target.getName()));
                }
            }

            handleMigration(child, iteration);
        }
    }

    private static double calculateSatisfaction(HierarchyNode node) {
        double total = 0;
        for(Resource r : node.getResources()) {
            total += r.getAmount() * r.getImpactCoefficient();
        }
        return Math.min(1.0, total / 500);
    }

    private static HierarchyNode chooseMigrationTarget(HierarchyNode parent, HierarchyNode from) {
        List<HierarchyNode> siblings = parent.getChildren();
        if(siblings.size() <= 1) return null;

        HierarchyNode target;
        do {
            int idx = random.nextInt(siblings.size());
            target = siblings.get(idx);
        } while(target == from);

        return target;
    }
}
