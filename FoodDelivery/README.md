# Food Delivery System Design - Technical Analysis

## 📋 Overview
This is a simplified Food Delivery System demonstrating core design patterns and object-oriented principles. It manages users, restaurants, orders, delivery partners, and payment processing.

---

## 🏗️ Architecture & Design Patterns

### **1. Singleton Pattern** ✅ Implemented
**Used in:** `RestaurantService`, `OrderService`, `DeliveryPartnerService`

```java
public static OrderService getInstance() {
    if(instance == null) {
        instance = new OrderService();
    }
    return instance;
}
```

**Interview Assessment:** 
- ✅ **Correctly Used:** Ensures single instance for managing system-wide resources
- ⚠️ **Issue:** Thread-unsafe implementation (race condition in multi-threaded environment)

**Improvement Needed:**
```java
// Use double-checked locking or synchronized
public static synchronized OrderService getInstance() {
    if(instance == null) {
        instance = new OrderService();
    }
    return instance;
}
```

### **2. Strategy Pattern** ✅ Correctly Implemented
**Used in:** Payment strategies (`Card`, `UPI`) and Price calculation strategies

```java
PaymentStrategy payment = new Card();  // Can be swapped to new Upi()
boolean success = payment.pay(amount);
```

**Interview Assessment:** 
- ✅ **Correct:** Open/Closed Principle - easy to add new payment methods without modifying existing code
- ✅ **Extensible:** New payment types (ApplePay, GooglePay) can be added without touching Order class
- ✅ **Good Use:** Strategies are interchangeable at runtime

### **3. State Pattern** ⚠️ Partially Implemented
**Used in:** Order status management via `Status` enum

```java
public enum Status {
    DRAFT, PLACED, PREPARING, PREPARED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}
```

**Interview Assessment:**
- ✅ **Advantage:** Clear state transitions with validation
- ⚠️ **Issue:** Logic is embedded in Order class, not in separate state classes
- ⚠️ **Issue:** All state transitions happen synchronously in runOrderEngine() - unrealistic for real systems

**Better Approach (for interview):**
Create state classes: `OrderState`, `OrderPlacedState`, `OrderPreparingState`, etc.

### **4. Service Locator Pattern** ⚠️ Implemented
Services act as central repositories for entities (restaurants, orders, delivery partners).

**Interview Assessment:**
- ✅ **Good:** Centralizes access to resources
- ⚠️ **Issue:** Should use Dependency Injection instead of retrieving services via getInstance()
- ⚠️ **Issue:** Creates hidden dependencies

---

## 📊 Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                          USER                                    │
├─────────────────────────────────────────────────────────────────┤
│ - name: String                                                   │
│ - cart: List<Items>                                              │
└─────────────────────────────────────────────────────────────────┘
           │
           │ places
           ▼
┌─────────────────────────────────────────────────────────────────┐
│                          ORDER                                   │
├─────────────────────────────────────────────────────────────────┤
│ - id: int                                                        │
│ - user: User                                                     │
│ - restaurant: Restaurant                                         │
│ - items: List<Items>                                             │
│ - status: Status (DRAFT, PLACED, PREPARING, ...)                │
│ - payment: PaymentStrategy                                       │
│ - calculationStrategy: PriceCalculationStrategy                 │
│ - deliveryPartner: DeliveryPartner                              │
├─────────────────────────────────────────────────────────────────┤
│ + placeOrder(): void                                             │
│ + runOrderEngine(): void                                         │
│ + updateStatus*(): void                                          │
└─────────────────────────────────────────────────────────────────┘
           │                        │                        │
           ├────────────────────────┼────────────────────────┤
           ▼                        ▼                        ▼
┌──────────────────┐    ┌──────────────────┐    ┌──────────────────┐
│   RESTAURANT     │    │     ITEMS        │    │ DELIVERY PARTNER │
├──────────────────┤    ├──────────────────┤    ├──────────────────┤
│ - id: int        │    │ - name: String   │    │ - name: String   │
│ - name: String   │    │ - price: double  │    │ - status: String │
│ - menu: List     │    └──────────────────┘    │ - location: Loc  │
└──────────────────┘                            └──────────────────┘
                             │
                             │ ordered from
                             ▼
┌──────────────────────────────────────────────────────────────────────┐
│ Strategy Pattern - Payment Methods           Price Calculation      │
├──────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  ┌─────────────────────┐         ┌──────────────────────────┐      │
│  │ PaymentStrategy     │         │ PriceCalculationStrat.  │      │
│  │ (interface)         │         │ (interface)              │      │
│  ├─────────────────────┤         ├──────────────────────────┤      │
│  │ + pay(amount)       │         │ + calculate(order)       │      │
│  └─────────────────────┘         └──────────────────────────┘      │
│        △                                  △                         │
│        │                                  │                         │
│        ├─────────┬──────────┐             │                         │
│        │         │          │             │                         │
│      Card       UPI      [Future]    NormalPrice                    │
│  + pay()      + pay()                + calculate()                  │
│                                                                      │
│  Status: Can add PayPal, GooglePay, ApplePay without modifying Order
└──────────────────────────────────────────────────────────────────────┘

Services (Singleton Pattern):
┌──────────────────────┐  ┌──────────────────────┐  ┌───────────────────┐
│ RestaurantService    │  │  OrderService        │  │ DeliveryPartner   │
├──────────────────────┤  ├──────────────────────┤  │    Service        │
│ - restaurants: List  │  │ - orders: List       │  ├───────────────────┤
│ + getInstance()      │  │ + getInstance()      │  │ - partners: List  │
│ + addRestaurant()    │  │ + placeOrder()       │  │ + getInstance()   │
│ + searchRestaurant() │  │                      │  │ + addPartner()    │
└──────────────────────┘  └──────────────────────┘  │ + searchPartner() │
                                                     └───────────────────┘
