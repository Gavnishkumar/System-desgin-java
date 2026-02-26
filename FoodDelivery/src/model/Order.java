package model;

import strategy.PaymentStrategy;
import java.util.List;
import strategy.PriceCalculationStrategy;
import strategy.NormalPrice;    
import service.DeliveryPartnerService;
public class Order {
    int id;
    static int idCounter = 0;
    User user;
    Restaurant restaurant;
    List<Items> items;
    Status status;
    PaymentStrategy payment;
    PriceCalculationStrategy calculationStrategy;
    DeliveryPartner deliveryPartner;
    DeliveryPartnerService deliveryPartnerService;
    public Order(User user, Restaurant restaurant, List<Items> items,PaymentStrategy payment) {
        this.id = ++idCounter;
        this.user = user;
        this.restaurant = restaurant;
        this.items = items;
        this.status = Status.DRAFT;
        this.calculationStrategy = new NormalPrice();
        this.deliveryPartnerService = DeliveryPartnerService.getInstance();
        this.payment = new strategy.Card();
    }

    private void runOrderEngine(){
        updateStatusToPreparing();
        updateStatusToPrepared();
        deliveryPartner = deliveryPartnerService.searchDeliveryPartner();
        System.out.println("Assigned delivery partner: " + deliveryPartner.getName());
        updateStatusToOutForDelivery();
        updateStatusToDelivered();
    }

    public void placeOrder() {
        System.out.println("Placing order " + id + " for user " + user.getName());
        boolean isPaymentSuccessful = payment.pay(calculationStrategy.calculate(this));
        if(isPaymentSuccessful) {
            this.status = Status.PLACED;
            runOrderEngine();
        } else {
            System.out.println("Payment failed for order " + id);
            updateStatusToCancelled();
        }
    }

    public void updateStatusToPreparing() {
        if(status != Status.PLACED) {
            System.out.println("Order " + id + " cannot be prepared as it is not in PLACED status.");
            return;
        }
        System.out.println("Order " + id + " is being prepared.");
        this.status = Status.PREPARING;
    }
    public void updateStatusToPrepared(){
        if(status != Status.PREPARING) {
            System.out.println("Order " + id + " cannot be marked as prepared as it is not in PREPARING status.");
            return;
        }
        System.out.println("Order " + id + " is prepared and ready for delivery.");  
        this.status = Status.PREPARED;
    }
    public void updateStatusToOutForDelivery() {
        if(status != Status.PREPARED) {
            System.out.println("Order " + id + " cannot be marked as out for delivery as it is not in PREPARED status.");
            return;
        }
        System.out.println("Order " + id + " is out for delivery.");
        this.status = Status.OUT_FOR_DELIVERY;
    }

    public void updateStatusToDelivered() {
        if(status != Status.OUT_FOR_DELIVERY) {
            System.out.println("Order " + id + " cannot be marked as delivered as it is not in OUT_FOR_DELIVERY status.");
            return;
        }
        System.out.println("Order " + id + " has been delivered by " + deliveryPartner.getName());
        this.status = Status.DELIVERED;
    }

    public void updateStatusToCancelled() {
        System.out.println("Order " + id + " has been cancelled.");
        this.status = Status.CANCELLED;
    }

    public void setPaymentStrategy(PaymentStrategy payment) {
        this.payment = payment;
    }

    public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<Items> getItems() {
        return items;
    }

    public Status getStatus() {
        return status;
    }

    public PaymentStrategy getPayment() {
        return payment;
    }

    public DeliveryPartner getDeliveryPartner() {
        return deliveryPartner;
    }

    
}
