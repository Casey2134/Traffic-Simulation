public class Bus extends Vehicle {
    public Bus(int passengerCount, Highway startPoint, Highway endPoint, double startTime) {
        super(passengerCount, startPoint, endPoint, startTime);
        vehicleLength = 30;
        maxPassengerCount = 22;
    }

    // Max Passengers: 22
}
