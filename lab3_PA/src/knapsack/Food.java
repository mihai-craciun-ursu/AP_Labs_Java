package knapsack;

public class Food implements Item {

    private String name;
    private int value;
    private int weight;

    public Food(String name, int weight){
        this.name = name;
        this.weight = weight;
        this.value = weight * 2;
    }

    @Override
    public float profitFactor() {
        return (float) value/weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getName() {return name;}

    public int getValue() {
        return value;
    }
}
