public class Highway {
    private final Lane leftLane;
    private final Lane righLane;
    private final Ramp ramp;
    private final boolean hasOnRamp;
    private final double length; // Total length of the highway in feet

    // Constructor
    public Highway(double length, boolean hasOnRamp) {
        this.leftLane = new Lane(length);
        this.righLane = new Lane(length);
        this.ramp = new Ramp();
        this.hasOnRamp = hasOnRamp;
        this.length = length;
    }

    // Method to get the total length of the highway, both right and left lanes
    public double getLength() {
        return length;
    }

    // Method to determine if it has an on or off ramp
    public boolean hasOnRamp() {
        return hasOnRamp;
    }

    // Method to get the remaining space available in the left lane
    public double getLeftLaneRemainingSpace() {
        return leftLane.getRemainingSpace();
    }

    // Method to enqueue a vehicle into the left lane if there's enough space
    public boolean enqueueLeftLane(Vehicle vehicle) {
        return leftLane.enqueue(vehicle);
    }

    // Method to dequeue the first vehicle in the left lane
    public Vehicle dequeueLeftLane() {
        return leftLane.dequeue();
    }

    // Method to get the number of vehicles currently in the left lane
    public int getLeftLaneNumOfVehicles() {
        return leftLane.getNumOfVehicles();
    }

    // Method to get the next vehicle at the head of the Left Lane queue without dequeuing it
    public Vehicle nextVehicleLeftLane() {
        return leftLane.nextVehicle();
    }

    // Method to get the remaining space available in the right lane
    public double getRightLaneRemainingSpace() {
        return righLane.getRemainingSpace();
    }

    // Method to enqueue a vehicle into the right lane if there's enough space
    public boolean enqueueRightLane(Vehicle vehicle) {
        return righLane.enqueue(vehicle);
    }

    // Method to dequeue the first vehicle in the right lane
    public Vehicle dequeueRightLane() {
        return righLane.dequeue();
    }

    // Method to get the number of vehicles currently in the right lane
    public int getRightLaneNumOfVehicles() {
        return righLane.getNumOfVehicles();
    }

    // Method to get the next vehicle at the head of the Right Lane queue without dequeuing it
    public Vehicle nextVehicleRightLane() {
        return righLane.nextVehicle();
    }

    // Method to enqueue a vehicle into the Ramp
    public void enqueueRamp(Vehicle vehicle) {
        ramp.enqueue(vehicle);
    }

    // Method to dequeue the first vehicle on the Ramp
    public Vehicle dequeueRamp() {
        return ramp.dequeue();
    }

    // Method to get the next vehicle at the head of the Ramp queue without dequeuing it
    public Vehicle nextVehicleRamp() {
        return ramp.nextVehicle();
    }

    // Method to get the number of vehicles currently on the ramp
    public int getRampNumOfVehicles() {
        return ramp.getNumOfVehicles();
    }
}
