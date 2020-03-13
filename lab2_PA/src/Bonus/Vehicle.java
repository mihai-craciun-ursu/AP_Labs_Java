package Bonus;

import java.util.Objects;

public abstract class Vehicle {
    private VehicleColor color;
    private float consumption;
    private int year;
    private String producerName;
    private Depot depot;
    private int[][] matrixOfCost;

    public Vehicle(VehicleColor color, float consumption, int year, String producerName, int[][] matrixOfCost) {
        this.color = color;
        this.consumption = consumption;
        this.year = year;
        this.producerName = producerName;
        this.matrixOfCost = matrixOfCost;
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

    public void setDepot(Depot depot) {
        if(this.depot != null){
            this.depot.removeVehicleFromDepot(this);
            depot.addVehicleToDepot(this);
            this.depot = depot;
        }else{
            this.depot = depot;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Float.compare(vehicle.consumption, consumption) == 0 &&
                year == vehicle.year &&
                color == vehicle.color &&
                Objects.equals(producerName, vehicle.producerName) &&
                Objects.equals(depot, vehicle.depot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, consumption, year, producerName, depot);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                ", color=" + color +
                ", consumption=" + consumption +
                ", year=" + year +
                ", producerName='" + producerName + '\'' +
                ", depot=" + depot +
                '}';
    }
}
