package service;

import java.util.List;
import model.Restaurant;
import java.util.ArrayList;
public class RestaurantService {
    List<Restaurant> restaurants;
    static RestaurantService instance;
    private RestaurantService() {
        restaurants= new ArrayList<>();
    }
    public static RestaurantService getInstance() {
        if(instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public Restaurant searchRestaurant(String name) {
        for(Restaurant restaurant : restaurants) {
            if(restaurant.getName().equals(name)) {
                return restaurant;
            }
        }
        return null;
    }
}
