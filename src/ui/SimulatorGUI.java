package ui;

import javax.swing.*;

import managers.EconomyManager;
import managers.MigrationManager;
import managers.PopulationManager;
import managers.WarManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SimulatorGUI {

    private HierarchyNode pais1, pais2;
    private JTextArea logArea;

    private Timer iterationTimer;
    private int currentIteration = 0;
    private int intervalMillis = 2000; // 2 segundos padrão

    public SimulatorGUI() {
        setupSimulator();
        createGUI();
    }

    private void setupSimulator() {
        // --- Criar hierarquia ---
        pais1 = new HierarchyNode("País A");
        HierarchyNode estado1 = pais1.addChild("Estado A1");
        HierarchyNode cidade1 = estado1.addChild("Cidade A1-1");
        HierarchyNode cidade2 = estado1.addChild("Cidade A1-2");

        pais2 = new HierarchyNode("País B");
        HierarchyNode estado2 = pais2.addChild("Estado B1");
        HierarchyNode cidade3 = estado2.addChild("Cidade B1-1");

        // --- Criar espécies ---
        Species humana = new Species("Humana", 80);
        Species elfa = new Species("Elfa", 200);

        // --- População inicial ---
        cidade1.addPopulation(humana, 100);
        cidade1.addPopulation(elfa, 20);
        cidade2.addPopulation(humana, 50);
        cidade3.addPopulation(humana, 80);

        // --- Recursos iniciais ---
        cidade1.addResource(new Resource("Comida", 500, 20, 1, 1.0));
        cidade1.addResource(new Resource("Madeira", 300, 15, 0.5, 0.3));

        cidade2.addResource(new Resource("Comida", 200, 10, 1, 1.0));
        cidade2.addResource(new Resource("Madeira", 100, 5, 0.5, 0.3));

        cidade3.addResource(new Resource("Comida", 400, 18, 1, 1.0));
        cidade3.addResource(new Resource("Pedra", 150, 7, 0.5, 0.2));
    }

    private void createGUI() {
        JFrame frame = new JFrame("RPG Simulator GUI - Multiespécies");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel panel = new JPanel(new BorderLayout());

        // --- Botões e controles ---
        JPanel controlPanel = new JPanel(new FlowLayout());

        JButton btnStart = new JButton("Iniciar Timer");
        JButton btnPause = new JButton("Pausar Timer");
        JButton btnSingleIteration = new JButton("Executar Iteração Única");
        JButton btnGrowPopulation = new JButton("Crescer População");
        JButton btnProduceResources = new JButton("Produzir Recursos");
        JButton btnStartWar = new JButton("Iniciar Guerra");
        JButton btnShowLogs = new JButton("Mostrar Logs País A");

        JLabel lblInterval = new JLabel("Intervalo (ms):");
        JTextField txtInterval = new JTextField(String.valueOf(intervalMillis), 5);

        controlPanel.add(btnStart);
        controlPanel.add(btnPause);
        controlPanel.add(btnSingleIteration);
        controlPanel.add(btnGrowPopulation);
        controlPanel.add(btnProduceResources);
        controlPanel.add(btnStartWar);
        controlPanel.add(btnShowLogs);
        controlPanel.add(lblInterval);
        controlPanel.add(txtInterval);

        // --- Área de logs ---
        logArea = new JTextArea(30, 90);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        panel.add(controlPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);

        // --- Timer ---
        iterationTimer = new Timer(intervalMillis, e -> runIteration());

        // --- Ações ---
        btnStart.addActionListener(e -> {
            try {
                intervalMillis = Integer.parseInt(txtInterval.getText());
                iterationTimer.setDelay(intervalMillis);
                iterationTimer.start();
                appendLog("Timer iniciado. Intervalo: " + intervalMillis + "ms.");
            } catch(NumberFormatException ex) {
                appendLog("Erro: insira um valor válido para intervalo.");
            }
        });

        btnPause.addActionListener(e -> {
            iterationTimer.stop();
            appendLog("Timer pausado.");
        });

        btnSingleIteration.addActionListener(e -> runIteration());

        btnGrowPopulation.addActionListener(e -> {
            PopulationManager.populationGrowth(pais1);
            PopulationManager.populationGrowth(pais2);
            appendLog("População atualizada!");
        });

        btnProduceResources.addActionListener(e -> {
            produceResources(pais1);
            produceResources(pais2);
            appendLog("Recursos produzidos!");
        });

        btnStartWar.addActionListener(e -> {
            List<HierarchyNode> cidadesPais1 = pais1.getChildren().get(0).getChildren();
            List<HierarchyNode> cidadesPais2 = pais2.getChildren().get(0).getChildren();
            if(!cidadesPais1.isEmpty() && !cidadesPais2.isEmpty()) {
                WarManager.startWar(cidadesPais1.get(0), cidadesPais2.get(0), currentIteration+1);
                appendLog("Guerra iniciada entre " + cidadesPais1.get(0).getName() + " e " + cidadesPais2.get(0).getName() + "!");
            }
        });

        btnShowLogs.addActionListener(e -> {
            appendLog("--- Histórico País A ---");
            List<String> logs = pais1.getAllLogs();
            for(String log : logs) appendLog(log);
            appendLog("-----------------------");
        });
    }

    private void runIteration() {
        currentIteration++;
        appendLog("\n=== Iteração " + currentIteration + " ===");

        // Crescimento populacional
        PopulationManager.populationGrowth(pais1);
        PopulationManager.populationGrowth(pais2);

        // Produção de recursos
        produceResources(pais1);
        produceResources(pais2);

        // Economia
        EconomyManager.updateMarket(pais1, currentIteration);
        EconomyManager.updateMarket(pais2, currentIteration);

        // Migração
        MigrationManager.handleMigration(pais1, currentIteration);
        MigrationManager.handleMigration(pais2, currentIteration);

        appendLog("Iteração " + currentIteration + " concluída.\n");
    }

    private void produceResources(HierarchyNode node){
        for(Resource r : node.getResources()){
            r.produce();
            node.addLog(currentIteration, "Produziu " + String.format("%.2f", r.getProductionRate()) + " de " + r.getName() + " (Total: " + String.format("%.2f", r.getAmount()) + ")");
        }
        for(HierarchyNode child : node.getChildren()) produceResources(child);
    }

    private void appendLog(String text){
        logArea.append(text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimulatorGUI());
    }
}
