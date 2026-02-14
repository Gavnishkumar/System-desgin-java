package main.exception;

public class InvalidPaymentException extends ParkingException {
    public InvalidPaymentException(String message) {
        super(message);
    }
}
