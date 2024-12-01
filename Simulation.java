import java.util.Random;

public class Simulation {
    enum Event {
        ARRIVAL, MERGE, LANE1, LANE2
    };

    double currentTime = 0;
    double totalTime = 0;
    double[] highwayLengths = new double[] { 1584, 190, 5280, 20064, 75, 17952, 1584, 6864, 2112, 1584, 1584, 1056, 528,
            3696, 2112, 1056, 528, 3168, 528, 2112, 1056, 2640, 528, 1584, 1584, 4752, 1584, 57552, 2640, 3168, 354,
            2640, 2640, 19536, 499, 15312, 364, 7392 };
    boolean[] hasOnRamp = new boolean[] { true, false, true, false, true, false, true, false, true, false, true, true,
            false, true, false, false, true, false, true, false, true, false, true, false, true, false, true, false,
            true, false, true, false, true, false, true, false, true, false };
    Highway[] highways = new Highway[highwayLengths.length];
    Highway currentHighway;
    Highway nextHighway;

    Arrival arrival = new Arrival();
    Exponential arrivalRate = new Exponential(0.67);
    Normal mergeRate = new Normal(4, 1);

    // iterates through the highways in the array until the time is up
    public void run(double time) {
        totalTime = time;
        for (int i = 0; i < highways.length; i++) {
            Highway highway = new Highway(highwayLengths[i], hasOnRamp[i], i);
            highways[i] = highway;
        }
        doLoop();
        getData();
    }

    private void doLoop() {
        while (currentTime < totalTime) {
            doNextEvent(getNextEvent());
        }

    }

    private Event getNextEvent() {
        Event nextEvent = Event.LANE1;
        Highway timeHighway = highways[0];
        int position = 0;

        double closestTime = highways[0].times[0];
        for (int i = 0; i < highways.length; i++) {
            for (int x = 0; x < highways[i].times.length; x++) {
                if (highways[i].times[x] < closestTime) {
                    if (x == 0) {
                        nextEvent = Event.LANE1;
                    } else if (x == 1) {
                        nextEvent = Event.LANE2;
                    } else if (x == 2) {
                        nextEvent = Event.MERGE;
                    } else {
                        nextEvent = Event.ARRIVAL;
                    }
                    closestTime = highways[i].times[x];
                    timeHighway = highways[i];

                    position = x;
                }
            }
        }
        currentTime = closestTime;
        currentHighway = timeHighway;
        if (currentHighway.index != highways.length - 1) {
            nextHighway = highways[currentHighway.index + 1];
        } else {
            nextHighway = null;
        }
        if (nextEvent == Event.ARRIVAL) {
            timeHighway.times[position] = currentTime + arrivalRate.sample();
        } else if (nextEvent == Event.MERGE) {
            timeHighway.times[position] = currentTime + mergeRate.sample();
        } else {
            timeHighway.times[position] = currentTime + 0.5;
        }
        return nextEvent;
    }

    private void doNextEvent(Event event) {
        if (event == Event.LANE1 && currentHighway.nextVehicleLeftLane() != null) {
            advanceHighway(event);
        } else if (event == Event.LANE2 && currentHighway.nextVehicleRightLane() != null) {
            advanceHighway(event);
        } else if (event == Event.MERGE && currentHighway.nextVehicleRamp() != null) {
            merge();
        } else {
            enterOnRamp();
        }
    }

