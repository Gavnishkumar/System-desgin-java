package main;

import main.vehicle.Vehicle;
import main.payment.PaymentProcessor;

public class Attendant {
    private ParkingLot parkingLot;
    private PaymentProcessor paymentProcessor;

    public Attendant() {
        this.parkingLot = ParkingLot.getInstance();
        this.paymentProcessor = new PaymentProcessor();
    }

    public Ticket parkVehicle(Vehicle vehicle) throws Exception {
        return parkingLot.parkVehicle(vehicle);
    }

    public Ticket unparkVehicle(String licensePlate) throws Exception {
        Ticket ticket = parkingLot.unparkVehicle(licensePlate);
        paymentProcessor.processPayment(ticket.getAmount());
        return ticket;
    }

    public double getTotalPaymentCollected() {
        return paymentProcessor.getTotalCollected();
    }
}
