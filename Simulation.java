public class Simulation {
    double currentTime = 0;
    double totalTime;
    double nextArrival;
    double nextMerge;
    double nextExit;
    Highway highway = new Highway(100);
    OnRamp onRamp1 = new OnRamp();
    OffRamp offRamp1 = new OffRamp();
    Arrival arrival = new Arrival();
    // consider making multiple exponential objects for each event type
    Exponential exponential = new Exponential(0.5);

    private void doLoop() {
        while (currentTime <= totalTime) {
            // merges next vehicle
            if (currentTime == nextMerge && onRamp1.nextVehicle() != null) {
                if (onRamp1.nextVehicle().getVehicleLength() < highway.getRemainingSpace()) {
                    highway.enqueue(onRamp1.dequeue());
                }
                nextMerge = currentTime + exponential.sample();
            }
            // exits vehicle from the highway
            if (currentTime == nextExit && highway.nextVehicle() != null) {
                offRamp1.enqueue(highway.dequeue());
                nextExit = currentTime + exponential.sample();
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
        nextMerge = nextArrival + exponential.sample();
        nextExit = nextMerge + exponential.sample();
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
        System.out.println("Average distance travelled: " + averageDistanceTotal / averageDistanceValues);
        System.out.println("Average speed: " + averageSpeedTotal / averageSpeedValues);
        System.out.println("Total vehicles: " + numOfVehicles);
        System.out.println("Number of cars: " + numOfCars);
        System.out.println("Number of busses: " + numOfBusses);
        System.out.println("Total passengers travelled: " + totalPeopleTravelled);

    }
}
