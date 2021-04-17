
import java.util.ArrayList;
import java.util.Arrays;

public class App {

    public static void printStartMessage() {
        System.out.println(
            "Dijkstra and Floyd-Warshall algorithms implementation for CFF networks."
            );
        System.out.println(
            "Authors: Sergey PLATONOV, Dylan MPACKO"
        );
        System.out.println(
            "Course: Advanced Algorithmics, Eric PASCAL"
        );    
        System.out.println(
            "HEPIA 2020 - 2021"
        );    
    }

    public static void main(String[] args) {
        String path = args[0];
        String algo = args[1];

        printStartMessage();

        // ArrayList<ArrayList<Double>> distanceMatrix = FileDistanceReader.extractFromFile(args[0]);
        ArrayList<ArrayList<Double>> distanceMatrix = new ArrayList<ArrayList<Double>>();
        distanceMatrix.add(new ArrayList<Double>(Arrays.asList(0.0, 1.0, 3.0, 0.0)));
        distanceMatrix.add(new ArrayList<Double>(Arrays.asList(1.0, 0.0, 2.0, 4.0)));
        distanceMatrix.add(new ArrayList<Double>(Arrays.asList(3.0, 2.0, 0.0, 2.0)));
        distanceMatrix.add(new ArrayList<Double>(Arrays.asList(0.0, 4.0, 2.0, 4.0)));

        int source = 2;
        int destination = 4;
        System.out.println(distanceMatrix.get(source-1).get(destination-1));

        ArrayList<ArrayList<Double>> resultMatrix = Algorithm.applyAlgorithm(algo, distanceMatrix);
    }
}