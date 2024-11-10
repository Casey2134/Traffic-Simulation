public class Vehicle {
    int startPoint;
    int endPoint;
    double startTime;
    double endTime;
    double totalTime;
    double distanceTraveled;


    public Vehicle(int startPosition, int endPosition, double startTime) {
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
