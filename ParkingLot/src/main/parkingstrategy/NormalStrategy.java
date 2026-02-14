package main.parkingstrategy;

import java.util.List;

import main.ParkingSpot;
import main.vehicle.VehicleType;
public class NormalStrategy implements FindSpotStrategy {
        @Override
        public ParkingSpot findSpot(VehicleType type,List<ParkingSpot> parkingSpots) {
            for (ParkingSpot spot : parkingSpots) {
                if (!spot.isOccupied() && spot.getType() == type) {
                    return spot;
                }
            }
            return null;
        }
}
