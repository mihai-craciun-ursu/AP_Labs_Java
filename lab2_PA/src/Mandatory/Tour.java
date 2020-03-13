package Mandatory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tour {
    private Vehicle vehicle;
    private List<Client> listOfClients = new ArrayList<>();

    public Tour(Vehicle vehicle, List<Client> listOfClients) {
        this.vehicle = vehicle;
        this.listOfClients = listOfClients;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }


    public List<Client> getListOfClients() {
        return listOfClients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return Objects.equals(vehicle, tour.vehicle) &&
                Objects.equals(listOfClients, tour.listOfClients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle, listOfClients);
    }

    @Override
    public String toString() {
        String listOfClientsString = "";

        for(Client client : listOfClients){
            listOfClientsString = listOfClientsString.concat(client.getName() + "->");
        }
        return vehicle.getProducerName() + " : " + vehicle.getDepot().getName() + "->" + listOfClientsString + vehicle.getDepot().getName();
    }
}
