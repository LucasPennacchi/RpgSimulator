public class Resource {
    private String name;
    private double amount;
    private double productionRate;
    private double minLimit;
    private double maxLimit;
    private double impactCoefficient;

    public Resource(String name, double amount, double productionRate, double minLimit, double impactCoefficient) {
        this.name = name;
        this.amount = amount;
        this.productionRate = productionRate;
        this.minLimit = minLimit;
        this.impactCoefficient = impactCoefficient;
        this.maxLimit = amount * 10; // limite máximo arbitrário, pode ser ajustado
    }

    // Produzir recursos com limite
    public void produce() {
        amount += productionRate;
        if(amount > maxLimit) amount = maxLimit;
    }

    // Getters e setters
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public double getProductionRate() { return productionRate; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setProductionRate(double rate) { this.productionRate = rate; }
    public double getImpactCoefficient() { return impactCoefficient; }
}
