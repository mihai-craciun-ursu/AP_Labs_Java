package Bonus;

public class Car extends Vehicle {

    public Car(VehicleColor color, float consumption, int year, String producerName, int[][] matrixOfCost) {
        super(color, consumption, year, producerName, matrixOfCost);
    }

    @Override
    public String toString() {
        return "Car{" +
                "color=" + this.getColor() +
                ", consumption=" + this.getConsumption() +
                ", year=" + this.getYear() +
                ", producerName='" + this.getProducerName();
    }
}
