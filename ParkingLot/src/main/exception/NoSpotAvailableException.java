package main.exception;

public class NoSpotAvailableException extends ParkingException {
    public NoSpotAvailableException(String vehicleType) {
        super("No parking spot available for " + vehicleType);
    }
}
