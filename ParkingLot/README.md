# Parking Lot Management System

A production-ready parking lot management system built with Java, demonstrating advanced object-oriented design principles, design patterns, and system design best practices.

## Overview

This project implements a scalable parking lot management system that handles vehicle parking, unparking, payment processing, and capacity management. It showcases clean architecture, thread-safe implementation, and extensible design patterns suitable for real-world applications.

## Features

### Core Functionality
- **Vehicle Parking**: Park vehicles (BIKE, CAR, TRUCK) in designated spots
- **Vehicle Unparking**: Release vehicles with automatic fee calculation
- **Unique Ticket System**: Generate tickets with entry/exit timestamps and parking fees
- **Payment Processing**: Flexible pricing strategies with automatic payment collection
- **Capacity Management**: Real-time tracking of available and occupied spots
- **Vehicle Tracking**: Prevent duplicate parking and locate parked vehicles

### System Features
- **Thread-Safe Design**: Volatile singleton pattern with double-checked locking
- **Flexible Pricing**: Multiple pricing strategy implementations
- **Enhanced Reporting**: Detailed status displays and payment tracking
- **Proper Exception Handling**: Custom exception classes for different scenarios
- **Extensible Architecture**: Easy to add new spot-finding strategies and pricing models

## Project Structure

```
ParkingLot/
├── src/
│   ├── App.java                          (Main entry point)
│   ├── main/
│   │   ├── Admin.java                    (Admin operations)
│   │   ├── Attendant.java                (Attendant operations)
│   │   ├── ParkingLot.java               (Core parking lot logic)
│   │   ├── ParkingSpot.java              (Individual parking spot)
│   │   ├── Ticket.java                   (Parking ticket)
│   │   ├── exception/
│   │   │   ├── ParkingException.java
│   │   │   ├── NoSpotAvailableException.java
│   │   │   ├── VehicleNotParkedException.java
│   │   │   └── InvalidPaymentException.java
│   │   ├── payment/
│   │   │   ├── PricingStrategy.java      (Interface)
│   │   │   ├── HourlyPricingStrategy.java
│   │   │   ├── MinuteBasedPricingStrategy.java
│   │   │   ├── FlatRatePricingStrategy.java
│   │   │   └── PaymentProcessor.java
│   │   ├── parkingstrategy/
│   │   │   ├── FindSpotStrategy.java     (Interface)
│   │   │   └── NormalStrategy.java       (FIFO implementation)
│   │   └── vehicle/
│   │       ├── Vehicle.java              (Abstract class)
│   │       ├── VehicleType.java          (Enum)
│   │       ├── Bike.java
│   │       ├── Car.java
│   │       └── Truck.java
│   └── bin/                              (Compiled classes)
└── README.md

```

## Getting Started

### Prerequisites
- Java 8 or higher
- Any terminal or command prompt

### Compilation

Navigate to the `src` directory and compile all Java files:

```bash
cd src
javac *.java main/*.java main/vehicle/*.java main/parkingstrategy/*.java main/exception/*.java main/payment/*.java
```

### Running the Application

Execute the main application:

```bash
java App
```

### Expected Output

```
========== PARKING LOT STATUS ==========
Total Spots: 5
Occupied Spots: 0
Available Spots: 5
========================================

========== PARKING TICKET ==========
Ticket ID: 1
Vehicle: BIKE1
Vehicle Type: BIKE
Spot ID: 1
Entry Time: 2026-02-14T15:17:02.144307300
Exit Time: Not exited
Amount: $0.00
====================================

[... more tickets ...]

========== PARKING LOT STATUS ==========
Total Spots: 5
Occupied Spots: 4
Available Spots: 1
========================================

========== PARKING TICKET ==========
Ticket ID: 1
Vehicle: BIKE1
Vehicle Type: BIKE
Spot ID: 1
Entry Time: 2026-02-14T15:17:02.144307300
Exit Time: 2026-02-14T15:17:04.186771600
Amount: $5.00
====================================
Payment collected: $5.00

========== PARKING LOT STATUS ==========
Total Spots: 5
Occupied Spots: 2
Available Spots: 3
========================================

Total Revenue: $10.00
```

## Architecture & Design Patterns

### Design Patterns Implemented

