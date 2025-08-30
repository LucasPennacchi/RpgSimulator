import java.util.Map;
import java.util.Random;

public class PopulationManager {

    private static Random random = new Random();

    // Crescimento populacional dinâmico por espécie
    public static void populationGrowth(HierarchyNode node) {
        for(Map.Entry<Species, Double> entry : node.getAllPopulations().entrySet()) {
            Species species = entry.getKey();
            double count = entry.getValue();

            double growthRate = 0.02; // taxa mínima
            if(checkFavorableConditions(node)) {
                growthRate = 0.05 + random.nextDouble() * 0.05; // 5% a 10%
            }

            double newCount = count * (1 + growthRate);
            node.addPopulation(species, newCount - count); // adiciona crescimento
            node.addLog(0, String.format("Espécie %s: população atualizada: %.2f", species.getName(), newCount));
        }

        // Cresce filhos
        for(HierarchyNode child : node.getChildren()) {
            populationGrowth(child);
        }
    }

    private static boolean checkFavorableConditions(HierarchyNode node) {
        // Simples: verifica se recursos essenciais estão acima de 50
        for(Resource r : node.getResources()) {
            if(r.getAmount() < 50) return false;
        }
        return true;
    }
}
