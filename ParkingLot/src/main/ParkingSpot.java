package main;

import main.vehicle.Vehicle;
import main.vehicle.VehicleType;

public class ParkingSpot {
    private int spotNumber;
    private VehicleType type;
    private boolean isOccupied;

    public ParkingSpot(int spotNumber, VehicleType type) {
        this.spotNumber = spotNumber;
        this.type = type;
        this.isOccupied = false;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public VehicleType getType() {
        return type;
    }

    public void parkVehicle(Vehicle vehicle) throws Exception {
        if (isOccupied) {
            throw new Exception("Spot is already occupied");
        }
        if (vehicle.getType() != this.type) {
            throw new Exception("Vehicle type does not match spot type");
        }
        this.isOccupied = true;
    }

    public void unparkVehicle() {
        this.isOccupied = false;
    }
}
