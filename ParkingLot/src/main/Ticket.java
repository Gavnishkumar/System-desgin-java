package main;

import java.time.LocalDateTime;
import main.vehicle.Vehicle;

public class Ticket {
    private int ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double amount;

    public Ticket(int ticketId, Vehicle vehicle, ParkingSpot parkingSpot, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.amount = 0;
    }

    public void setExitTimeAndAmount(LocalDateTime exitTime, double amount) {
        this.exitTime = exitTime;
        this.amount = amount;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public double getAmount() {
        return amount;
    }

    public void printTicket() {
        System.out.println("========== PARKING TICKET ==========");
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Vehicle: " + vehicle.getLicensePlate());
        System.out.println("Vehicle Type: " + vehicle.getType());
        System.out.println("Spot ID: " + parkingSpot.getSpotNumber());
        System.out.println("Entry Time: " + entryTime);
        System.out.println("Exit Time: " + (exitTime != null ? exitTime : "Not exited"));
        System.out.println("Amount: $" + String.format("%.2f", amount));
        System.out.println("====================================");
    }
}