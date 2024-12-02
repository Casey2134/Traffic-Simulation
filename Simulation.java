import java.util.Random;

public class Simulation {
    enum Event {
        ARRIVAL, MERGE, LANE1, LANE2
    };

    int errorCount = 0;
    int vehiclesGenerated = 0;
    double currentTime = 0;
    double totalTime = 0;
    double[] highwayLengths = new double[] { 1584, 190, 5280, 20139, 17952, 1584, 6864, 2112, 1584, 1584, 1056,
            528,
            3696, 2112, 1056, 528, 3168, 528, 2112, 1056, 2640, 528, 1584, 1584, 4752, 1584, 57552, 2640, 3168, 354,
            2640, 2640, 19536, 499, 15312, 364, 7392 };
    int numOfHighways = highwayLengths.length;
    boolean[] hasOnRamp = new boolean[] { true, false, true, false, false, true, false, true, false, true, true,
            false, true, false, false, true, false, true, false, true, false, true, false, true, false, true, false,
            true, false, true, false, true, false, true, false, true, false };
    Highway[] highways = new Highway[highwayLengths.length];
    Highway currentHighway;
    Highway nextHighway;

    Arrival arrival = new Arrival(1800, 30);
    Exponential arrivalRate = new Exponential(0.21);
    Normal mergeRate = new Normal(5, 3);

    // iterates through the highways in the array until the time is up
    public double[] run(double time) {
        totalTime = time;
        for (int i = 0; i < highways.length; i++) {
            Highway highway = new Highway(highwayLengths[i], hasOnRamp[i], i);
            highways[i] = highway;
        }
        doLoop();
        return getData();
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
            if (nextEvent == Event.LANE1 && currentHighway.nextVehicleLeftLane() != null) {
                if ((currentTime - currentHighway.nextVehicleLeftLane().segmentTime) < getMinTimeOnHighway(
                        currentHighway)) {
                    timeHighway.times[position] = currentTime
                            + (getMinTimeOnHighway(currentHighway)
                                    - (currentTime - currentHighway.nextVehicleLeftLane().segmentTime));
                } else {
                    timeHighway.times[position] = currentTime + 1;
                }
            } else if (nextEvent == Event.LANE2 && currentHighway.nextVehicleRightLane() != null) {
                if (currentTime
                        - currentHighway.nextVehicleRightLane().segmentTime < getMinTimeOnHighway(currentHighway)) {
                    timeHighway.times[position] = currentTime + (getMinTimeOnHighway(currentHighway)
                            - (currentTime - currentHighway.nextVehicleRightLane().segmentTime));
                } else {
                    timeHighway.times[position] = currentTime + 1;
                }
            } else {
                timeHighway.times[position] = currentTime + getMinTimeOnHighway(currentHighway);
            }
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
                    currentHighway.nextVehicleLeftLane().segmentTime = currentTime;
                    nextHighway.enqueueRightLane(currentHighway.dequeueLeftLane());
                }
            } else {
                if (nextHighway.getLeftLaneRemainingSpace() < nextHighway.getRightLaneRemainingSpace() && nextHighway
                        .getLeftLaneRemainingSpace() >= currentHighway.nextVehicleLeftLane().getVehicleLength()) {
                    currentHighway.nextVehicleLeftLane().updateDistanceTraveled(currentHighway.getLength());
                    currentHighway.nextVehicleLeftLane().segmentTime = currentTime;
                    nextHighway.enqueueLeftLane(currentHighway.dequeueLeftLane());
                } else if (nextHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleLeftLane()
                        .getVehicleLength()) {
                    currentHighway.nextVehicleLeftLane().updateDistanceTraveled(currentHighway.getLength());
                    currentHighway.nextVehicleLeftLane().segmentTime = currentTime;
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
                    currentHighway.nextVehicleRightLane().segmentTime = currentTime;
                    nextHighway.enqueueRightLane(currentHighway.dequeueRightLane());
                }
            } else {
                if (nextHighway.getLeftLaneRemainingSpace() > nextHighway.getRightLaneRemainingSpace() && nextHighway
                        .getLeftLaneRemainingSpace() >= currentHighway.nextVehicleRightLane().getVehicleLength()) {
                    currentHighway.nextVehicleRightLane().updateDistanceTraveled(currentHighway.getLength());
                    currentHighway.nextVehicleRightLane().segmentTime = currentTime;
                    nextHighway.enqueueLeftLane(currentHighway.dequeueRightLane());
                } else if (nextHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleRightLane()
                        .getVehicleLength()) {
                    currentHighway.nextVehicleRightLane().updateDistanceTraveled(currentHighway.getLength());
                    currentHighway.nextVehicleRightLane().segmentTime = currentTime;
                    nextHighway.enqueueRightLane(currentHighway.dequeueRightLane());
                }
            }
        }

    }

    private void merge() {
        if (currentHighway.getRightLaneRemainingSpace() >= currentHighway.nextVehicleRamp().getVehicleLength()) {
            currentHighway.nextVehicleRamp().segmentTime = currentTime;
            currentHighway.enqueueRightLane(currentHighway.dequeueRamp());
        }
    }

    private void enterOnRamp() {
        if (currentHighway.hasOnRamp()) {

            Vehicle vehicle = arrival.nextVehicle(currentTime, nextHighway, getExitHighway());
            if (vehicle != null) {
                currentHighway.enqueueRamp(vehicle);
                vehiclesGenerated++;
            }
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
        Highway thisHighway;
        int index = getRandomInt(offRamps.length);
        for (int i = 0; i <= index; i++) {
            if ((thisHighway = offRamps.dequeue()) != null) {
                exitHighway = thisHighway;
            }
        }
        if (exitHighway == null) {
            System.out.println("error, exit highway is null");
            errorCount++;
            System.out.println("Errors " + errorCount);
        }
        return exitHighway;
    }

    public static int getRandomInt(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
    }

    private double getMinTimeOnHighway(Highway highway) {
        // System.out.println(highway.getLength() / 95.333);
        return (highway.getLength() / 95.333) + mergeRate.sample();
    }

    public double[] getData() {

        double averageSpeedTotal = 0;
        double averageSpeedValues = 0;
        double averageTimeValues = 0;
        double averageTimeTotal = 0;
        double totalBusPassengers = 0;
        double totalCarPassengers = 0;
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
                averageTimeTotal += currentVehicle.endTime - currentVehicle.startTime;
                averageTimeValues++;
                averageSpeedTotal += currentVehicle.getAverageSpeed();
                averageSpeedValues++;
                averageDistanceTotal += currentVehicle.getDistanceTraveled();
                averageDistanceValues++;
                totalPeopleTravelled += currentVehicle.getPassengers();
                if (currentVehicle.getVehicleLength() == 30) {
                    numOfBusses++;
                    totalBusPassengers += currentVehicle.getPassengers();
                } else {
                    numOfCars++;
                    totalCarPassengers += currentVehicle.getPassengers();
                }
                numOfVehicles++;

            }
        }
        double[] data = new double[] { averageSpeedTotal, averageSpeedValues, averageTimeValues, averageTimeTotal,
                totalBusPassengers, totalCarPassengers, numOfBusses, numOfCars, numOfVehicles, averageDistanceTotal,
                averageDistanceValues, totalPeopleTravelled, totalTime };
        return data;

    }

}
