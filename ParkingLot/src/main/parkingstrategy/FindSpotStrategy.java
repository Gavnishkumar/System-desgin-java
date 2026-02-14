package main.parkingstrategy;

import java.util.List;

import main.ParkingSpot;
import main.vehicle.VehicleType;

public interface FindSpotStrategy {
    ParkingSpot findSpot(VehicleType type,List<ParkingSpot> parkingSpots);
}
