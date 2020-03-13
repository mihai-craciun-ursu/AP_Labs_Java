package Optional;

public class Truck extends Vehicle {

    public Truck(VehicleColor color, float consumption, int year, String producerName) {
        super(color, consumption, year, producerName);
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
