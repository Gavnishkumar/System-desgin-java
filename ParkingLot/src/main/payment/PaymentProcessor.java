package main.payment;

public class PaymentProcessor {
    private double totalCollected;

    public PaymentProcessor() {
        this.totalCollected = 0;
    }

    public void processPayment(double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Invalid payment amount");
        }
        this.totalCollected += amount;
    }

    public double getTotalCollected() {
        return totalCollected;
    }
}
