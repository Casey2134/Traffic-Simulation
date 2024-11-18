public class Vehicle {
    int vehicleLength;
    int passengerCount;
    static int maxPassengerCount;
    int startPoint;
    int endPoint;
    double startTime;
    double endTime;
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

    public void setDistanceTraveled(double input) {
        distanceTraveled = input;
    }

    public static int getMaxPassengers() {

        return maxPassengerCount;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setEndTime(double input) {
        endTime = input;
    }

    public double getAverageSpeed() {
        return (distanceTraveled / getTotalTime());
    }

    public int getVehicleLength() {
        return vehicleLength;
    }

    public double getTotalTime() {
        return endTime - startTime;
    }

    public void doUnitTests() {
        // .....
    }

    public int getPassengers() {
        return passengerCount;
    }

}
