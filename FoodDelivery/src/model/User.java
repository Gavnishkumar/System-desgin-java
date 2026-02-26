package model;
import java.util.ArrayList;
import java.util.List;
public class User{
    private String name;
    List<Items> cart;

    public User(String name){
        this.name = name;
        cart= new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Items> getCart() {
        return cart;
    }

    
}