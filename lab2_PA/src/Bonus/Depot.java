package Bonus;

import java.util.ArrayList;
import java.util.List;

public class Depot {
    private List<Vehicle> listOfVehicle;
    private String name;

    public Depot(String name){
        this.name = name;
        listOfVehicle = new ArrayList<>();
    }

    public boolean addVehicleToDepot(Vehicle vehicle){
        for(Vehicle vehicleIterator : listOfVehicle){
            if(vehicle.equals(vehicleIterator)){
                return false;
            }
        }
        listOfVehicle.add(vehicle);
        vehicle.setDepot(this);
        return true;
    }

    public void removeVehicleFromDepot(Vehicle vehicle){
        listOfVehicle.remove(vehicle);
    }

    public String getName() {
        return name;
    }

    public List<Vehicle> getListOfVehicle() {
        return listOfVehicle;
    }

    @Override
    public String toString() {
        return "Depot{" +
                "listOfVehicle=" + listOfVehicle +
                ", name='" + name + '\'' +
                '}';
    }

}
