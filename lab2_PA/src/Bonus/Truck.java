package Bonus;

public class Truck extends Vehicle {

    public Truck(VehicleColor color, float consumption, int year, String producerName, int[][] matrixOfCost) {
        super(color, consumption, year, producerName, matrixOfCost);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "color=" + this.getColor() +
                ", consumption=" + this.getConsumption() +
                ", year=" + this.getYear() +
                ", producerName='" + this.getProducerName();
    }
}
