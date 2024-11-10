import java.util.Random;

public class Arrival {
    // Decides When Vehicles Are Created
    private Exponential e;
    // Decides How Many Passengers Vehicles Have
    private Normal n;
    // Decides What Type of Vehicle
    private Random random;
    // Decides When Vehicle is Created
    private double nextVehicle;
    // Basic Constructor
    public Arrival(){
        this(1);
    }
    // Custom Constructor (For Changing Arrival Rates)
    public Arrival(double arrivalRate){
        e = new Exponential(arrivalRate);
        random = new Random();
        nextVehicle = e.sample();
    }
    // Creation Method For Vehicle
    public Vehicle nextVehicle(double currentTime){
        Vehicle vehicle;
        int passengers;
        if(random.nextInt(100) > 10){ // For now, it is an 90% chance to be a car and a 10% chance to be a bus
            n = new Normal(Car.getMaxPassengers() / 2, 1);
            passengers = (int) n.sample();
            vehicle = new Car(passengers, 1, 1); //passengers, start point, destination
        } else{
            n = new Normal(Bus.getMaxPassengers() / 2, 1);
            passengers = (int) n.sample();
            vehicle = new Bus(passengers, 1, 1); //passengers, start point, destination
        }
        nextVehicle = currentTime + e.sample();
        return(vehicle);
    }
    // Get Method for Next Vehicle Creation Time
    public double getNextVehicleTime(){
        return(nextVehicle);
    }

}
