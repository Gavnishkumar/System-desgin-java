package main;

import java.util.ArrayList;
import java.util.List;


public class Building {
    private static final int MIN_FLOOR = 0;
    private static final int MAX_FLOOR = 20;

    private int minFloor;
    private int maxFloor;
    private List<Elevator> elevators;
    private ElevatorSchedular scheduler;

    public Building(int elevatorCount) {
        this(elevatorCount, MIN_FLOOR, MAX_FLOOR);
    }

    public Building(int elevatorCount, int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.elevators = new ArrayList<>();
        
        for (int i = 0; i < elevatorCount; i++) {
            elevators.add(new Elevator(i + 1, minFloor));
        }
        
        this.scheduler = new SmartElevatorScheduler(elevators, minFloor, maxFloor);
    }


    public boolean requestElevator(int floor, Direction direction) {
        if (!isValidFloor(floor)) {
            System.err.println("❌ Invalid floor: " + floor + 
                             " (Valid range: " + minFloor + " to " + maxFloor + ")");
            return false;
        }

        Request request = new Request(floor, direction);
        
        Elevator elevator = scheduler.selectElevator(floor, direction);
        
        if (elevator == null) {
            System.err.println("❌ No elevators available");
            return false;
        }

        boolean accepted = elevator.addRequest(request);
        
        if (!accepted) {
            System.err.println("⚠️  Elevator " + elevator.getId() + " at capacity, request queued");
            return false;
        }

        System.out.println("✓ Request dispatched to Elevator " + elevator.getId() + 
                         " for Floor " + floor + " (Direction: " + direction + ")");
        
        if (elevator.getState() == State.MOVING) {
            elevator.operate();
        }
        
        return true;
    }

    private boolean isValidFloor(int floor) {
        return floor >= minFloor && floor <= maxFloor;
    }


    public Elevator getElevator(int id) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == id) {
                return elevator;
            }
        }
        return null;
    }


    public void printStatus() {
        System.out.println("\n=== Building Status ===");
        for (Elevator elevator : elevators) {
            System.out.println(elevator);
        }
        System.out.println("=======================\n");
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }
}
