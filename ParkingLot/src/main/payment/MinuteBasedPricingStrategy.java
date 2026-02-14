package main.payment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MinuteBasedPricingStrategy implements PricingStrategy {
    private double ratePerMinute;

    public MinuteBasedPricingStrategy(double ratePerMinute) {
        this.ratePerMinute = ratePerMinute;
    }

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
        if (minutes == 0) {
            minutes = 1;
        }
        return minutes * ratePerMinute;
    }
}
