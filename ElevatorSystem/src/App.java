import main.Building;
import main.Direction;


public class App {
    public static void main(String[] args) {
        

        Building building = new Building(2, 0, 20);
        
        System.out.println("Building initialized with 2 elevators, 20 floors\n");

        System.out.println("--- Scenario 1: Multiple requests to upper floors ---");
        building.requestElevator(15, Direction.UP);
        building.requestElevator(8, Direction.UP);
        building.requestElevator(12, Direction.UP);
        building.requestElevator(5, Direction.DOWN);

        System.out.println("\n--- Building Status After Scenario 1 ---");
        building.printStatus();

        System.out.println("--- Scenario 2: Test floor validation ---");
        building.requestElevator(25, Direction.UP); // Invalid floor
        building.requestElevator(-1, Direction.DOWN); // Invalid floor

        System.out.println("\n--- Scenario 3: Mixed direction requests ---");
        building.requestElevator(10, Direction.UP);
        building.requestElevator(3, Direction.DOWN);
        building.requestElevator(18, Direction.UP);

        System.out.println("\n--- Final Building Status ---");
        building.printStatus();

       
    }
}
