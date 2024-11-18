import java.util.LinkedList;

public class Highway {
    private LinkedList<Vehicle> lane;
    private double length;
    private double remainingSpace;

    // Constructor
    public Highway(double length) {
        this.length = length;
        this.remainingSpace = length;
        this.lane = new LinkedList<>();
    }

    public double getLength() {
        return this.length;
    }

    public double getRemainingSpace() {
        return this.remainingSpace;
    }

    public boolean enqueue(Vehicle vehicle) {
        double vehicleLength = vehicle.getVehicleLength();
        if (vehicleLength <= remainingSpace) {
            lane.add(vehicle);
            remainingSpace -= vehicleLength;
            return true;
        }
        return false; // No space for vehicle
    }

    public Vehicle dequeue() {
        if (lane.isEmpty()) {
            return null;
        }
        Vehicle exitingVehicle = lane.removeFirst();
        remainingSpace += exitingVehicle.getVehicleLength(); // Free up space
        return exitingVehicle;
    }

    public int getNumOfVehicles() {
        return lane.size();
    }

    public Vehicle nextVehicle() {
        if (lane.isEmpty()) {
            return null;
        }
        return lane.getFirst();
    }

}