#### 1. **Singleton Pattern (Thread-Safe)**
```java
public class ParkingLot {
    private static volatile ParkingLot instance = null;
    
    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }
}
```
- Ensures only one parking lot instance exists
- Thread-safe for concurrent access
- Lazy initialization

#### 2. **Strategy Pattern**
Used for flexible spot-finding and pricing algorithms:

**Spot Finding Strategies:**
- `FindSpotStrategy` (Interface)
  - `NormalStrategy` - FIFO spot allocation

**Pricing Strategies:**
- `PricingStrategy` (Interface)
  - `HourlyPricingStrategy` - $X per hour
  - `MinuteBasedPricingStrategy` - $X per minute
  - `FlatRatePricingStrategy` - Fixed rate

#### 3. **Facade Pattern**
- `Admin` - Simplified interface for parking lot management
- `Attendant` - Simplified interface for parking operations

#### 4. **Template Method Pattern**
- `Vehicle` (Abstract) - Defines vehicle structure
- Concrete implementations: `Bike`, `Car`, `Truck`

## Core Classes & Responsibilities

### ParkingLot
**Responsibilities:**
- Manage parking spots collection
- Track parked vehicles by license plate
- Execute parking/unparking operations
- Calculate parking fees
- Maintain capacity metrics

**Key Methods:**
- `parkVehicle(Vehicle)` - Park a vehicle
- `unparkVehicle(String licensePlate)` - Unpark and calculate fee
- `getAvailableSpots()` - Get available spot count
- `getOccupiedSpots()` - Get occupied spot count

### ParkingSpot
**Responsibilities:**
- Store spot number and type
- Track occupancy status
- Validate vehicle type match

**Key Methods:**
- `parkVehicle(Vehicle)` - Park vehicle (throws exception if invalid)
- `unparkVehicle()` - Release spot
- `isOccupied()` - Check spot status

### Ticket
**Responsibilities:**
- Store parking transaction details
- Track entry and exit times
- Store parking fee amount
- Generate ticket report

**Key Fields:**
- `ticketId` - Unique identifier
- `vehicle` - Parked vehicle
- `parkingSpot` - Assigned spot
- `entryTime` - Entry timestamp
- `exitTime` - Exit timestamp
- `amount` - Parking fee

### Admin
**Responsibilities:**
- Configure parking lot
- Set pricing strategy
- Set spot-finding strategy
- Monitor parking status

**Key Methods:**
- `addParkingSpot(int spotNumber, VehicleType type)`
- `setPricingStrategy(PricingStrategy)`
- `setFindSpotStrategy(FindSpotStrategy)`
- `displayParkingStatus()`

### Attendant
**Responsibilities:**
- Park vehicles
- Unpark vehicles and process payment
- Collect revenue

**Key Methods:**
- `parkVehicle(Vehicle)` - Park vehicle
- `unparkVehicle(String licensePlate)` - Unpark and charge
- `getTotalPaymentCollected()` - Get revenue

## Usage Examples

### Basic Setup

```java
Admin admin = new Admin();
Attendant attendant = new Attendant();

admin.setPricingStrategy(new HourlyPricingStrategy(5.0));
admin.setFindSpotStrategy(new NormalStrategy());

admin.addParkingSpot(1, VehicleType.BIKE);
admin.addParkingSpot(2, VehicleType.CAR);
admin.addParkingSpot(3, VehicleType.TRUCK);
```

### Parking a Vehicle

```java
try {
    Ticket ticket = attendant.parkVehicle(new Car("ABC123"));
    ticket.printTicket();
} catch (Exception e) {
    System.out.println("Parking failed: " + e.getMessage());
}
```

### Unparking and Payment

```java
try {
    Ticket exitTicket = attendant.unparkVehicle("ABC123");
    exitTicket.printTicket();
    System.out.println("Amount charged: $" + exitTicket.getAmount());
} catch (Exception e) {
    System.out.println("Unparking failed: " + e.getMessage());
}
```

### Viewing Parking Status

```java
admin.displayParkingStatus();
```

### Revenue Tracking

```java
double totalRevenue = attendant.getTotalPaymentCollected();
System.out.println("Total Revenue: $" + totalRevenue);
```

## Pricing Strategies

### HourlyPricingStrategy
- Charges per hour with minimum 1-hour charge
- Partial hours rounded up
- Best for: Long-term parking facilities

