package main.vehicle;

public class Vehicle {
    private VehicleType type;
    private String licensePlate;
    public Vehicle(VehicleType type, String licensePlate) {
        this.type = type;
        this.licensePlate = licensePlate;
    }
    public VehicleType getType() {
        return type;
    }
    public void setType(VehicleType type) {
        this.type = type;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }   
}
