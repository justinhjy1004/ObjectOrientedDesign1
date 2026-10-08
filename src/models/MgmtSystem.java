package models;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MgmtSystem {
    private List<Vehicle> vehicles;
    private List<Person> persons;

    private Map<String, List<Person>> vehicleToOwners;
    private Map<String, List<Vehicle>> personToVehicles;

    public MgmtSystem() {
        this.vehicles = new ArrayList<>();
        this.persons = new ArrayList<>();
        this.vehicleToOwners = new HashMap<>();
        this.personToVehicles = new HashMap<>();
    }

    public Map<String, List<Vehicle>> getPersonToVehicles() {
        return this.personToVehicles;
    }

    public Map<String, List<Person>> getVehicleToOwners() {
        return this.vehicleToOwners;
    }

    public void addPerson(Person person) {
        if (person != null && findPersonByLicense(person.getLicenseNumber()) == null) {
            persons.add(person);
            personToVehicles.put(person.getLicenseNumber(), new ArrayList<>());
        }
    }

    public void deletePerson(String licenseNumber) {
        Person person = findPersonByLicense(licenseNumber);
        if (person == null) {
            return;
        }

        List<Vehicle> ownedVehicles = personToVehicles.getOrDefault(licenseNumber, new ArrayList<>());
        for (Vehicle v : new ArrayList<>(ownedVehicles)) {
            List<Person> owners = vehicleToOwners.get(v.getPlateNumber());
            if (owners != null) {
                owners.remove(person);
            }
        }

        personToVehicles.remove(licenseNumber);
        persons.remove(person);
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null && findVehicleByPlate(vehicle.getPlateNumber()) == null) {
            vehicles.add(vehicle);
            vehicleToOwners.put(vehicle.getPlateNumber(), new ArrayList<>());
        }
    }

    public void deleteVehicle(String plateNumber) {
        Vehicle vehicle = findVehicleByPlate(plateNumber);
        if (vehicle == null) {
            return;
        }

        List<Person> owners = vehicleToOwners.getOrDefault(plateNumber, new ArrayList<>());
        for (Person p : new ArrayList<>(owners)) {
            List<Vehicle> pVehicles = personToVehicles.get(p.getLicenseNumber());
            if (pVehicles != null) {
                pVehicles.remove(vehicle);
            }
        }

        vehicleToOwners.remove(plateNumber);
        vehicles.remove(vehicle);
    }

    public Person findPersonByLicense(String licenseNumber) {
        for (Person p : this.persons) {
            if (p.getLicenseNumber().equalsIgnoreCase(licenseNumber)) {
                return p;
            }
        }
        return null;
    }

    public Vehicle findVehicleByPlate(String plateNumber) {
        for (Vehicle v : this.vehicles) {
            if (v.getPlateNumber().equalsIgnoreCase(plateNumber)) {
                return v;
            }
        }
        return null;
    }

    public List<Vehicle> getVehiclesByPerson(String licenseNumber) {
        List<Vehicle> list = personToVehicles.get(licenseNumber);
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    public List<Person> getOwnersByVehicle(String plateNumber) {
        List<Person> list = vehicleToOwners.get(plateNumber);
        return list != null ? Collections.unmodifiableList(list) : Collections.emptyList();
    }

    public void assignOwner(String plateNumber, String licenseNumber) {
        Vehicle vehicle = findVehicleByPlate(plateNumber);
        Person person = findPersonByLicense(licenseNumber);

        if (vehicle == null || person == null) {
            throw new IllegalArgumentException("Vehicle or Person not registered in the system.");
        }

        List<Person> owners = vehicleToOwners.computeIfAbsent(plateNumber, k -> new ArrayList<>());
        if (!owners.contains(person)) {
            owners.add(person);
        }

        List<Vehicle> userVehicles = personToVehicles.computeIfAbsent(licenseNumber, k -> new ArrayList<>());
        if (!userVehicles.contains(vehicle)) {
            userVehicles.add(vehicle);
        }
    }

    public void transferVehicle(String plateNumber, String fromLicense, String toLicense) {
        Vehicle vehicle = findVehicleByPlate(plateNumber);
        Person fromPerson = findPersonByLicense(fromLicense);
        Person toPerson = findPersonByLicense(toLicense);

        if (vehicle == null || fromPerson == null || toPerson == null) {
            throw new IllegalArgumentException("Vehicle or Persons not found.");
        }

        List<Person> owners = vehicleToOwners.get(plateNumber);
        if (owners != null) {
            owners.remove(fromPerson);
        }
        List<Vehicle> oldOwnerVehicles = personToVehicles.get(fromLicense);
        if (oldOwnerVehicles != null) {
            oldOwnerVehicles.remove(vehicle);
        }

        assignOwner(plateNumber, toLicense);
    }

    public void printAnnualTaxReport() {
        System.out.println(" ANNUAL VEHICLE TAX REPORT");
        for (Person person : persons) {
            List<Vehicle> userVehicles = personToVehicles.getOrDefault(person.getLicenseNumber(), Collections.emptyList());

            System.out.println("\nOwner: " + person.getName() + " " + person.getSurname()
                    + " (License: " + person.getLicenseNumber() + ")");

            if (userVehicles.isEmpty()) {
                System.out.println(" No vehicles registered.");
                System.out.println(" Total Tax: 0.00 €");
                continue;
            }

            double totalTax = 0.0;
            for (Vehicle vehicle : userVehicles) {
                double tax = vehicle.calculateTax();
                totalTax += tax;
                System.out.printf("  - [%s] %s %s | Tax: %.2f €%n",
                        vehicle.getPlateNumber(), vehicle.getMaker(), vehicle.getModel(), tax);
            }
            System.out.printf("  Total Tax for Owner: %.2f €%n", totalTax);
        }
    }

    public List<Vehicle> getVehicles() {
        return this.vehicles;
    }

    public List<Person> getPersons() {
        return this.persons;
    }
}