package Optional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution {

    private List<Tour> listOfTours = new ArrayList<>();
    private boolean isSolutionViable;

    public Solution(Problem problem){
        List<Client> tempClients = problem.getListOfClients();
        List<Depot> tempListOfDepots = problem.getListOfDepots();

        Collections.sort(tempClients, (o1, o2) -> {
            if (o1.getNumberOfOrder() < o2.getNumberOfOrder()) {
                return 1;
            } else if (o1.getNumberOfOrder() == o2.getNumberOfOrder()) {
                return 0;
            } else {
                return -1;
            }
        });

        for(Depot depot : tempListOfDepots){
            for(Vehicle vehicle : depot.getListOfVehicle()){
                int numberOfOrder = 1;
                Client orderClient = getFirstClientWithThisNumberOfOrder(numberOfOrder, tempClients);
                List<Client> clientsThatTakeThisCar = new ArrayList<>();
                while(orderClient != null){
                    clientsThatTakeThisCar.add(orderClient);
                    numberOfOrder = numberOfOrder + 1;
                    tempClients.remove(orderClient);
                    orderClient = getFirstClientWithThisNumberOfOrder(numberOfOrder, tempClients);
                }

                if(clientsThatTakeThisCar.size() > 0){
                    Tour tour = new Tour(vehicle, clientsThatTakeThisCar);
                    listOfTours.add(tour);
                }
            }
        }

    }

    private Client getFirstClientWithThisNumberOfOrder(int numberOfOrder, List<Client> clients){
        for(Client client : clients){
            if(client.getNumberOfOrder() == numberOfOrder) return client;
        }

        return null;
    }

    public List<Tour> getListOfTours() {
        return listOfTours;
    }
}
