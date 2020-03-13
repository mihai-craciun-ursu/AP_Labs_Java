package Optional;

public class Drone extends Vehicle {

    public Drone(VehicleColor color, float consumption, int year, String producerName) {
        super(color, consumption, year, producerName);
    }

    @Override
    public String toString() {
        return "Drone{" +
                "color=" + this.getColor() +
                ", consumption=" + this.getConsumption() +
                ", year=" + this.getYear() +
                ", producerName='" + this.getProducerName();
    }
}
