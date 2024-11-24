public class Car extends Vehicle {
    public Car(int passengerCount, Highway startPoint, Highway endPoint, double startTime) {
        super(passengerCount, startPoint, endPoint, startTime);
        vehicleLength = 15;
        maxPassengerCount = 5;
    }

    // Max Passengers: 5
}
