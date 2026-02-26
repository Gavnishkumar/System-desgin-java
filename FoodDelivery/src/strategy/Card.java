package strategy;

public class Card implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing card payment of amount: " + amount);
        return true;
    }
    
}
