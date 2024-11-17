public class Vehicle {
    int vehicleLength;
    int passengerCount;
    int maxPassengerCount;
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

    public void setDistanceTraveled(double input) {
        distanceTraveled = input;
    }

    public int getMaxPassengers() {
        return maxPassengerCount;
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
