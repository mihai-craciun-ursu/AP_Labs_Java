package knapsack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Greedy implements Algorithm {

    private List<Item> solutionList = new ArrayList<>();

    public Greedy(List<Item> items, Knapsack knapsack){

        List<Item> tempList = new ArrayList<>();
        tempList.addAll(items);

        Collections.sort(tempList, (i1, i2)->{
            if(i1.profitFactor() > i2.profitFactor()) {return -1;}
            else if (i1.profitFactor() < i2.profitFactor()) {return 1;}
            else {
                if (i1.getWeight() > i2.getWeight()) {
                    return -1;
                }else if(i2.getWeight() > i1.getWeight()){
                    return 1;
                }
                return 0;
                }
            });

        int capacity = knapsack.getCapacity();
        int index = 0;
        while(capacity > tempList.get(index).getWeight()){
            solutionList.add(tempList.get(index));
            capacity = capacity - tempList.get(index).getWeight();
            index = index + 1;

        }
    }

    @Override
    public List<Item> getSolution() {
        return solutionList;
    }
}
