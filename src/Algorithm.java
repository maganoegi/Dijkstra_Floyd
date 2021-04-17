


import java.util.ArrayList;

public class Algorithm {


    /**
    * Applied algorithm specified through user input. Enum pattern is used to select the right 
    * algorithm to apply. 
    *
    * @param  algorithm  user provided string with the name of the algorithm
    * @param  distanceMatrix the matrix containing connection graph data
    * @return      2D matrix with the shortest distances between every vertex of the graph
    */
    static ArrayList<ArrayList<Double>> applyAlgorithm (
        String algorithm, 
        ArrayList<ArrayList<Double>> distanceMatrix
    ) throws IllegalArgumentException {

        ArrayList<ArrayList<Double>> resultMatrix;
        switch (algorithm) {
            case "dijkstra" : resultMatrix = dijkstra(distanceMatrix); break;
            case "floyd" : resultMatrix = floydWarshall(distanceMatrix); break;
            case "warshall" : resultMatrix = floydWarshall(distanceMatrix); break;
            default : throw new IllegalArgumentException();
        }

        return resultMatrix;
    }

    public static ArrayList<ArrayList<Double>> dijkstra(
        ArrayList<ArrayList<Double>> distanceMatrix
    ) {
        
        throw new UnsupportedOperationException();
    }

    public static ArrayList<ArrayList<Double>> floydWarshall(
        ArrayList<ArrayList<Double>> distanceMatrix
    ) {
        throw new UnsupportedOperationException();
    }


}
