package main.payment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HourlyPricingStrategy implements PricingStrategy {
    private double hourlyRate;

    public HourlyPricingStrategy(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
        if (minutes == 0) {
            minutes = 1;
        }
        long hours = (minutes + 59) / 60;
        return hours * hourlyRate;
    }
}

