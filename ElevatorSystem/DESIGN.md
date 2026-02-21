# Elevator System - Design Documentation

## Overview

This is an **interview-ready elevator system design** that demonstrates:
- **SOLID principles** (Single Responsibility, Open/Closed)
- **Design Patterns** (Strategy, Factory, State)
- **Optimal Algorithms** (SCAN Algorithm)
- **Real-world Constraints** (Capacity, Floor Validation)
- **Clean Code** (Well-documented, maintainable)

---

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         Building                                │
├─────────────────────────────────────────────────────────────────┤
│ - minFloor: int                                                 │
│ - maxFloor: int                                                 │
│ - elevators: List<Elevator>                                     │
│ - scheduler: ElevatorSchedular                                  │
├─────────────────────────────────────────────────────────────────┤
│ + requestElevator(floor, direction): boolean                   │
│ + printStatus(): void                                          │
│ - isValidFloor(floor): boolean                                 │
└─────────────────────────────────────────────────────────────────┘
           │
           │ "manages"
           ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Elevator                                 │
├─────────────────────────────────────────────────────────────────┤
│ - id: int                                                       │
│ - currentFloor: int                                             │
│ - state: State                                                  │
│ - direction: Direction                                          │
│ - currentPassengers: int                                        │
│ - floorsToVisit: TreeSet<Integer>                               │
│ - requestQueue: RequestQueue                                    │
├─────────────────────────────────────────────────────────────────┤
│ + addRequest(request): boolean                                  │
│ + operate(): void          ◄─── SCAN Algorithm                  │
│ - determineDirection(): void                                    │
│ - moveToNextFloor(): void                                       │
│ - serveFloorIfNeeded(): void                                    │
│ + getPendingRequests(): int                                     │
│ + isFull(): boolean                                             │
└─────────────────────────────────────────────────────────────────┘
           │
           │ "uses"
           ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Request                                    │
├─────────────────────────────────────────────────────────────────┤
│ - floor: int                                                    │
│ - direction: Direction                                          │
│ - timestamp: long                                               │
├─────────────────────────────────────────────────────────────────┤
│ + getFloor(): int                                               │
│ + getDirection(): Direction                                     │
│ + getTimestamp(): long                                          │
└─────────────────────────────────────────────────────────────────┘

           │
           │ "contains"
           ▼
┌─────────────────────────────────────────────────────────────────┐
│                    RequestQueue                                 │
├─────────────────────────────────────────────────────────────────┤
│ - requests: Queue<Request>                                      │
├─────────────────────────────────────────────────────────────────┤
│ + addRequest(request): void                                     │
│ + peekNextRequest(): Request                                    │
│ + pollNextRequest(): Request                                    │
│ + hasRequests(): boolean                                        │
└─────────────────────────────────────────────────────────────────┘

           │
           │ "implements"
           ▼
┌─────────────────────────────────────────────────────────────────┐
│          <<Interface>> ElevatorSchedular                        │
├─────────────────────────────────────────────────────────────────┤
│ + selectElevator(floor, direction): Elevator                    │
└─────────────────────────────────────────────────────────────────┘
           │
           │ "implemented by"
           ▼
┌─────────────────────────────────────────────────────────────────┐
│              SmartElevatorScheduler                             │
├─────────────────────────────────────────────────────────────────┤
│ - elevators: List<Elevator>                                     │
│ - minFloor: int                                                 │
│ - maxFloor: int                                                 │
├─────────────────────────────────────────────────────────────────┤
│ + selectElevator(floor, direction): Elevator                    │
│ - calculateElevatorScore(...): double                           │
│ - selectLeastLoadedElevator(): Elevator                         │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│           <<Enum>> State        <<Enum>> Direction              │
├─────────────────────────────────────────────────────────────────┤
│ - IDLE                          - UP                            │
│ - MOVING                        - DOWN                          │
│ - MAINTENANCE                   - IDLE                          │
└─────────────────────────────────────────────────────────────────┘
```

---

## Design Patterns Used

### 1. **Strategy Pattern**
- `ElevatorSchedular` (Interface) - Defines scheduling algorithm contract
- `SmartElevatorScheduler` (Implementation) - Concrete scheduling strategy
- **Benefit**: Easy to swap scheduling algorithms without changing `Building` class

### 2. **State Pattern**
- `State` enum - Tracks elevator status (IDLE, MOVING, MAINTENANCE)
- `Direction` enum - Tracks movement direction
- **Benefit**: Clear state management, prevents invalid state transitions

### 3. **Encapsulation**
- `Request` class - Encapsulates request details
- `RequestQueue` class - Manages request ordering with FIFO
- **Benefit**: Maintainability and separation of concerns

---

## Key Algorithms

### SCAN Algorithm (Elevator Scheduling)
```
1. Move UP to the highest requested floor
2. Then move DOWN to the lowest requested floor
3. Repeat until all requests are served
```

**Why SCAN is superior:**
- ✓ Minimizes total travel distance
- ✓ Prevents starvation (all requests will be served)
- ✓ More efficient than FCFS (First Come First Served)
- ✓ Better than SSTF (Shortest Seek Time First)

### SmartElevatorScheduler Scoring
```
Total Score = (Distance Score × 0.5) + (Direction Score × 0.3) + (Load Score × 0.2)

