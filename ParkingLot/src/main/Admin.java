package main;

import main.parkingstrategy.FindSpotStrategy;
import main.payment.PricingStrategy;
import main.vehicle.VehicleType;

public class Admin {
    private ParkingLot parkingLot;

    public Admin() {
        this.parkingLot = ParkingLot.getInstance();
    }

    public void addParkingSpot(int spotNumber, VehicleType type) {
        parkingLot.addParkingSpot(new ParkingSpot(spotNumber, type));
    }

    public void setFindSpotStrategy(FindSpotStrategy strategy) {
        parkingLot.setFindSpotStrategy(strategy);
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        parkingLot.setPricingStrategy(strategy);
    }

    public void displayParkingStatus() {
        System.out.println("\n========== PARKING LOT STATUS ==========");
        System.out.println("Total Spots: " + parkingLot.getParkingSpots().size());
        System.out.println("Occupied Spots: " + parkingLot.getOccupiedSpots());
        System.out.println("Available Spots: " + parkingLot.getAvailableSpots());
        System.out.println("========================================\n");
    }
}
