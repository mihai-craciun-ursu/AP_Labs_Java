package knapsack;

public class Book implements Item{

    private String name;
    private int weight;
    private int value;
    private int numberOfPages;

    public Book(String name, int value, int numberOfPages) {
        this.name = name;
        this.value = value;
        this.numberOfPages = numberOfPages;
        this.weight = numberOfPages/100;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public float profitFactor() {
        return (float) value/weight;
    }
}
