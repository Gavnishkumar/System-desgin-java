package main.exception;

public class VehicleNotParkedException extends ParkingException {
    public VehicleNotParkedException(String licensePlate) {
        super("Vehicle with license plate " + licensePlate + " is not parked");
    }
}
