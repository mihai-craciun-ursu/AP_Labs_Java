import knapsack.*;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static int callAlgorithm(Algorithm algorithm){

        List<Item> solutionGreedy = algorithm.getSolution();


        int totalValue = 0;
        for(Item item : solutionGreedy){
            System.out.println(item.getName());
            totalValue = totalValue + item.getValue();
        }

        return totalValue;

    }

    public static void main(String[] args) {


        Item book1 = new Book("Dragon Rising", 5, 321);
        Item book2 = new Book("A Blade in the Dark", 5, 322);
        Item food1 = new Food("Cabbage", 2);
        Item food2 = new Food("Rabbit", 2);
        Item weapon = new Weapon(WeaponType.SWORD, 5, 10);

        Knapsack knapsack = new Knapsack(10);

        List<Item> itemList = new ArrayList<>();
        itemList.add(book1);
        itemList.add(book2);
        itemList.add(food1);
        itemList.add(food2);
        itemList.add(weapon);


        System.out.println(knapsack);

        for(Item item : itemList){
            if(item instanceof Book){
                System.out.println("book: " + item.getName() + ", weight = " + item.getWeight() + ", value = " + item.getValue() + " ( profit factor = " + item.profitFactor() + ")");
            }else if(item instanceof Food){
                System.out.println("food: " + item.getName() + ", weight = " + item.getWeight() + ", value = " + item.getValue() + " ( profit factor = " + item.profitFactor() + ")");
            }else if(item instanceof Weapon){
                System.out.println("weapon: " + item.getName() + ", weight = " + item.getWeight() + ", value = " + item.getValue() + " ( profit factor = " + item.profitFactor() + ")");
            }
        }



        long startTimeGreedy = System.nanoTime();
        int greedyProfit = callAlgorithm(new Greedy(itemList, knapsack));
        long endTimeGreedy = System.nanoTime();

        System.out.println("Greedy total profit: " + greedyProfit + " (Time : " + (endTimeGreedy - startTimeGreedy)/1000000.0 + ")");


        long startTimeDynamic = System.nanoTime();
        int dynamicProfit = callAlgorithm(new Dynamic(itemList, knapsack));
        long endTimeDynamic = System.nanoTime();

        System.out.println("Dynamic total profit: " + dynamicProfit + " (Time : " + ((endTimeDynamic - startTimeDynamic)/1000000.0) + ")");

    }

}
