package main;

import java.util.TreeSet;


public class Elevator {
    private static final int MAX_CAPACITY = 10; 
    private static final int DELAY_PER_FLOOR = 500; 

    private int id;
    private int currentFloor;
    private State state;
    private Direction direction;
    private RequestQueue requestQueue;
    private int currentPassengers; 

    private TreeSet<Integer> floorsToVisit;

    public Elevator(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
        this.state = State.IDLE;
        this.direction = Direction.IDLE;
        this.requestQueue = new RequestQueue();
        this.floorsToVisit = new TreeSet<>();
        this.currentPassengers = 0;
    }

   
    public boolean addRequest(Request request) {
        if (currentPassengers >= MAX_CAPACITY) {
            return false; 
        }

        floorsToVisit.add(request.getFloor());
        requestQueue.addRequest(request);
        
        if (state == State.IDLE) {
            state = State.MOVING;
        }
        
        return true;
    }


    public void operate() {
        while (floorsToVisit.size() > 0) {
            determineDirection();
            moveToNextFloor();
            serveFloorIfNeeded();
        }
        
        state = State.IDLE;
        direction = Direction.IDLE;
    }

    private void determineDirection() {
        if (floorsToVisit.isEmpty()) {
            direction = Direction.IDLE;
            return;
        }

        Integer maxFloor = floorsToVisit.last();
        Integer minFloor = floorsToVisit.first();

        if (direction == Direction.UP) {
            if (currentFloor < maxFloor) {
                return; 
            } else {
                direction = Direction.DOWN; 
            }
        }
        else if (direction == Direction.DOWN) {
            if (currentFloor > minFloor) {
                return; 
            } else {
                direction = Direction.UP; 
            }
        }
        else if (direction == Direction.IDLE) {
            int distUp = maxFloor - currentFloor;
            int distDown = currentFloor - minFloor;

            if (distUp <= distDown) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN;
            }
        }
    }

   
    private void moveToNextFloor() {
        if (direction == Direction.UP) {
            Integer nextFloor = floorsToVisit.higher(currentFloor);
            if (nextFloor != null) {
                currentFloor = nextFloor;
                logMovement("UP", currentFloor);
            }
        } else if (direction == Direction.DOWN) {
            Integer nextFloor = floorsToVisit.lower(currentFloor);
            if (nextFloor != null) {
                currentFloor = nextFloor;
                logMovement("DOWN", currentFloor);
            }
        }
        
        try {
            Thread.sleep(DELAY_PER_FLOOR);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    private void serveFloorIfNeeded() {
        if (floorsToVisit.contains(currentFloor)) {
            floorsToVisit.remove(currentFloor);
            currentPassengers++;
            System.out.println("  ✓ Elevator " + id + " at Floor " + currentFloor + 
                             " | Passengers: " + currentPassengers + "");
        }
    }

    private void logMovement(String dir, int floor) {
        System.out.println("Elevator " + id + " moving " + dir + " → Floor " + floor);
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public State getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getPendingRequests() {
        return floorsToVisit.size();
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public boolean isFull() {
        return currentPassengers >= MAX_CAPACITY;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", floor=" + currentFloor +
                ", state=" + state +
                ", direction=" + direction +
                ", passengers=" + currentPassengers +
                "/" + MAX_CAPACITY +
                ", pending=" + floorsToVisit.size() +
                '}';
    }
}
