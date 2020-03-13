package Bonus;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Client c1 = new Client("C1");
        Client c2 = new Client("C2");
        Client c3 = new Client("C3");
        Client c4 = new Client("C4");
        Client c5 = new Client("C5");

        List<Client> listOfClients = new ArrayList<>();

        listOfClients.add(c1);
        listOfClients.add(c2);
        listOfClients.add(c3);


        Depot depot1 = new Depot("D1");
        Depot depot2 = new Depot("D2");

        int matrixVehicle1[][] = new int[][] {
                { 0, -1, 2, 5, 4},
                { -1, -1, -1, -1, -1},
                { 2, -1, 0, 5, 2},
                { 5, -1, 5, 0, -1},
                { 4, -1, 2, -1, 0}};

        int matrixVehicle2[][] = new int[][] {
                { 0, -1, 0, 0, 0},
                { -1, -1, -1, -1, -1},
                { 0, -1, 0, 7, 0},
                { 0, -1, 7, 0, 9},
                { 0, -1, 0, 9, 0}};

        int matrixVehicle3[][] = new int[][] {
                { -1, -1, -1, -1, -1},
                { -1, 0, 3, 5, 9},
                { -1, 3, 0, 7, 6},
                { -1, 5, 7, 0, 9},
                { -1, 9, 6, 9, 0}};

        Vehicle truck = new Truck(VehicleColor.BLACK, 12.5f, 1999, "MAN", null);
        Vehicle car = new Car(VehicleColor.RED, 7.0f, 2010, "Volvo", null);
        Vehicle drone = new Drone(VehicleColor.WHITE, 1.0f, 2015, "Drone", null);

        depot1.addVehicleToDepot(truck);
        depot1.addVehicleToDepot(car);
        depot2.addVehicleToDepot(drone);


        List<Depot> listOfDepots = new ArrayList<>();

        listOfDepots.add(depot1);
        listOfDepots.add(depot2);

        Problem problem = new Problem(listOfDepots, listOfClients);
        Solution solution = new Solution(problem);

        for(Tour tour : solution.getListOfTours()){
            System.out.println(tour);
        }
    }
}
