public class Car extends Vehicle {
    public Car(int passengerCount, int startPoint, int endPoint, double startTime) {
        super(passengerCount, startPoint, endPoint, startTime);
        length = 15;
        maxPassengerCount = 5;
    }

    // Max Passengers: 5
}
