public class Arrival {
    // Decides How Many Passengers Vehicles Have
    private Normal car;
    private Normal bus;
    private int busesLeft;
    private double timeLeftToBuses;
    private double intervalForBuses;
    private int totalBusPassengers = 0;

    public Arrival(double intervalForBuses) {
        bus = new Normal((Bus.getMaxPassengers() / 2), 3);
        car = new Normal((Car.getMaxPassengers() / 2), 1);
        this.intervalForBuses = intervalForBuses;
        timeLeftToBuses = intervalForBuses;
    }

    // Creation Method For Vehicle
    public Vehicle nextVehicle(double currentTime, Highway highway, Highway exitHighway, int totalHighways) {
        Vehicle vehicle;
        int passengers;
        if(currentTime > timeLeftToBuses){
            busesLeft = totalHighways;
            timeLeftToBuses += intervalForBuses;
        }
        if(busesLeft > 0){
            passengers = (int) bus.sample();
            totalBusPassengers += passengers;
            vehicle = new Bus(passengers, highway, exitHighway, currentTime);
            busesLeft--;
        } else{
            passengers = (int) car.sample();
            if(totalBusPassengers > 0){
                totalBusPassengers -= passengers;
                return(null);
            }
            vehicle = new Car(passengers, highway, exitHighway, currentTime);
        }
        return (vehicle);
    }
}
