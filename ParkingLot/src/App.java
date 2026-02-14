import main.Admin;
import main.Attendant;
import main.Ticket;
import main.vehicle.VehicleType;
import main.payment.HourlyPricingStrategy;
import main.parkingstrategy.NormalStrategy;

public class App {
    public static void main(String[] args) throws Exception {
        Admin admin = new Admin();
        Attendant attendant = new Attendant();

        admin.setPricingStrategy(new HourlyPricingStrategy(5.0));
        admin.setFindSpotStrategy(new NormalStrategy());

        admin.addParkingSpot(1, VehicleType.BIKE);
        admin.addParkingSpot(2, VehicleType.BIKE);
        admin.addParkingSpot(3, VehicleType.CAR);
        admin.addParkingSpot(4, VehicleType.CAR);
        admin.addParkingSpot(5, VehicleType.TRUCK);

        admin.displayParkingStatus();

        Ticket t1 = attendant.parkVehicle(new main.vehicle.Bike("BIKE1"));
        t1.printTicket();

        Ticket t2 = attendant.parkVehicle(new main.vehicle.Car("CAR1"));
        t2.printTicket();

        Ticket t3 = attendant.parkVehicle(new main.vehicle.Car("CAR2"));
        t3.printTicket();

        Ticket t4 = attendant.parkVehicle(new main.vehicle.Car("CAR3"));
        t4.printTicket();

        Ticket t6 = attendant.parkVehicle(new main.vehicle.Truck("TRUCK1"));
        t6.printTicket();

        Ticket t5 = attendant.parkVehicle(new main.vehicle.Truck("TRUCK1"));
        t5.printTicket();

        admin.displayParkingStatus();

        Thread.sleep(2000);

        Ticket exitTicket1 = attendant.unparkVehicle("BIKE1");
        exitTicket1.printTicket();
        System.out.println("Payment collected: $" + String.format("%.2f", exitTicket1.getAmount()));

        Thread.sleep(1000);

        Ticket exitTicket2 = attendant.unparkVehicle("CAR1");
        exitTicket2.printTicket();
        System.out.println("Payment collected: $" + String.format("%.2f", exitTicket2.getAmount()));

        admin.displayParkingStatus();

        System.out.println("\nTotal Revenue: $" + String.format("%.2f", attendant.getTotalPaymentCollected()));
    }
}
