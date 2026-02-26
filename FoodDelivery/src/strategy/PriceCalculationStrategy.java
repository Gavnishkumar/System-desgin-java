package strategy;

import model.Order;
public interface PriceCalculationStrategy {
    double calculate(Order order);
} 