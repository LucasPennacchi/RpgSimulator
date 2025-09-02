package core;

import logs.LogManager;
import managers.EconomyManager;
import managers.EventManager;
import managers.PopulationManager;
import managers.WarManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HierarchyNode {
    private String name;
    private PopulationManager populationManager;
    private EconomyManager economyManager;
    private WarManager warManager;
    private EventManager eventManager;
    private Map<String, Resource> resources;
    private List<HierarchyNode> children;
    private LogManager logManager;

    public HierarchyNode(String name, LogManager logManager) {
        this.name = name;
        this.logManager = logManager;
        this.resources = new HashMap<>();
        this.children = new ArrayList<>();

        // Managers recebem o logs.LogManager para gerar logs
        this.populationManager = new PopulationManager(logManager);
        this.economyManager = new EconomyManager(logManager);
        this.warManager = new WarManager(logManager);
        this.eventManager = new EventManager(logManager);
    }

    public void addChild(HierarchyNode child) {
        children.add(child);
        logManager.log("NODE", "core.HierarchyNode", "Nó filho adicionado: " + child.getName());
    }

    public String getName() { return name; }
    public PopulationManager getPopulationManager() { return populationManager; }
    public EconomyManager getEconomyManager() { return economyManager; }
    public WarManager getWarManager() { return warManager; }
    public EventManager getEventManager() { return eventManager; }
    public Map<String, Resource> getResources() { return resources; }
}
