package Mandatory;

public class Client {

    private String name;
    private int numberOfOrder;

    public Client(String name, int numberOfOrder) {
        this.name = name;
        this.numberOfOrder = numberOfOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public boolean setNumberOfOrder(int numberOfOrder) {
        if(numberOfOrder > 0) {
            this.numberOfOrder = numberOfOrder;
            return true;
        }
        return false;
    }
}