Distance Score (0-10):   How close the elevator is
Direction Score (0-10):  How aligned the direction is
Load Score (0-10):       How much capacity is available
```

**Priority:**
1. Capacity (skip full elevators)
2. Distance (prefer closer)
3. Direction (prefer same direction)
4. Load (prefer less busy)

---

## Implementation Details

### Elevator Features
- **Capacity Management**: Max 10 passengers per elevator
- **SCAN Movement**: Optimal floor traversal
- **Realistic Timing**: 500ms delay between floors
- **State Tracking**: Current floor, direction, passenger count

### Building Features
- **Floor Validation**: Enforces min/max floor bounds (0-20)
- **Smart Dispatching**: Selects best elevator for each request
- **Request Handling**: Returns success/failure for each request
- **Dynamic Configuration**: Adjustable floor range and elevator count

### Request Queue
- **FIFO Ordering**: Fair request processing
- **Timestamp Tracking**: Could extend for priority scheduling
- **Load Balancing**: Distributed across available elevators

---

## Design Improvements Over Original

| Aspect | Original | Improved |
|--------|----------|----------|
| **Movement** | Blocks with while loop | Non-blocking SCAN algorithm |
| **Scheduling** | Naive distance only | Smart multi-factor scoring |
| **Direction** | Ignores direction | Direction-aware selection |
| **Capacity** | Unlimited | 10 passenger limit |
| **Validation** | None | Floor bounds checking |
| **Code Quality** | Poor documentation | Well-commented, production-ready |
| **Error Handling** | None | Proper validation & feedback |
| **Reusability** | Tight coupling | Loose coupling via interfaces |

---

## Usage Example

```java
// Create building with 2 elevators, floors 0-20
Building building = new Building(2, 0, 20);

// Request elevator to floor 15, going UP
boolean success = building.requestElevator(15, Direction.UP);

// Check building status
building.printStatus();

// Request to invalid floor returns false
building.requestElevator(25, Direction.UP); // Floor out of range
```

---

## Complexity Analysis

### Time Complexity
- **Request Submission**: O(n) where n = number of elevators
  - Scheduler evaluates each elevator
- **Elevator Operation**: O(k log k) where k = number of requests
  - TreeSet operations for floor management
- **Per Floor Traversal**: O(1) amortized

### Space Complexity
- **Elevator**: O(k) for pending floor requests
- **Building**: O(n + m) where n=elevators, m=total requests

---

## Extension Points

For a production system, consider:

1. **Multi-threading**: Process requests concurrently
2. **Priority Queues**: VIP/emergency buttons
3. **Maintenance Mode**: Out-of-service elevators
4. **Weight Sensors**: Actual capacity limits
5. **Door Management**: Opening/closing simulation
6. **Energy Optimization**: Idle strategies
7. **Monitoring**: Request fulfillment metrics
8. **Load Balancing**: Predictive algorithms
9. **Emergency Handling**: Power failure scenarios
10. **Logging**: Audit trail of all operations

---

## Interview Talking Points

1. **Problem Understanding**: Clarified requirements (floors, capacity, concurrent requests)
2. **Trade-offs**: SCAN vs FCFS vs SSTF - chose SCAN for optimality
3. **Scalability**: TreeSet for efficient floor management, O(log k) operations
4. **Design Patterns**: Strategy for swappable algorithms, State for clear management
5. **Real-world Thinking**: Validation, capacity, timing, error handling
6. **Algorithm Optimization**: Multi-factor scoring vs simple distance
7. **Edge Cases**: Full elevators, invalid floors, concurrent requests
8. **Code Quality**: Documentation, maintainability, testing concerns

---

## Testing Scenarios

### Test Case 1: Valid Requests
```java
building.requestElevator(5, Direction.UP);
building.requestElevator(10, Direction.UP);
// Both requests served, elevator moves 0→5→10
```

### Test Case 2: Invalid Floors
```java
building.requestElevator(25, Direction.UP);  // Out of range
building.requestElevator(-1, Direction.DOWN); // Negative
// Both return false with error message
```

### Test Case 3: Multiple Elevators
```java
building.requestElevator(3, Direction.UP);   // Elevator 1
building.requestElevator(15, Direction.UP);  // Elevator 2 (farther)
// Smart scheduler distributes load
```

### Test Case 4: Direction Awareness
```java
building.requestElevator(12, Direction.UP);  // Going up
building.requestElevator(8, Direction.DOWN); // Same elevator likely
// Scheduler prefers elevators in same direction
```

---

## Files Structure

```
src/
├── App.java                          # Demo application
└── main/
    ├── Building.java                 # Building manager
    ├── Elevator.java                 # Elevator with SCAN algorithm
    ├── Request.java                  # Request encapsulation
    ├── RequestQueue.java             # FIFO queue for requests
    ├── ElevatorSchedular.java        # Scheduling interface
    ├── SmartElevatorScheduler.java   # Smart scheduling implementation
    ├── Direction.java                # Movement direction enum
    └── State.java                    # Elevator state enum
```

---

**Design Pattern Maturity**: ⭐⭐⭐⭐⭐ (SDE 1 Interview Ready)
