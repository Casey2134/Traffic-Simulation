public class Simulation {
    public Simulation(double input) {
        totalTime = input;
    }

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
    Exponential exponential = new Exponential(0.5);nextArrival=currentTime+exponential.sample();nextMerge=nextArrival+exponential.sample();nextExit=nextMerge+exponential.sample();

    while(currentTime<=totalTime)
    {
        // merges next vehicle
        if (currentTime == nextMerge && onRamp1.nextVehicle() != null) {
            if (onRamp1.nextVehicle().getVehicleLength < highway.getRemainingSpace()) {
                highway.enqueue(onRamp1.dequeue());
            }
            nextMerge = currentTime + exponential.sample();
        }
        // exits vehicle from the highway
        if (currentTime == nextExit && highway.nextVehicle() != null) {
            highway.nextVehicle().arrived();
            offRamp1.enqueue(highway.dequeue());
            nextExit = currentTime + exponential.sample();
        }
        // adds vehicle to the on ramp
        if (currentTime == nextArrival) {
            onRamp1.enqueue(arrival.getNextVehicle(currentTime));
            nextArrival = currentTime + exponential.sample();
        }

        currentTime = getNextEvent();

    }

    getData(offRamp1);

    private double getNextEvent(){
        double[] times = new double[] {nextArrival, nextMerge, nextExit};
        double closestTime = times[0];
        for(int i = 0; i < times.length; i++){
            if(times[i] < closestTime){
                closestTime = times[i];
            }
        }
        return closestTime;
    }

    private void getData(OffRamp offRamp){
        double averageSpeedTotal;
        double averageSpeedValues;
        double numOfBusses;
        double numOfCars;
        double numOfVehicles;
        double averageDistanceTotal;
        double averageDistanceValues;
        double totalPeopleTravelled;
        Vehicle currentVehicle;
        while (currentVehicle = offRamp.dequeue() != null) {
            averageSpeedTotal += currentVehicle.getSpeed();
            averageSpeedValues++;
            averageDistanceTotal += currentVehicle.getDistance();
            averageDistanceValues++;
            totalPeopleTravelled += currentVehicle.getPassengers();
            if(currentVehicle.length == 30){
                numOfBusses++;
            }
            else{
                numOfCars++;
            }
            numOfVehicles++;
            
        }
        System.out.println("Average distance travelled: " + averageDistanceTotal/averageDistanceValues);
        System.out.println("Average speed: " + averageSpeedTotal/averageSpeedValues);
        System.out.println("Total vehicles: " + numOfVehicles);
        System.out.println("Number of cars: " + numOfCars);
        System.out.println("Number of busses: " + numOfBusses);
        System.out.println("Total passengers travelled: " + totalPeopleTravelled);

    }
}
