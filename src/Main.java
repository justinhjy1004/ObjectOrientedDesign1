import models.Car;
import models.Car.FuelType;
import models.MgmtSystem;
import models.Motorcycle;
import models.Person;
import models.Vehicle;

public class Main {
    public static void main(String[] args) {

        // create new management system
        MgmtSystem system = new MgmtSystem();

        System.out.println("1. ADDING 5 PERSONS");
        Person p1 = new Person("LIC001", "Alice", "Smith", "123 Main St");
        Person p2 = new Person("LIC002", "Bob", "Johnson", "456 Oak Ave");
        Person p3 = new Person("LIC003", "Charlie", "Brown", "789 Pine Rd");
        Person p4 = new Person("LIC004", "Diana", "Prince", "101 Maple Blvd");
        Person p5 = new Person("LIC005", "Evan", "Wright", "202 Elm St");

        system.addPerson(p1);
        system.addPerson(p2);
        system.addPerson(p3);
        system.addPerson(p4);
        system.addPerson(p5);

        System.out.println("\n");

        System.out.println("\n2. ADDING 20 VEHICLES OF DIFFERENT TYPES");
        // Motorcycles
        system.addVehicle(new Motorcycle("MOT001", "Honda", "CBR500R", 471));
        system.addVehicle(new Motorcycle("MOT002", "Yamaha", "MT-07", 689));
        system.addVehicle(new Motorcycle("MOT003", "Kawasaki", "Ninja 400", 399));
        system.addVehicle(new Motorcycle("MOT004", "Ducati", "Monster", 937));
        system.addVehicle(new Motorcycle("MOT005", "Suzuki", "SV650", 645));

        // Petrol Cars
        system.addVehicle(new Car("PET001", "Ford", "Mustang", 250, FuelType.PETROL));
        system.addVehicle(new Car("PET002", "Honda", "Civic", 130, FuelType.PETROL));
        system.addVehicle(new Car("PET003", "Volkswagen", "Golf", 140, FuelType.PETROL));
        system.addVehicle(new Car("PET004", "Mazda", "MX-5", 150, FuelType.PETROL));
        system.addVehicle(new Car("PET005", "Subaru", "Impreza", 160, FuelType.PETROL));

        // Diesel Cars
        system.addVehicle(new Car("DIE001", "BMW", "320d", 120, FuelType.DIESEL));
        system.addVehicle(new Car("DIE002", "Audi", "A4 TDI", 125, FuelType.DIESEL));
        system.addVehicle(new Car("DIE003", "Mercedes", "C220d", 115, FuelType.DIESEL));
        system.addVehicle(new Car("DIE004", "Volvo", "XC60 D4", 145, FuelType.DIESEL));
        system.addVehicle(new Car("DIE005", "Peugeot", "3008 BlueHDi", 110, FuelType.DIESEL));

        // Hybrid Cars
        system.addVehicle(new Car("HYB001", "Toyota", "Prius", 90, FuelType.HYBRID));
        system.addVehicle(new Car("HYB002", "Lexus", "ES 300h", 105, FuelType.HYBRID));
        system.addVehicle(new Car("HYB003", "Hyundai", "Ioniq", 95, FuelType.HYBRID));
        system.addVehicle(new Car("HYB004", "Kia", "Niro", 100, FuelType.HYBRID));
        system.addVehicle(new Car("HYB005", "Honda", "Accord Hybrid", 108, FuelType.HYBRID));

        System.out.println("\n");

        System.out.println("\n3. EDITING PERSONS & VEHICLES");

        // Editing a person
        Person alice = (Person) system.findPersonByLicense("LIC001");

        alice.setAddress("999 New Address St");
        alice.setSurname("Smith-Jones");

        // Editing a car
        Car prius = (Car) system.findVehicleByPlate("HYB001");
        prius.setCo2Emissions(85);

        System.out.println("\n 4. SELLING VEHICLES TO PERSONS (ASSIGNMENT)");
        system.assignOwner("MOT001", "LIC001");
        system.assignOwner("PET001", "LIC001");

        system.assignOwner("DIE001", "LIC002");
        system.assignOwner("HYB001", "LIC002");

        system.assignOwner("MOT002", "LIC003");
        system.assignOwner("PET002", "LIC003");
        system.assignOwner("DIE002", "LIC003");

        system.assignOwner("MOT005", "LIC005");

        System.out.println("Person to Vehicles Map:");
        System.out.println(system.getPersonToVehicles());

        System.out.println("\nVehicle to Owners Map:");
        System.out.println(system.getVehicleToOwners());

        System.out.println("\n");

        System.out.println("\n5. TRANSFERRING A VEHICLE");
        System.out.println("BEFORE TRANSFER:");
        System.out.println("Alice: " + system.getVehiclesByPerson("LIC001"));
        System.out.println("Bob:   " + system.getVehiclesByPerson("LIC002"));

        system.transferVehicle("DIE001", "LIC002", "LIC001");

        System.out.println("\nAFTER TRANSFER:");
        System.out.println("Alice: " + system.getVehiclesByPerson("LIC001"));
        System.out.println("Bob:   " + system.getVehiclesByPerson("LIC002"));

        System.out.println("\n6. DELETING A PERSON & A VEHICLE");
        System.out.println("BEFORE DELETION:");
        System.out.println("Evan: " + system.findPersonByLicense("LIC005"));

        system.deletePerson("LIC005");

        System.out.println("\nAFTER DELETION:");
        System.out.println("Evan: " + system.findPersonByLicense("LIC005"));

        System.out.println("\nList of all people:");
        System.out.println(system.getPersons());

        System.out.println("\n7. SEARCHING FOR A PERSON AND A VEHICLE");
        Person foundPerson = system.findPersonByLicense("LIC003");
        System.out.println("Person with license LIC003: " + (foundPerson != null ? foundPerson.toString() : "Not found"));

        Vehicle foundVehicle = system.findVehicleByPlate("HYB002");
        System.out.println("Car with Plate HYB002: " + (foundVehicle != null ? foundVehicle.getMaker() + " " + foundVehicle.getModel() : "Not found"));

        System.out.println("\n8. GENERATING ANNUAL TAX REPORT");
        system.printAnnualTaxReport();
    }
}