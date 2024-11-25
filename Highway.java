public class Highway {
    private final Lane leftLane;
    private final Lane rightLane;
    private final Ramp ramp;
    private final boolean hasOnRamp;
    private final double length; // Total length of the highway in feet
    double[] times;
    private double nextMerge;
    private double nextExitToOffRamp;
    private double nextExitLane1;
    private double nextExitLane2;
    public int index;

    // Constructor
    public Highway(double length, boolean hasOnRamp, int index) {
        this.leftLane = new Lane(length);
        this.rightLane = new Lane(length);
        this.ramp = new Ramp();
        this.hasOnRamp = hasOnRamp;
        this.length = length;
        this.index = index;
        times = new double[hasOnRamp ? 4 : 2];
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

    // Method to get the next vehicle at the head of the Left Lane queue without
    // dequeuing it
    public Vehicle nextVehicleLeftLane() {
        return leftLane.nextVehicle();
    }

    // Method to get the remaining space available in the right lane
    public double getRightLaneRemainingSpace() {
        return rightLane.getRemainingSpace();
    }

    // Method to enqueue a vehicle into the right lane if there's enough space
    public boolean enqueueRightLane(Vehicle vehicle) {
        return rightLane.enqueue(vehicle);
    }

    // Method to dequeue the first vehicle in the right lane
    public Vehicle dequeueRightLane() {
        return rightLane.dequeue();
    }

    // Method to get the number of vehicles currently in the right lane
    public int getRightLaneNumOfVehicles() {
        return rightLane.getNumOfVehicles();
    }

    // Method to get the next vehicle at the head of the Right Lane queue without
    // dequeuing it
    public Vehicle nextVehicleRightLane() {
        return rightLane.nextVehicle();
    }

    // Method to enqueue a vehicle into the Ramp
    public void enqueueRamp(Vehicle vehicle) {
        ramp.enqueue(vehicle);
    }

    // Method to dequeue the first vehicle on the Ramp
    public Vehicle dequeueRamp() {
        return ramp.dequeue();
    }

    // Method to get the next vehicle at the head of the Ramp queue without
    // dequeuing it
    public Vehicle nextVehicleRamp() {
        return ramp.nextVehicle();
    }

    // Method to get the number of vehicles currently on the ramp
    public int getRampNumOfVehicles() {
        return ramp.getNumOfVehicles();
    }

    // Method to set the next arrival
    public void setNextTimes(double[] nextTimes) {
        for (int i = 0; i < this.times.length; i++) {
            this.times[i] = nextTimes[i];
        }
    }

    // Method to get the next merge
    public double getNextMerge() {
        return nextMerge;
    }

    // Method to set the next merge
    public void setNextMerge(double nextMerge) {
        this.nextMerge = nextMerge;
    }

    // Method to get the next exit to the off-ramp
    public double getNextExitToOffRamp() {
        return nextExitToOffRamp;
    }

    // Method to set the next exit to the off-ramp
    public void setNextExitToOffRamp(double nextExitToOffRamp) {
        this.nextExitToOffRamp = nextExitToOffRamp;
    }

    // Method to get the next exit from lane one
    public double getNextExitLane1() {
        return nextExitLane1;
    }

    // Method to set the next exit from lane one
    public void setNextExitLane1(double nextExitLane1) {
        this.nextExitLane1 = nextExitLane1;
    }

    // Method to get the next exit from lane two
    public double getNextExitLane2() {
        return nextExitLane2;
    }

    // Method to set the next exit from lane two
    public void setNextExitLane2(double nextExitLane2) {
        this.nextExitLane2 = nextExitLane2;
    }
}