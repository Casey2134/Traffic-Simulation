public class Arrival {
    // Decides How Many Passengers Vehicles Have
    private Normal car;
    private Normal bus;
    private int busesLeft;
    private double timeLeftToBuses;
    private double intervalForBuses;
    private int totalBusPassengers = 0;
    private int numberOfBuses;

    public Arrival(double intervalForBuses, int numberOfBuses) {
        bus = new Normal((22 / 2), 3);
        car = new Normal((5 / 2), 1);
        this.numberOfBuses = numberOfBuses;
        this.intervalForBuses = intervalForBuses;
        timeLeftToBuses = 0;
    }

    // Creation Method For Vehicle
    public Vehicle nextVehicle(double currentTime, Highway highway, Highway exitHighway) {
        Vehicle vehicle;
        int passengers;
        if (currentTime >= timeLeftToBuses) {
            busesLeft = numberOfBuses;
            timeLeftToBuses += intervalForBuses;
        }
        if (busesLeft > 0) {
            passengers = (int) Math.round(bus.sample());
            if (passengers <= 0) {
                passengers = 1;
            }
            totalBusPassengers += passengers;
            vehicle = new Bus(passengers, highway, exitHighway, currentTime);
            busesLeft--;
        } else {
            passengers = (int) Math.round(car.sample());
            if (passengers <= 0) {
                passengers = 1;
            }
            if (totalBusPassengers > 0) {
                totalBusPassengers -= passengers;
                return (null);
            }
            vehicle = new Car(passengers, highway, exitHighway, currentTime);
        }
        return (vehicle);
    }
}