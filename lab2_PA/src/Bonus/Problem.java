package Bonus;

import java.util.ArrayList;
import java.util.List;

public class Problem {
    private List<Depot> listOfDepots = new ArrayList<>();
    private List<Client> listOfClients = new ArrayList<>();

    public Problem(List<Depot> listOfDepots, List<Client> listOfClients){
        this.listOfDepots = listOfDepots;
        this.listOfClients = listOfClients;
    }

    public List<Vehicle> getVehicles(){
        List<Vehicle> vehicles = new ArrayList<>();

        for(Depot depot : listOfDepots){
            for(Vehicle vehicle : depot.getListOfVehicle()){
                vehicles.add(vehicle);
            }
        }

        return vehicles;
    }

    public List<Depot> getListOfDepots() {
        return listOfDepots;
    }

    public List<Client> getListOfClients() {
        return listOfClients;
    }
}
