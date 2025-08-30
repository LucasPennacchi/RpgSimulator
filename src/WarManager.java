public class WarManager {

    public static void startWar(HierarchyNode attacker, HierarchyNode defender, int iteration) {
        double lossFactor = 0.1; // 10% perda

        // Reduz população por espécie
        for(Map.Entry<Species, Double> entry : attacker.getAllPopulations().entrySet()) {
            Species s = entry.getKey();
            double lost = entry.getValue() * lossFactor;
            attacker.addPopulation(s, -lost);
            attacker.addLog(iteration, String.format("Perdeu %.2f pessoas da espécie %s em guerra com %s", lost, s.getName(), defender.getName()));
        }

        for(Map.Entry<Species, Double> entry : defender.getAllPopulations().entrySet()) {
            Species s = entry.getKey();
            double lost = entry.getValue() * lossFactor;
            defender.addPopulation(s, -lost);
            defender.addLog(iteration, String.format("Perdeu %.2f pessoas da espécie %s em guerra com %s", lost, s.getName(), attacker.getName()));
        }

        // Reduz recursos
        for(Resource r : attacker.getResources()) r.setAmount(r.getAmount() * 0.9);
        for(Resource r : defender.getResources()) r.setAmount(r.getAmount() * 0.9);
    }
}
