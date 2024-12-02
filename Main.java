import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        String[] dataNames = {"Average Speed", "Average Time", "Bus Passengers", "Car Passengers", "Number of Buses",
        "Number of Cars", "Number of Vehicles", "Average Distance", "Total Passengers"};
        final int numOfRuns = 100;
        int simulationBuses = 100;
        double simulationTime = 18000;
        double[][] dataCollection = new double[numOfRuns][10];
        for (int i = 0; i < numOfRuns; i++) {
            Simulation simulation = new Simulation();
            dataCollection[i] = simulation.run(simulationTime, 1800, simulationBuses * i / numOfRuns);
        }
        try {
            File file = new File("Results");
            FileWriter writer = new FileWriter(file, true);
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print("");
            writer.append("Total Time Set:\n\n\t").append(Double.toString(simulationTime)).append("\n\n");
            for(int i = 0 ; i < dataNames.length ; i++){
                writer.append(dataNames[i]).append(" Set:\n\n");
                for(double[] data : dataCollection){
                    writer.append("\t").append(Double.toString(data[i])).append("\n");
                }
                writer.append("\n");
            }
            writer.close();
            printWriter.close();
        } catch (IOException e) {
            //Hi
        }
    }
}