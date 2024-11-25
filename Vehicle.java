public class Vehicle {
    int vehicleLength;
    int passengerCount;
    static int maxPassengerCount;
    Highway startPoint;
    Highway endPoint;
    double startTime;
    double endTime;
    double distanceTraveled;
    double segmentTime;

    public Vehicle(int passengerCount, Highway startPoint, Highway endPoint, double startTime) {
        this.passengerCount = passengerCount;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
    };

    public double getSegmentTime() {
        return segmentTime;
    }

    public void setSegmentTime(double time) {
        segmentTime = time;
    }

    public String toString() {
        return (startTime + ", " + endTime);
    }

    public Highway getStartPoint() {
        return startPoint;
    }

    public Highway getEndPoint() {
        return endPoint;
    }

    public void updateDistanceTraveled(double input) {
        distanceTraveled += input;
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
