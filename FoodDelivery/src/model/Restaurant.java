package model;
import java.util.ArrayList;
import java.util.List;
public class Restaurant {
    int id;
    String name;
    List<Items> menu;
    public Restaurant(String name, int id){
        this.name = name;
        this.id = id;
        menu = new ArrayList<>();
        
    }

    public void addItems(Items item){
        menu.add(item);
    }

    public void removeItems(Items item){
        menu.remove(item);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Items> getMenu() {
        return menu;
    }

    
}
