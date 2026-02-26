import java.util.List;

import model.DeliveryPartner;
import model.Items;
import model.Order;
import model.Restaurant;
import model.User;
import service.OrderService;
import service.RestaurantService;
import service.DeliveryPartnerService;
public class App {
    public static void main(String[] args) throws Exception {
        RestaurantService restaurantService = RestaurantService.getInstance();
        OrderService orderService = OrderService.getInstance();
        DeliveryPartnerService deliveryPartnerService = DeliveryPartnerService.getInstance();
        Restaurant restaurant = new Restaurant("Pizza Place",1);
        Items pizza = new Items("Pepperoni Pizza", 10.0);
        Items burger = new Items("Cheese Burger", 8.0);
        restaurant.addItems(pizza);
        restaurant.addItems(burger);
        restaurantService.addRestaurant(restaurant);
    
        deliveryPartnerService.addDeliveryPartner(new DeliveryPartner("DHL"));

        User user= new User("John Doe");
        Restaurant searchedRestaurant = restaurantService.searchRestaurant("Pizza Place");

       
        List<Items> menu = searchedRestaurant.getMenu();
        
        List<Items> orderItems = List.of(menu.get(0), menu.get(1));
        Order order = new Order(user, searchedRestaurant, orderItems, new strategy.Card());
        orderService.placeOrder(order);
    }
}
