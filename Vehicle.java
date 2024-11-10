public class Vehicle {
    int length;
    int passengerCount;
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

    public double getDistanceTraveled() {
        return distanceTraveled;
    }

    public double getAverageSpeed() {
        return (distanceTraveled / totalTime);
    }


    public void doUnitTests() {
        //.....
    }

}
