public class Ramp {
    private final GenericQueue<Vehicle> queue;

    public Ramp() {
        this.queue = new GenericQueue<>();
    }

    // Enqueues a vehicle
    public void enqueue(Vehicle vehicle) {
        queue.enqueue(vehicle);
    }

    // Dequeues a vehicle
    public Vehicle dequeue() {
        return queue.dequeue();
    }

    // Gets the vehicle at the head of the queue without dequeuing it.
    public Vehicle nextVehicle() {
        return queue.getHead();
    }

    // Gets the length of the queue
    public int getNumOfVehicles() {
        return queue.length;
    }
}
