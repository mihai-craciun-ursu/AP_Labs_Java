package Optional;

public class Car extends Vehicle {

    public Car(VehicleColor color, float consumption, int year, String producerName) {
        super(color, consumption, year, producerName);
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