    private void advanceHighway(Event event) {
        if (event == Event.LANE1) {
            if (nextHighway == currentHighway.nextVehicleLeftLane().endPoint) {
                if (nextHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleLeftLane()
                        .getVehicleLength()) {
                    currentHighway.nextVehicleLeftLane().updateDistanceTraveled(currentHighway.getLength());
                    nextHighway.enqueueRightLane(currentHighway.dequeueLeftLane());
                }
            } else {
                if (nextHighway.getLeftLaneRemainingSpace() < nextHighway.getRightLaneRemainingSpace() && nextHighway
                        .getLeftLaneRemainingSpace() >= currentHighway.nextVehicleLeftLane().getVehicleLength()) {
                    currentHighway.nextVehicleLeftLane().updateDistanceTraveled(currentHighway.getLength());
                    nextHighway.enqueueLeftLane(currentHighway.dequeueLeftLane());
                } else if (nextHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleLeftLane()
                        .getVehicleLength()) {
                    currentHighway.nextVehicleLeftLane().updateDistanceTraveled(currentHighway.getLength());
                    nextHighway.enqueueRightLane(currentHighway.dequeueLeftLane());
                }
            }
        }
        if (event == Event.LANE2) {
            if (currentHighway == currentHighway.nextVehicleRightLane().getEndPoint()
                    || currentHighway.index == highways.length - 1) {
                currentHighway.nextVehicleRightLane().updateDistanceTraveled(currentHighway.getLength());
                currentHighway.nextVehicleRightLane().setEndTime(currentTime);
                currentHighway.enqueueRamp(currentHighway.dequeueRightLane());
            } else if (nextHighway == currentHighway.nextVehicleRightLane().getEndPoint()) {
                if (nextHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleRightLane()
                        .getVehicleLength()) {
                    currentHighway.nextVehicleRightLane().updateDistanceTraveled(currentHighway.getLength());
                    nextHighway.enqueueRightLane(currentHighway.dequeueRightLane());
                }
            } else {
                if (nextHighway.getLeftLaneRemainingSpace() < nextHighway.getRightLaneRemainingSpace() && nextHighway
                        .getLeftLaneRemainingSpace() >= currentHighway.nextVehicleRightLane().getVehicleLength()) {
                    currentHighway.nextVehicleRightLane().updateDistanceTraveled(currentHighway.getLength());
                    nextHighway.enqueueLeftLane(currentHighway.dequeueRightLane());
                } else if (nextHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleRightLane()
                        .getVehicleLength()) {
                    currentHighway.nextVehicleRightLane().updateDistanceTraveled(currentHighway.getLength());
                    nextHighway.enqueueRightLane(currentHighway.dequeueRightLane());
                }
            }
        }

    }

    private void merge() {
        if (currentHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleRamp().getVehicleLength()) {
            currentHighway.enqueueRightLane(currentHighway.dequeueRamp());
        }
    }

    private void enterOnRamp() {
        if (currentHighway.hasOnRamp()) {
            currentHighway.enqueueRamp(arrival.nextVehicle(currentTime, currentHighway, getExitHighway()));
        }
    }

    private GenericQueue<Highway> getOffRamps() {
        GenericQueue<Highway> offRamps = new GenericQueue<>();

        for (int i = 0; i < highways.length; i++) {
            if (!highways[i].hasOnRamp()) {
                offRamps.enqueue(highways[i]);
            }
        }
        return offRamps;
    }

    private Highway getExitHighway() {
        GenericQueue<Highway> offRamps = new GenericQueue<>();

        for (int i = 0; i < highways.length; i++) {
            if (highways[i].index > currentHighway.index && !highways[i].hasOnRamp()) {
                offRamps.enqueue(highways[i]);
            }
        }

        Highway exitHighway = offRamps.getHead();
        int index = getRandomInt(offRamps.length);
        for (int i = 0; i <= index; i++) {
            exitHighway = offRamps.dequeue();
        }
        return exitHighway;
    }

    public static int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
    }

    private void getData() {
        double averageSpeedTotal = 0;
        double averageSpeedValues = 0;
        double numOfBusses = 0;
        double numOfCars = 0;
        double numOfVehicles = 0;
        double averageDistanceTotal = 0;
        double averageDistanceValues = 0;
        double totalPeopleTravelled = 0;
        Vehicle currentVehicle;
        Highway processHighway;
        GenericQueue<Highway> offRamps = getOffRamps();
        while ((processHighway = offRamps.dequeue()) != null) {
            while ((currentVehicle = processHighway.dequeueRamp()) != null) {
                // System.out.println(currentVehicle.toString());
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