```java
new HourlyPricingStrategy(5.0)  // $5 per hour
```

### MinuteBasedPricingStrategy
- Charges per minute of parking
- Best for: Short-term or precise billing

```java
new MinuteBasedPricingStrategy(0.1)  // $0.10 per minute
```

### FlatRatePricingStrategy
- Fixed charge regardless of duration
- Best for: Flat-rate parking structures

```java
new FlatRatePricingStrategy(10.0)  // $10 flat rate
```

## Exception Handling

The system provides custom exceptions for better error management:

| Exception | Cause |
|-----------|-------|
| `ParkingException` | Base exception for all parking errors |
| `NoSpotAvailableException` | No spot available for vehicle type |
| `VehicleNotParkedException` | Vehicle not found in parking lot |
| `InvalidPaymentException` | Invalid payment amount |

### Example Error Handling

```java
try {
    attendant.parkVehicle(vehicle);
} catch (NoSpotAvailableException e) {
    System.out.println("Parking full: " + e.getMessage());
} catch (VehicleNotParkedException e) {
    System.out.println("Vehicle not found: " + e.getMessage());
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
}
```

## Extending the System

### Adding a New Pricing Strategy

```java
public class DynamicPricingStrategy implements PricingStrategy {
    @Override
    public double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
        return calculateDynamicRate(minutes);
    }
    
    private double calculateDynamicRate(long minutes) {
        // Custom logic here
        return 0.0;
    }
}
```

Usage:
```java
admin.setPricingStrategy(new DynamicPricingStrategy());
```

### Adding a New Spot-Finding Strategy

```java
public class SmartStrategy implements FindSpotStrategy {
    @Override
    public ParkingSpot findSpot(VehicleType type, List<ParkingSpot> parkingSpots) {
        return parkingSpots.stream()
            .filter(spot -> !spot.isOccupied() && spot.getType() == type)
            .min(Comparator.comparing(ParkingSpot::getSpotNumber))
            .orElse(null);
    }
}
```

### Adding a New Vehicle Type

```java
public class Bus extends Vehicle {
    public Bus(String licensePlate) {
        super(VehicleType.BUS, licensePlate);
    }
}
```

## Thread Safety

The system is designed for concurrent access:

- **Volatile Singleton**: `ParkingLot` uses volatile and synchronized for thread-safe access
- **Thread-Safe Collections**: HashMap for vehicle tracking
- **Synchronized Operations**: Critical sections use locking

## Performance Considerations

| Operation | Time Complexity |
|-----------|-----------------|
| Park Vehicle | O(n) |
| Unpark Vehicle | O(1) |
| Get Status | O(n) |
| Find Available Spot | O(n) |

## Future Enhancements

1. **Multi-Level Parking**: Support basement/multi-floor lots
2. **Reservation System**: Pre-reserve parking spots
3. **Payment Methods**: Credit card, mobile wallet integration
4. **Real-Time Occupancy**: Display real-time availability
5. **Analytics Dashboard**: Track usage patterns and revenue
6. **Receipt Generation**: Digital/print receipts
7. **Seasonal Pricing**: Different rates for peak/off-peak hours
8. **Customer Portal**: Track parking history

## Code Quality Features

✅ **No Code Comments** - Self-documenting code  
✅ **Clean Architecture** - Proper separation of concerns  
✅ **SOLID Principles** - Applied throughout  
✅ **Type Safety** - Enum for vehicle types  
✅ **Exception Handling** - Custom exceptions  
✅ **Thread Safety** - Concurrent access ready  
✅ **Extensibility** - Strategy pattern for easy additions  

## Design Principles Applied

1. **Single Responsibility** - Each class has one clear purpose
2. **Open/Closed** - Open for extension, closed for modification
3. **Liskov Substitution** - Implementations can replace abstractions
4. **Interface Segregation** - Focused, minimal interfaces
5. **Dependency Inversion** - Depend on abstractions, not implementations

## Summary

This parking lot management system demonstrates:
- Professional software architecture
- Advanced design patterns
- Thread-safe implementation
- Flexible, extensible design
- Clean code principles
- Real-world problem solving

Perfect for portfolio, interviews, and as a foundation for larger systems.

---

**Version**: 1.0  
**Last Updated**: February 2026  
**Language**: Java 8+
