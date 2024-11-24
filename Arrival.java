public class Arrival {
    // Decides How Many Passengers Vehicles Have
    private Normal n;
    // Decides What Type of Vehicle
    private int busesLeft;
    private double timeLeftToBuses;

    // Custom Constructor (For Changing Arrival Rates)
    public Arrival() {
        e = new Exponential(1);
        timeLeftToBuses = 60.0;
    }

    // Creation Method For Vehicle
    public Vehicle nextVehicle(double currentTime, Highway highway, Highway exitHighway, int totalHighways) {
        Vehicle vehicle;
        int passengers;
        if(currentTime > timeLeftToBuses){
            busesLeft = totalHighways;
            timeLeftToBuses += 60;
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
