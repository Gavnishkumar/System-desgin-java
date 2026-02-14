package main.payment;

import java.time.LocalDateTime;

public interface PricingStrategy {
    double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime);
}
