package main;

import java.util.List;


public class SmartElevatorScheduler implements ElevatorSchedular {
    private List<Elevator> elevators;
    private int minFloor;
    private int maxFloor;

    public SmartElevatorScheduler(List<Elevator> elevators, int minFloor, int maxFloor) {
        this.elevators = elevators;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    @Override
    public Elevator selectElevator(int floor, Direction direction) {
        Elevator bestElevator = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (Elevator elevator : elevators) {
            if (elevator.isFull()) {
                continue;
            }

            double score = calculateElevatorScore(elevator, floor, direction);
            
            if (score > bestScore) {
                bestScore = score;
                bestElevator = elevator;
            }
        }

        if (bestElevator == null) {
            return selectLeastLoadedElevator();
        }

        return bestElevator;
    }


    private double calculateElevatorScore(Elevator elevator, int requestFloor, Direction direction) {
        double score = 0;

        int distance = Math.abs(elevator.getCurrentFloor() - requestFloor);
        int maxDistance = maxFloor - minFloor;
        double distanceScore = 10 * (1 - (double) distance / maxDistance);
        score += distanceScore * 0.5; // 50% weight

        Direction elevatorDir = elevator.getDirection();
        double directionScore = 0;
        
        if (elevatorDir == Direction.IDLE) {
            directionScore = 10;
        } else if (elevatorDir == direction) {
            directionScore = 8; 
        } else if (elevatorDir != direction) {
            directionScore = 2; 
        }
        score += directionScore * 0.3; 

        int maxCapacity = 10;
        double loadScore = 10 * (1 - (double) elevator.getCurrentPassengers() / maxCapacity);
        score += loadScore * 0.2; 

        return score;
    }

    private Elevator selectLeastLoadedElevator() {
        Elevator leastLoaded = elevators.get(0);
        for (Elevator elevator : elevators) {
            if (elevator.getPendingRequests() < leastLoaded.getPendingRequests()) {
                leastLoaded = elevator;
            }
        }
        return leastLoaded;
    }
}
