public class Species {
    private String name;
    private double averageLifespan; // idade média para morrer

    public Species(String name, double averageLifespan) {
        this.name = name;
        this.averageLifespan = averageLifespan;
    }

    public String getName() {
        return name;
    }

    public double getAverageLifespan() {
        return averageLifespan;
    }
}
