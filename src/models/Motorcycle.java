package models;

// Motorcycle.java
public class Motorcycle implements Vehicle {

    // identifiers
    private String plateNumber;
    private String maker;
    private String model;

    // usage here
    private double engineDisplacement;

    public Motorcycle(String plateNumber, String maker, String model, double engineDisplacement) {
        this.plateNumber = plateNumber;
        this.maker = maker;
        this.model = model;
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String getPlateNumber() {
        return plateNumber;
    }

    @Override
    public String getMaker() {
        return maker;
    }

    @Override
    public String getModel() {
        return model;
    }

    public double getEngineDisplacement() {
        return engineDisplacement;
    }

    @Override
    public double calculateTax() {
        return 0.10 * engineDisplacement;
    }

    @Override
    public String toString() {
        return plateNumber + " " + maker + " " + model;
    }
}