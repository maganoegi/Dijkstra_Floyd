

public interface Algorithm {

    final static double inf = Double.POSITIVE_INFINITY;

    /**
    * Applied algorithm specified through user input. Enum pattern is used to select the right 
    * algorithm to apply. 
    *
    * @param  algorithm  user provided string with the name of the algorithm
    * @param  distanceMatrix the matrix containing connection graph data
    * @return      2D matrix with the shortest distances between every vertex of the graph
    */
    static double[][] applyAlgorithm (
        String algorithm, 
        double[][] distanceMatrix
    ) throws IllegalArgumentException {

        double[][] resultMatrix;
        switch (algorithm) {
            case "dijkstra" : resultMatrix = dijkstra(distanceMatrix); break;
            case "floyd" : resultMatrix = floydWarshall(distanceMatrix); break;
            case "warshall" : resultMatrix = floydWarshall(distanceMatrix); break;
            default : throw new IllegalArgumentException();
        }

        return resultMatrix;
    }

    public static double[][] dijkstra(
        double[][] distanceMatrix
    ) {
        double[][] resultingMatrix = distanceMatrix; // TODO: clone it
        int nbVertices = resultingMatrix.length;

        return resultingMatrix;
    }

    public static double[][] floydWarshall(
        double[][] distanceMatrix
    ) {
        double[][] resultingMatrix = distanceMatrix; // TODO: clone it, or maybe not
        int nbVertices = resultingMatrix.length;

        for (int i=0; i < nbVertices; i++) {
            for (int j=0; j < nbVertices; j++) {
                for (int k=0; k < nbVertices; k++) {
                    double ij = resultingMatrix[i][j]; 
                    double ik = resultingMatrix[i][k]; 
                    double kj = resultingMatrix[k][j]; 
                    double sum = ik + kj;
                    if (ij > sum) {
                        resultingMatrix[i][j] = sum;
                    }
                }
            }   
        }

        return resultingMatrix;
    }


}
