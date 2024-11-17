public class Vehicle {
    int vehicleLength;
    int passengerCount;
    int maxPassengerCount;
    int startPoint;
    int endPoint;
    double startTime;
    double endTime;
    double totalTime;
    double distanceTraveled;

    public Vehicle(int passengerCount, int startPoint, int endPoint, double startTime) {
        this.passengerCount = passengerCount;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
    };

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public void dequeue(int endPoint, double currentTime) {
        this.endPoint = endPoint;
        distanceTraveled = endPoint - startPoint;
        endTime = currentTime;
        totalTime = endTime - startTime;
    }

    public int getMaxPassengers() {
        return maxPassengerCount;
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public double getAverageSpeed() {
        return (distanceTraveled / totalTime);
    }

    public int getVehicleLength() {
        return vehicleLength;
    }


    public void doUnitTests() {
        //.....
    }

}
