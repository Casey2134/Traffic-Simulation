public class Arrival {
    // Decides How Many Passengers Vehicles Have
    private Normal n;
    // Decides What Type of Vehicle
    private int busesLeft;
    private double timeLeftToBuses;

    // Custom Constructor (For Changing Arrival Rates)
    public Arrival(double timeLeftToBuses) {
        this.timeLeftToBuses = timeLeftToBuses;
    }

    // Creation Method For Vehicle
    public Vehicle nextVehicle(double currentTime, Highway highway, Highway exitHighway, int totalHighways) {
        Vehicle vehicle;
        int passengers;
        if(currentTime > timeLeftToBuses){
            busesLeft = totalHighways;
            timeLeftToBuses += timeLeftToBuses;
        }
        if(busesLeft > 0){
            n = new Normal((Bus.getMaxPassengers() / 2), 1);
            passengers = (int) n.sample();
            vehicle = new Bus(passengers, highway, exitHighway, currentTime);
            busesLeft--;
        } else{
            n = new Normal((Car.getMaxPassengers() / 2), 1);
            passengers = (int) n.sample();
            vehicle = new Car(passengers, highway, exitHighway, currentTime);
        }
        return (vehicle);
    }
}
