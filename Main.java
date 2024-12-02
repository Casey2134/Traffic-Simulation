public class Main {
    public static void main(String[] args) {

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
        double totalTime = 0;
        final double numOfRuns = 20;
        for (int i = 0; i < numOfRuns; i++) {
            Simulation simulation = new Simulation();
            double data[] = simulation.run(18000);

            averageSpeedTotal += data[0];
            averageSpeedValues += data[1];
            averageTimeValues += data[2];
            averageTimeTotal += data[3];
            totalBusPassengers += data[4];
            totalCarPassengers += data[5];
            numOfBusses += data[6];
            numOfCars += data[7];
            numOfVehicles += data[8];
            averageDistanceTotal += data[9];
            averageDistanceValues += data[10];
            totalPeopleTravelled += data[11];
            totalTime += data[12];
        }
        averageSpeedTotal /= numOfRuns;
        averageSpeedValues /= numOfRuns;
        averageTimeValues /= numOfRuns;
        averageTimeTotal /= numOfRuns;
        totalBusPassengers /= numOfRuns;
        totalCarPassengers /= numOfRuns;
        numOfBusses /= numOfRuns;
        numOfCars /= numOfRuns;
        numOfVehicles /= numOfRuns;
        averageDistanceTotal /= numOfRuns;
        averageDistanceValues /= numOfRuns;
        totalPeopleTravelled /= numOfRuns;
        totalTime /= numOfRuns;
        System.out.println("Number of runs: " + numOfRuns);
        System.out.println("Time Run = " + (totalTime / 60) / 60 + " hours");
        System.out.println("Average Time Travelled = " + ((averageTimeTotal / averageTimeValues) / 60) + " minutes");
        System.out.println("Average speed: " + (averageSpeedTotal / averageSpeedValues) * 0.68 + " mph");
        System.out.println(
                "Average distance travelled: " + (averageDistanceTotal / averageDistanceValues) / 5280 + " miles");
        System.out.println("Total vehicles: " + numOfVehicles);
        System.out.println("Number of cars: " + numOfCars);
        System.out.println("Number of busses: " + numOfBusses);
        System.out.println("Total passengers travelled: " + totalPeopleTravelled);
        System.out.println("Percentage of Cars " + numOfCars / numOfVehicles * 100 + "%");
        System.out.println("Average Bus Passengers: " + totalBusPassengers / numOfBusses);
        System.out.println("Average Car Passengers: " + totalCarPassengers / numOfCars);

    }
}