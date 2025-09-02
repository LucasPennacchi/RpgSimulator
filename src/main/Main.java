package main;

import core.HierarchyNode;
import core.Resource;
import core.Species;

import managers.EconomyManager;
import managers.EventManager;
import managers.MigrationManager;
import managers.PopulationManager;
import managers.WarManager;

import logs.LogManager;

import ui.SimulatorGUI;

public class Main {
    public static void main(String[] args) {
        LogManager logManager = new LogManager();
        HierarchyNode rootNode = new HierarchyNode("Root", logManager);

        PopulationManager populationManager = new PopulationManager(logManager);
        EconomyManager economyManager = new EconomyManager(logManager);
        WarManager warManager = new WarManager(logManager);
        MigrationManager migrationManager = new MigrationManager(logManager);
        EventManager eventManager = new EventManager(logManager);

        SimulatorGUI gui = new SimulatorGUI(rootNode, logManager);
        gui.start();
    }
}
