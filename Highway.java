import java.util.LinkedList;

public class Highway {
    private LinkedList<Vehicle> lane;
    private double length; // Total length of the highway in feet
    private double remainingSpace; // Remaining space on the highway in feet

    // Constructor
    public Highway(double length) {
        this.length = length;
        this.remainingSpace = length;
        this.lane = new LinkedList<>();
    }

    // Method to get the total length of the highway
    public double getLength() {
        return this.length;
    }

    // Method to get the remaining space available on the highway
    public double getRemainingSpace() {
        return this.remainingSpace;
    }

    // Method to enqueue a vehicle onto the highway if there's enough space
    public boolean enqueue(Vehicle vehicle) {
        double vehicleLength = vehicle.getLength();
        if (vehicleLength <= remainingSpace) {
            lane.add(vehicle);
            remainingSpace -= vehicleLength;
            return true; // Vehicle successfully added to the highway
        }
        return false; // Not enough space for the vehicle
    }

    // Method to dequeue the first vehicle on the highway
    public Vehicle dequeue() {
        if (lane.isEmpty()) {
            return null;
        }
        Vehicle exitingVehicle = lane.removeFirst();
        remainingSpace += exitingVehicle.getLength(); // Free up space
        return exitingVehicle;
    }

    // Method to get the number of vehicles currently on the highway
    public int getNumOfVehicles() {
        return lane.size();
    }

    // Method to get the next vehicle at the head of the queue without dequeuing it
    public Vehicle nextVehicle() {
        if (lane.isEmpty()) {
            return null;
        }
        return lane.getFirst();
    }

    // Unit test for the Highway class
    public static void unitTest() {
        Highway highway = new Highway(500); // Highway of 500 feet
        Vehicle car1 = new Car(1, 5); // Car with 1 passenger
        Vehicle car2 = new Car(2, 3); // Car with 2 passengers
        Vehicle bus1 = new Bus(3, 15); // Bus with 15 passengers

        // Test: Add vehicles to the highway
        System.out.println("Enqueue Car 1: " + highway.enqueue(car1)); // Expected: true
        System.out.println("Enqueue Car 2: " + highway.enqueue(car2)); // Expected: true
        System.out.println("Enqueue Bus 1: " + highway.enqueue(bus1)); // Expected: true if space, false otherwise

        // Test: Get the number of vehicles on the highway
        System.out.println("Number of vehicles: " + highway.getNumOfVehicles()); // Expected: 2 or 3

        // Test: Check the next vehicle on the highway
        System.out.println("Next Vehicle: " + highway.nextVehicle()); // Expected: car1

        // Test: Dequeue a vehicle and check remaining space
        Vehicle exitedVehicle = highway.dequeue();
        System.out.println("Dequeued Vehicle: " + exitedVehicle);
        System.out.println("Remaining Space: " + highway.getRemainingSpace()); // Should reflect space after removing car1
    }
}