package managers;

import logs.LogManager;

public class EventManager {
    private LogManager logManager;

    public EventManager(LogManager logManager) {
        this.logManager = logManager;
    }

    public void triggerEvent(HierarchyNode node, String eventName) {
        logManager.log("EVENT", "managers.EventManager", "Evento '" + eventName + "' ocorreu em " + node.getName());
    }
}
