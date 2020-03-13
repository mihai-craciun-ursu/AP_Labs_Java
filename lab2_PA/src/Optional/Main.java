package Optional;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Depot depot1 = new Depot("D1");
        Depot depot2 = new Depot("D2");

        Vehicle truck = new Truck(VehicleColor.BLACK, 12.5f, 1999, "MAN");
        Vehicle car = new Car(VehicleColor.RED, 7.0f, 2010, "Volvo");
        Vehicle drone = new Drone(VehicleColor.WHITE, 1.0f, 2015, "Drone");

        depot1.addVehicleToDepot(truck);
        depot1.addVehicleToDepot(car);
        depot2.addVehicleToDepot(drone);

        Client client1 = new Client("C1", 1);
        Client client2 = new Client("C2", 1);
        Client client3 = new Client("C3", 2);
        Client client4 = new Client("C4", 2);
        Client client5 = new Client("C5", 3);

        List<Client> listOfClients = new ArrayList<>();

        listOfClients.add(client1);
        listOfClients.add(client2);
        listOfClients.add(client3);
        listOfClients.add(client5);
        listOfClients.add(client5);

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
