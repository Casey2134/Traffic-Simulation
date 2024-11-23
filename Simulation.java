import java.util.Random;

public class Simulation {
    double currentTime = 0;
    double totalTime;
    double nextArrival;
    double nextMerge;
    double nextExit;
    Lane lane = new Lane(10000);
    OnRamp onRamp1 = new OnRamp();
    OffRamp offRamp1 = new OffRamp();
    Arrival arrival = new Arrival();
    // consider making multiple exponential objects for each event type
    Exponential exponential = new Exponential(0.5);

    private void doLoop() {
        while (currentTime < totalTime) {
            // merges next vehicle
            if (currentTime == nextMerge) {
                if (onRamp1.nextVehicle() != null) {
                    if (onRamp1.nextVehicle().getVehicleLength() < lane.getRemainingSpace()) {
                        lane.enqueue(onRamp1.dequeue());
                        nextMerge = currentTime + 3;
                    }
                }
                nextMerge = currentTime + exponential.sample();

            }
            // exits vehicle from the highway
            if (currentTime == nextExit) {
                if (lane.nextVehicle() != null) {
                    lane.nextVehicle().setDistanceTraveled(lane.getLength());
                    lane.nextVehicle().setEndTime(currentTime);
                    offRamp1.enqueue(lane.dequeue());

                }
                if (lane.nextVehicle() == null) {
                    nextExit = nextMerge + (lane.getLength() / 95.333);
                } else if (lane.nextVehicle().getStartTime() + (lane.getLength() / 95.3333) < currentTime) {
                    nextExit = lane.nextVehicle().getStartTime() + (lane.getLength() / 95.3333);
                } else {
                    nextExit = currentTime + 3;
                }
            }
            // adds vehicle to the on ramp
            if (currentTime == nextArrival) {
                onRamp1.enqueue(arrival.nextVehicle(currentTime));
                nextArrival = currentTime + exponential.sample();
            }

            currentTime = getNextEvent();
        }
        getData(offRamp1);
    }

    public void run(double input) {
        totalTime = input;
        nextArrival = currentTime + exponential.sample();
        nextMerge = nextArrival + 3;
        nextExit = nextMerge + 120;
        doLoop();

    }

    private double getNextEvent() {
        double[] times = new double[] { nextArrival, nextMerge, nextExit };
        double closestTime = times[0];
        for (int i = 0; i < times.length; i++) {
            if (times[i] < closestTime) {
                closestTime = times[i];
            }
        }
        return closestTime;
    }

    private void getData(OffRamp offRamp) {
        double averageSpeedTotal = 0;
        double averageSpeedValues = 0;
        double numOfBusses = 0;
        double numOfCars = 0;
        double numOfVehicles = 0;
        double averageDistanceTotal = 0;
        double averageDistanceValues = 0;
        double totalPeopleTravelled = 0;
        Vehicle currentVehicle;
        while ((currentVehicle = offRamp.dequeue()) != null) {
            averageSpeedTotal += currentVehicle.getAverageSpeed();
            averageSpeedValues++;
            averageDistanceTotal += currentVehicle.getDistanceTraveled();
            averageDistanceValues++;
            totalPeopleTravelled += currentVehicle.getPassengers();
            if (currentVehicle.getVehicleLength() == 30) {
                numOfBusses++;
            } else {
                numOfCars++;
            }
            numOfVehicles++;

        }
        System.out.println(
                "Average distance travelled: " + (averageDistanceTotal / averageDistanceValues) / 5280 + " miles");
        System.out.println("Average speed: " + (averageSpeedTotal / averageSpeedValues) * 0.68 + " mph");
        System.out.println("Total vehicles: " + numOfVehicles);
        System.out.println("Number of cars: " + numOfCars);
        System.out.println("Number of busses: " + numOfBusses);
        System.out.println("Total passengers travelled: " + totalPeopleTravelled);

    }
    public static int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max+1);
    }

}
