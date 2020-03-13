package knapsack;

public class Weapon implements Item {

    private WeaponType name;
    private int weight;
    private int value;

    public Weapon(WeaponType name, int weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name.getType();
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    @Override
    public float profitFactor() {
        return (float) value/weight;
    }
}