```

---

## 🔄 Flow of Execution

### **Step 1: System Initialization**
```
App.main()
  ├─ RestaurantService.getInstance() [Singleton]
  ├─ OrderService.getInstance() [Singleton]
  ├─ DeliveryPartnerService.getInstance() [Singleton]
  └─ Create and register initial data
      ├─ new Restaurant("Pizza Place", 1)
      ├─ Add items to restaurant
      ├─ restaurantService.addRestaurant()
      └─ deliveryPartnerService.addDeliveryPartner()
```

### **Step 2: User Place Order**
```
1. User searches restaurant
   Restaurant searchedRestaurant = restaurantService.searchRestaurant("Pizza Place")
   
2. Select items from menu
   List<Items> orderItems = searchedRestaurant.getMenu()
   
3. Create Order with Payment Strategy
   Order order = new Order(user, restaurant, items, new Card())
   
4. Place order via OrderService
   orderService.placeOrder(order)
```

### **Step 3: Order Processing Engine** (runOrderEngine)
```
Order Status Flow:
┌────────┐
│ DRAFT  │  (Created)
└────┬───┘
     │ User pays via PaymentStrategy
     ▼
┌────────────┐
│ PLACED     │  (Payment successful)
└────┬───────┘
     │ runOrderEngine() starts
     ▼
┌────────────┐
│ PREPARING  │  (Restaurant prepares food)
└────┬───────┘
     │
     ▼
┌────────────┐
│ PREPARED   │  (Ready for delivery)
└────┬───────┘
     │ DeliveryPartner assigned
     ▼
┌──────────────────┐
│ OUT_FOR_DELIVERY │  (Partner picks up & delivers)
└────┬─────────────┘
     │
     ▼
┌────────────┐
│ DELIVERED  │  (Order completed)
└────────────┘

OR on payment failure:

┌────────────┐
│ CANCELLED  │  (Payment failed)
└────────────┘
```

### **Step 4: Payment Processing** (Strategy Pattern)
```
order.placeOrder()
  │
  ├─ calculationStrategy.calculate(order)  [PriceCalculationStrategy]
  │  └─ NormalPrice.calculate() → Sums all item prices
  │
  ├─ payment.pay(totalAmount)  [PaymentStrategy]
  │  └─ Card.pay() / UPI.pay() → Process payment
  │     └─ Returns boolean success/failure
  │
  └─ if (isPaymentSuccessful)
     └─ runOrderEngine()  [State transitions]
```

### **Complete Sequence Diagram**
```
User           OrderService       Order            PaymentStrategy    DeliveryPartnerService
 │                 │              │                      │                      │
 ├─ placeOrder()──>│              │                      │                      │
 │                 ├─ add Order──>│                      │                      │
 │                 ├──────────────>│ placeOrder()         │                      │
 │                 │              ├─ calculate()─ ────(PriceCalcStrat)         │
 │                 │              ├─ pay()───────────────────────>│            │
 │                 │              │<──── return true/false ────────│            │
 │                 │              ├─ if success                     │            │
 │                 │              │  └─ runOrderEngine()            │            │
 │                 │              │     ├─ update to PREPARING      │            │
 │                 │              │     ├─ update to PREPARED       │            │
 │                 │              │     ├─ search Delivery Partner──────────────>│
 │                 │              │     │<──── return DeliveryPartner ───────────│
 │                 │              │     ├─ assign partner           │            │
 │                 │              │     ├─ OUT_FOR_DELIVERY         │            │
 │                 │              │     └─ DELIVERED               │            │
 │                 │              │                                 │            │
```

---

## ✅ Key Features

- **Singleton Pattern:** Services manage single instances of restaurants, orders, and delivery partners
- **Strategy Pattern:** Flexible payment methods (Card, UPI) and price calculations
- **State Management:** Orders progress through well-defined statuses (DRAFT → PLACED → PREPARING → PREPARED → OUT_FOR_DELIVERY → DELIVERED)
- **Clear Separation:** Model, Service, and Strategy layers for organized architecture

---

## 🚀 How to Run

```bash
cd src
javac App.java
java App
```

---

## 📚 Design Patterns Explained

1. **Singleton Pattern:** RestaurantService, OrderService, and DeliveryPartnerService use Singleton to ensure only one instance manages resources
2. **Strategy Pattern:** Payment methods (Card, UPI) and price calculations (NormalPrice) use strategy pattern for flexible algorithm selection
3. **State Pattern:** Status enum manages order lifecycle through well-defined states
4. **Service Layer:** Services act as repositories managing model objects and business logic

---

## 📁 Folder Structure

```
FoodDelivery/
├── src/
│   ├── App.java                    # Entry point
│   ├── model/                      # Domain models
│   │   ├── User.java
│   │   ├── Order.java
│   │   ├── Restaurant.java
│   │   ├── DeliveryPartner.java
│   │   ├── Items.java
│   │   └── Status.java (Enum)
│   ├── service/                    # Business logic (Singleton)
│   │   ├── OrderService.java
│   │   ├── RestaurantService.java
│   │   └── DeliveryPartnerService.java
│   └── strategy/                   # Strategy Pattern implementations
│       ├── PaymentStrategy.java    (Interface)
│       ├── Card.java
│       ├── UPI.java
│       ├── PriceCalculationStrategy.java (Interface)
│       └── NormalPrice.java
├── bin/                            # Compiled output
└── README.md                       # This file
```

---

**Last Updated:** February 26, 2026
**Design Pattern Score (Interview):** 7/10 - Good fundamentals, but needs improvements in thread-safety and single responsibility
