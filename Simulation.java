import java.util.Random;

public class Simulation {
    double currentTime = 0;
    double totalTime = 0;
    // stores the times for the events to be executed
    // might change data structure to queue
    double[] pastEvents = new double[5];
    // stores the lengths of highway sections in order
    // might import from csv in the future
    double[] lengths = new double[] { 1000, 3000, 3000, 6000 };
    // array of the highway sections in order based on the number of lengths
    Highway[] highways = new Highway[lengths.length];
    // for every highway this array stores if there is an on ramp(true) or an off
    // ramp(false) in order
    boolean[] hasOnRamp = new boolean[] { true, false, true, false };
    // will store all the highways with off ramps
    // might need a different data structure or a different way to init length
    Highway[] highwaysWithOffRamps = new Highway[2];

    Arrival arrival = new Arrival();
    Exponential exponential = new Exponential(0.5);
    Normal normal = new Normal(.5, 1);

    // iterates through the highways in the array until the time is up
    private void doLoop() {
        while (currentTime < totalTime) {
            for (int i = 0; i < highways.length; i++) {
                doNextEvents(getNextEvents(highways[i]), highways[i], highways);
            }
            currentTime++;

        }
    }

    // should be called every time the time advances
    private void doNextEvents(double[] events, Highway highway, Highway[] highways) {
        for (int i = 0; i < events.length; i++) {
            // checks if the event time exists
            if (events[i] == 0) {
                break;
            }
            // if the time is the next arrival time
            else if (events[i] == highway.getNextArrival()) {
                Vehicle vehicle = arrival.nextVehicle(currentTime, highway, getOffRamp(highway));
                ;
                highway.enqueueRamp(vehicle);

                highway.setNextArrival(currentTime + exponential.sample());
            }
            // if the time is the next merge time
            else if (events[i] == highway.getNextMerge()) {
                // checks if the right lane has enough space for the next car on the ramp
                if (highway.getRightLaneRemainingSpace() >= highway.nextVehicleRamp().getVehicleLength()) {
                    highway.enqueueRightLane(highway.dequeueRamp());
                }
                highway.setNextMerge(currentTime + exponential.sample());
            }
            // if the time is the next vehicle in the right lane (lane 2)
            else if (events[i] == highway.getNextExitLane2()) {
                // checks if the car is exiting on this highway
                if (highway.nextVehicleRightLane().getEndPoint() == highway) {
                    highway.enqueueRamp(highway.dequeueRightLane());
                } else {
                    // checks if the car is exiting on the next highway
                    if (highways[highway.index + 1] == highway.nextVehicleRightLane().getEndPoint()) {
                        // if correct the car must join the right lane or not move forward
                        // checks if the right lane has space for the car
                        if (highways[highway.index + 1].getRightLaneRemainingSpace() >= highway.nextVehicleRightLane()
                                .getVehicleLength()) {
                            highways[highway.index + 1].enqueueRightLane(highway.dequeueRightLane());
                        }
                    }
                    // checks if the left lane has space for the next car and if the left lane is
                    // shorter than the right lane
                    if (highways[highway.index + 1].getLeftLaneRemainingSpace() >= highway.nextVehicleRightLane()
                            .getVehicleLength()
                            && highways[highway.index + 1].getLeftLaneRemainingSpace() < highways[highway.index + 1]
                                    .getRightLaneRemainingSpace()) {
                        highways[highway.index + 1].enqueueLeftLane(highway.dequeueRightLane());
                    }
                    // checks if the right lane has space for the car
                    else if (highways[highway.index + 1].getRightLaneRemainingSpace() >= highway.nextVehicleRightLane()
                            .getVehicleLength()) {
                        highways[highway.index + 1].enqueueRightLane(highway.dequeueRightLane());
                    }
                }
                highway.setNextExitLane2(currentTime + exponential.sample());
            }
            // if the next time is the left lane (lane 1)
            else if (events[i] == highway.getNextExitLane1()) {
                // checks if the next highway is the next car's exit
                // if it is the car must join the right lane or not move forward
                if (highways[highway.index + 1] == highway.nextVehicleLeftLane().getEndPoint()) {
                    // checks if the right lane has enough space for the next car
                    if (highways[highway.index + 1].getRightLaneRemainingSpace() >= highway.nextVehicleLeftLane()
                            .getVehicleLength()) {
                        highways[highway.index + 1].enqueueRightLane(highway.dequeueRightLane());
                    }
                }
                // checks if the left lane is shorter than the right and if it has enough space
                // for the next car in the left lane
                else if (highways[highway.index + 1].getLeftLaneRemainingSpace() >= highway.nextVehicleLeftLane()
                        .getVehicleLength()
                        && highways[highway.index + 1].getLeftLaneRemainingSpace() < highways[highway.index + 1]
                                .getRightLaneRemainingSpace()) {
                    highways[highway.index + 1].enqueueLeftLane(highway.dequeueLeftLane());
                }
                // checks if the right lane has enough space for the next car in the left lane
                else if (highways[highway.index + 1].getRightLaneRemainingSpace() >= highway.nextVehicleLeftLane()
                        .getVehicleLength()) {
                    highways[highway.index + 1].enqueueRightLane(highway.dequeueLeftLane());
                }
                highway.setNextExitLane1(currentTime + exponential.sample());
            }
        }
    }

    public void run(double input) {
        // variable that stores the index of highways with off ramps
        int a = 0;
        // uses a loop to create the array of highways from the length and onramp arrays
        for (int i = 0; i < highways.length; i++) {
            Highway highway = new Highway(lengths[i], hasOnRamp[i], i);
            highways[i] = highway;
            // adds the highway to a second array if it has an off ramp
            if (!hasOnRamp[i]) {
                highwaysWithOffRamps[a] = highway;
                a++;
            }
        }
        totalTime = input;
        // need to set all initial times for all events in the array
        for (int i = 0; i < highways.length; i++) {
            highways[i].setNextArrival(exponential.sample());
            highways[i].setNextExitLane1(exponential.sample());
            highways[i].setNextExitLane2(exponential.sample());
            if (highways[i].hasOnRamp()) {
                highways[i].setNextMerge(exponential.sample());
            }
        }
        doLoop();

    }

    private double[] getNextEvents(Highway highway) {
        // stores all the next event times
        double[] times = new double[] { highway.getNextArrival(), highway.getNextMerge(),
                highway.getNextExitLane1(), highway.getNextExitLane2() };
        // checks if the event has happened since the last time update
        for (int i = 0; i < times.length; i++) {
            if (times[i] != 0 && times[i] <= currentTime) {
                pastEvents[i] = times[i];
            } else {
                pastEvents[i] = 0;
            }
        }
        return pastEvents;
    }

    private Highway getOffRamp(Highway currentHighway) {
        int numOfHighways = 0;
        Highway[] remainingExits = new Highway[highwaysWithOffRamps.length];
        for (int i = 0; i < highwaysWithOffRamps.length; i++) {
            if (highwaysWithOffRamps[i].index > currentHighway.index) {
                highwaysWithOffRamps[numOfHighways] = highwaysWithOffRamps[i];
                numOfHighways++;
            }
        }
        return (remainingExits[getRandomInt(numOfHighways)]);
    }

    public static int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
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

}
