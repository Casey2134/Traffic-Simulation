public class Lane {
    private GenericQueue<Vehicle> lane;
    private double length; // Total length of the lane in feet
    private double remainingSpace; // Remaining space in the lane in feet

    // Constructor
    public Lane(double length) {
        this.length = length;
        this.remainingSpace = length;
        this.lane = new GenericQueue<Vehicle>();
    }

    // Method to get the total length of the lane
    public double getLength() {
        return this.length;
    }

    // Method to get the remaining space available in the lane
    public double getRemainingSpace() {
        return this.remainingSpace;
    }

    // Method to enqueue a vehicle into the lane if there's enough space
    public boolean enqueue(Vehicle vehicle) {
        double vehicleLength = vehicle.getVehicleLength();
        if (vehicleLength <= remainingSpace) {
            lane.enqueue(vehicle);
            remainingSpace -= vehicleLength;
            return true; // Vehicle successfully added to the lane
        }
        return false; // Not enough space for the vehicle
    }

    // Method to dequeue the first vehicle in the lane
    public Vehicle dequeue() {
        if (lane.isEmpty()) {
            return null;
        }
        Vehicle exitingVehicle = lane.dequeue();
        remainingSpace += exitingVehicle.getVehicleLength(); // Free up space
        return exitingVehicle;
    }

    // Method to get the number of vehicles currently in the lane
    public int getNumOfVehicles() {
        return lane.length;
    }

    // Method to get the next vehicle at the head of the queue without dequeuing it
    public Vehicle nextVehicle() {
        if (lane.isEmpty()) {
            return null;
        }
        return lane.getHead();
    }

    // Unit test for the Lane class
    /*
     * public static void unitTest() {
     * Lane lane = new Lane(500); // Highway of 500 feet
     * Vehicle car1 = new Car(1, 5, 1, 1); // Car with 1 passenger
     * Vehicle car2 = new Car(2, 3, 1, 1); // Car with 2 passengers
     * Vehicle bus1 = new Bus(3, 15, 1, 1); // Bus with 15 passengers
     * 
     * // Test: Add vehicles to the lane
     * System.out.println("Enqueue Car 1: " + lane.enqueue(car1)); // Expected: true
     * System.out.println("Enqueue Car 2: " + lane.enqueue(car2)); // Expected: true
     * System.out.println("Enqueue Bus 1: " + lane.enqueue(bus1)); // Expected: true
     * if space, false otherwise
     * 
     * // Test: Get the number of vehicles in the lane
     * System.out.println("Number of vehicles: " + lane.getNumOfVehicles()); //
     * Expected: 2 or 3
     * 
     * // Test: Check the next vehicle in the lane
     * System.out.println("Next Vehicle: " + lane.nextVehicle()); // Expected: car1
     * 
     * // Test: Dequeue a vehicle and check remaining space
     * Vehicle exitedVehicle = lane.dequeue();
     * System.out.println("Dequeued Vehicle: " + exitedVehicle);
     * System.out.println("Remaining Space: " + lane.getRemainingSpace()); // Should
     * reflect space after removing
     * // car1
     * }
     */
}