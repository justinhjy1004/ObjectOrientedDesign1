package models;

public class Car implements Vehicle {
    // Possible fuelt types of each car
    public enum FuelType {
        PETROL,
        DIESEL,
        HYBRID
    }

    // identifiers of car
    private String plateNumber;
    private String maker;
    private String model;
    private double co2Emissions;
    private FuelType fuelType;

    public Car(String plateNumber, String maker, String model, double co2Emissions, FuelType fuelType) {
        this.plateNumber = plateNumber;
        this.maker = maker;
        this.model = model;
        this.co2Emissions = co2Emissions;
        this.fuelType = fuelType;
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

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setCo2Emissions(double co2Emissions) {
        this.co2Emissions = co2Emissions;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getCo2Emissions() {
        return co2Emissions;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    @Override
    public String toString() {
        return plateNumber + " " + maker + " " + model;
    }

    @Override
    public double calculateTax() {

        switch (fuelType) {
            case PETROL:
                return 1.4 * co2Emissions;
            case DIESEL:
                return 1.8 * co2Emissions;
            case HYBRID:
                return 1.2 * co2Emissions;
            default:
                return 0;
        }
    }
}