public class Simulation {
    double currentTime = 0;
    double totalTime = 0;
    int currentHighway = 0;
    double[] pastEvents = new double[5];
    Highway[] highways = new Highway[4];
    double[] lengths = new double[] { 1000, 3000, 3000, 6000 };
    boolean[] hasOnRamp = new boolean[] { true, false, true, false };

    Arrival arrival = new Arrival();
    Exponential exponential = new Exponential(0.5);
    Normal normal = new Normal(.5, 1);

    private void doLoop() {
        while (currentTime < totalTime) {
            for (int i = 0; i < highways.length; i++) {
                currentHighway = i;
                doNextEvents(getNextEvents(highways[i]), highways[i], highways);
            }
            currentTime++;

        }
    }

    private void doNextEvents(double[] events, Highway highway, Highway[] highways){
        for(int i = 0; i < events.length; i++){
            if(events [i] == 0){
                break;
            }
            else if(events[i] == highway.getNextArrival()){
                Vehicle vehicle = arrival.nextVehicle(currentTime);
                getOffRamp(vehicle);
                highway.enqueueRamp(vehicle);
                
                highway.setNextArrival(currentTime + exponential.sample());
            }
            else if(events[i] == highway.getNextMerge()){
                if(highway.getRightLaneRemainingSpace() > highway.nextVehicleRamp().getVehicleLength()){
                    highway.enqueueRightLane(highway.dequeueRamp());
                }
                highway.setNextMerge(currentTime + exponential.sample());
            }
            else if(events[i] == highway.getNextExitLane2()){
                if(highway.nextVehicleRightLane().getEndPoint() == currentHighway){
                    highway.rampEnqueue(highway.dequeueRightLane()
                }
                else{
                    if(highways[currentHighway+1].getLeftLaneRemainingSpace() > highway.nextVehicleRightLane() && highway[currentHighway + 1].getLeftLaneRemainingSpace() < highways[currentHighway + 1].getRightLaneRemainingSpace()){
                        highways[currentHighway + 1].enqueueLeftLane(highway.dequeueRightLane())
                    }
                    else if(highways[currentHighway + 1].getRightLaneRemainingSpace() > highway.nextVehicleRightLane()){
                        highways[currentHighway + 1].enqueueRightLane(highway.dequeueRightLane());
                    }
                }
            }


        }
    }

    public void run(double input) {
        for (int i = 0; i < highways.length; i++) {
            Highway highway = new Highway(lengths[i], hasOnRamp[i]);
            highways[i] = highway;
        }
        totalTime = input;
        doLoop();

    }

    private double[] getNextEvents(Highway highway) {
        double[] times = new double[] { highway.getNextArrival(), highway.getNextMerge(),
                highway.getNextExitToOfframp(), highway.getNextExitLane1(), highway.getNextExitLane2() };
        int index = 0;
        for (int i = 0; i < times.length; i++) {
            if (times[i] != 0 && times[i] <= currentTime) {
                pastEvents[i] = times[i];
            } else {
                pastEvents[i] = 0;
            }
        }
        return pastEvents;
    }

    private void getOffRamp(Vehicle vehicle) {

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
