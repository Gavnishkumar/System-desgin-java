package service;

import java.util.*;
import model.Order;
public class OrderService {
    List<Order> orders;

    static OrderService instance=null;
    private OrderService() {
            orders = new ArrayList<>();
    }
    public static OrderService getInstance() {
        if(instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public void placeOrder(Order order) {
        orders.add(order);
        order.placeOrder();
    }


}
