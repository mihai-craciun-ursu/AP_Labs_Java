package knapsack;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {

    private int capacity;
    private List<Item> listOfItems = new ArrayList<>();

    public Knapsack(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    protected void addItemsToKnapsack(List<Item> items){
        this.listOfItems.addAll(items);
    }

    public List<Item> getItemsInsideKnapsack(){
        List<Item> copyOfItemList = new ArrayList<>();
        copyOfItemList.addAll(listOfItems);
        return copyOfItemList;
    }

    @Override
    public String toString() {

        return "capacity of the knapsack = " + this.capacity + "\n" +
                "available items:";

    }
}
