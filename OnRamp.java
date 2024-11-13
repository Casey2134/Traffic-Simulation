public class OnRamp {
    private GenericQueue<Vehicle> queue;

    // Constructor
    public OnRamp() {
        queue = new GenericQueue<>();
    }

    // Enqueues a new vehicle
    public void enqueue(Vehicle vehicle) {
        queue.enqueue(vehicle);
    }

    // Dequeues the next car/bus in the queue
    public Vehicle dequeue() {
        return queue.dequeue();
    }

    // Gets the length of the queue
    public int getLength() {
        return queue.length;
    }
}