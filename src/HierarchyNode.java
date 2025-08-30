import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchyNode {
    private String name;
    private Map<Species, Double> populations; // população por espécie
    private List<HierarchyNode> children;
    private List<Resource> resources;
    private List<String> logs;

    public HierarchyNode(String name) {
        this.name = name;
        this.populations = new HashMap<>();
        this.children = new ArrayList<>();
        this.resources = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    // --- Hierarquia ---
    public HierarchyNode addChild(String childName) {
        HierarchyNode child = new HierarchyNode(childName);
        children.add(child);
        return child;
    }

    public List<HierarchyNode> getChildren() {
        return children;
    }

    // --- População ---
    public void addPopulation(Species species, double count) {
        populations.put(species, populations.getOrDefault(species, 0.0) + count);
    }

    public double getPopulation(Species species) {
        return populations.getOrDefault(species, 0.0);
    }

    public Map<Species, Double> getAllPopulations() {
        return populations;
    }

    public double getTotalPopulation() {
        return populations.values().stream().mapToDouble(Double::doubleValue).sum();
    }

    // --- Recursos ---
    public void addResource(Resource r) {
        resources.add(r);
    }

    public List<Resource> getResources() {
        return resources;
    }

    // --- Logs ---
    public void addLog(int iteration, String message) {
        logs.add("Iteração " + iteration + ": " + message);
    }

    public List<String> getAllLogs() {
        List<String> allLogs = new ArrayList<>(logs);
        for(HierarchyNode child : children) {
            allLogs.addAll(child.getAllLogs());
        }
        return allLogs;
    }

    // --- Getters e Setters ---
    public String getName() {
        return name;
    }
}
