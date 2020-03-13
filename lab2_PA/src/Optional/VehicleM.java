package Optional;

import java.util.Objects;

public class VehicleM {
    private VehicleType type;
    private VehicleColor color;
    private float consumption;
    private int year;
    private String producerName;
    private Depot depot;

    public VehicleM(VehicleType type, VehicleColor color, float consumption, int year, String producerName, Depot depot) {
        this.type = type;
        this.color = color;
        this.consumption = consumption;
        this.year = year;
        this.producerName = producerName;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public VehicleColor getColor() {
        return color;
    }

    public void setColor(VehicleColor color) {
        this.color = color;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public Depot getDepot() {
        return depot;
    }
/*
    public void setDepot(Depot depot) {
        if(this.depot != null){
            this.depot.removeVehicleFromDepot(this);
            depot.addVehicleToDepot(this);
        }else{
            depot.addVehicleToDepot(this);
        }
    }
 */


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleM vehicle = (VehicleM) o;
        return Float.compare(vehicle.consumption, consumption) == 0 &&
                year == vehicle.year &&
                type == vehicle.type &&
                color == vehicle.color &&
                Objects.equals(producerName, vehicle.producerName) &&
                Objects.equals(depot, vehicle.depot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color, consumption, year, producerName, depot);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "type=" + type +
                ", color=" + color +
                ", consumption=" + consumption +
                ", year=" + year +
                ", producerName='" + producerName + '\'' +
                ", depot=" + depot +
                '}';
    }
}
