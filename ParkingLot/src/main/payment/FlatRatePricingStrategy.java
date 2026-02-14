package main.payment;

import java.time.LocalDateTime;

public class FlatRatePricingStrategy implements PricingStrategy {
    private double flatRate;

    public FlatRatePricingStrategy(double flatRate) {
        this.flatRate = flatRate;
    }

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        return flatRate;
    }
}