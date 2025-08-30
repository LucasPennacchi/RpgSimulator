public class Main {

    public static void main(String[] args) {
        // Inicializa a GUI
        SimulatorGUI gui = new SimulatorGUI();

        // Caso queira rodar sem GUI, pode-se criar simulação por console
        /*
        HierarchyNode pais = new HierarchyNode("País X");
        HierarchyNode cidade = pais.addChild("Cidade X1");
        cidade.setPopulation(100);
        cidade.addResource(new Resource("Comida", 500, 20, 1, 1.0));

        for(int i=0; i<10; i++) {
            PopulationManager.populationGrowth(pais);
            EconomyManager.updateMarket(pais, i+1);
            MigrationManager.handleMigration(pais, i+1);
        }

        for(String log : pais.getAllLogs()) {
            System.out.println(log);
        }
        */
    }
}
