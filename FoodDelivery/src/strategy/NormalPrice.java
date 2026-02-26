package strategy;

public class NormalPrice implements PriceCalculationStrategy {
    @Override
    public double calculate(model.Order order) {
        double total = 0;
        for(model.Items item : order.getItems()) {
            total += item.getPrice();
        }
        return total;
    }
    
}
