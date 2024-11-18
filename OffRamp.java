public class OffRamp {
    private GenericQueue<Vehicle> queue;

    // Constructor
    public OffRamp() {
        queue = new GenericQueue<>();
    }

    // Enqueues a vehicle that has exited the highway
    public void enqueue(Vehicle vehicle) {
        queue.enqueue(vehicle);
    }

    // Dequeues a vehicle for processing
    public Vehicle dequeue() {
        return queue.dequeue();
    }

    // Gets the length of the queue
    public int getLength() {
        return queue.length;
    }
}

