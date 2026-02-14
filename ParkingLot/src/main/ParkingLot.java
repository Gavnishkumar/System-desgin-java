package main;

import java.time.LocalDateTime;
import java.util.*;

import main.parkingstrategy.FindSpotStrategy;
import main.vehicle.*;
import main.payment.PricingStrategy;

public class ParkingLot {
    private static volatile ParkingLot instance = null;
    private List<ParkingSpot> parkingSpots;
    private FindSpotStrategy findSpotStrategy;
    private PricingStrategy pricingStrategy;
    private Map<String, Ticket> parkedVehicles;
    private int ticketCounter;

    private ParkingLot() {
        parkingSpots = new ArrayList<>();
        parkedVehicles = new HashMap<>();
        ticketCounter = 0;
    }

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    public void setFindSpotStrategy(FindSpotStrategy strategy) {
        this.findSpotStrategy = strategy;
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    public Ticket parkVehicle(Vehicle vehicle) throws Exception {
        if (findSpotStrategy == null) {
            throw new Exception("Parking strategy not set");
        }
        if (pricingStrategy == null) {
            throw new Exception("Pricing strategy not set");
        }
        if (isVehicleAlreadyParked(vehicle.getLicensePlate())) {
            throw new Exception("Vehicle is already parked");
        }

        ParkingSpot spot = findSpotStrategy.findSpot(vehicle.getType(), parkingSpots);
        if (spot == null) {
            throw new Exception("No parking spot available for " + vehicle.getType());
        }

        spot.parkVehicle(vehicle);
        Ticket ticket = new Ticket(++ticketCounter, vehicle, spot, LocalDateTime.now());
        parkedVehicles.put(vehicle.getLicensePlate(), ticket);
        return ticket;
    }

    public Ticket unparkVehicle(String licensePlate) throws Exception {
        Ticket ticket = parkedVehicles.get(licensePlate);
        if (ticket == null) {
            throw new Exception("Vehicle with license plate " + licensePlate + " is not parked");
        }

        ParkingSpot spot = ticket.getParkingSpot();
        spot.unparkVehicle();
        LocalDateTime exitTime = LocalDateTime.now();
        double fee = pricingStrategy.calculateFee(ticket.getEntryTime(), exitTime);
        ticket.setExitTimeAndAmount(exitTime, fee);
        parkedVehicles.remove(licensePlate);
        return ticket;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public int getAvailableSpots() {
        return (int) parkingSpots.stream().filter(spot -> !spot.isOccupied()).count();
    }

    public int getOccupiedSpots() {
        return (int) parkingSpots.stream().filter(ParkingSpot::isOccupied).count();
    }

    private boolean isVehicleAlreadyParked(String licensePlate) {
        return parkedVehicles.containsKey(licensePlate);
    }
}

