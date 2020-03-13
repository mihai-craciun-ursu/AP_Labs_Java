package knapsack;

import java.util.ArrayList;
import java.util.List;

public class Dynamic implements Algorithm {

    private List<Item> solutionList = new ArrayList<>();

    public Dynamic(List<Item> items, Knapsack knapsack){
        int totalSize = items.size();
        int capacity = knapsack.getCapacity();

        List<Integer> weightOfParticularItem = new ArrayList<>();
        for(Item item : items){
            weightOfParticularItem.add(item.getWeight());
        }

        List<Integer> valueOfParticularItem = new ArrayList<>();
        for(Item item : items){
            valueOfParticularItem.add(item.getValue());
        }

        int i, w;
        int knapsackSolution[][] = new int[totalSize + 1][capacity + 1];

        for (i = 0; i <= totalSize; i++) {
            for (w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0)
                    knapsackSolution[i][w] = 0;
                else if (weightOfParticularItem.get(i-1) <= w)
                    knapsackSolution[i][w] = Math.max(valueOfParticularItem.get(i-1) +
                            knapsackSolution[i - 1][w - weightOfParticularItem.get(i-1)], knapsackSolution[i - 1][w]);
                else
                    knapsackSolution[i][w] = knapsackSolution[i - 1][w];
            }
        }

        int res = knapsackSolution[totalSize][capacity];

        w = capacity;
        for (i = totalSize; i > 0 && res > 0; i--) {
            if (res == knapsackSolution[i - 1][w])
                continue;
            else {

                solutionList.add(items.get(i-1));

                res = res - valueOfParticularItem.get(i - 1);
                w = w - weightOfParticularItem.get(i - 1);
            }
        }
    }

        @Override
    public List<Item> getSolution() {
        return solutionList;
    }
}
