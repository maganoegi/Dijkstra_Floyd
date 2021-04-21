import java.util.Optional;
import java.util.PriorityQueue;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Algorithm {

    private static String getMinKey(Map<String, Integer> map) {
        String minKey = null;
        int minValue = Integer.MAX_VALUE;
        for(String key : map.keySet()) {
            int value = map.get(key);
            if(value < minValue) {
                minValue = value;
                minKey = key;
            }
        }
        return minKey;
    }
    

    public static LinkedHashMap<String, Integer> dijkstra(
        ConnectionMatrix connections,
        LinkedHashMap<String, String> ancestors, 
        String origin) {

        LinkedHashMap<String, Integer> dist = new LinkedHashMap<>();
        for (String city : connections.cities().keySet()) {
            dist.put(city, ConnectionMatrix.inf);
        }

        HashMap<String, Integer> priorityQueue = new HashMap<>();
        priorityQueue.put(origin, 0);

        while (!priorityQueue.isEmpty()) {
            String currentKey = Algorithm.getMinKey(priorityQueue);
            int currentVal = priorityQueue.remove(currentKey);


            for (String city : connections.cities().keySet()) {
                if (!city.equals(currentKey)) {
                    Optional<Integer> d = connections.queryDistanceBetweenIfExists(currentKey, city);
                    if (d.isPresent()) {
                        int newVal = currentVal + d.get();
                        priorityQueue.put(city, newVal);

                        if (dist.get(city) > newVal) {
                            dist.put(city, newVal);
                            ancestors.put(city, currentKey);
                        } else {
                            priorityQueue.remove(city);
                        }
                    }
                }
            }
        }
        return dist;
    }


    public static void floydWarshall(ConnectionMatrix connections, ConnectionMatrix precedences) {

        for (String k : connections.cities().keySet()) {
            for (String i : connections.cities().keySet()) {
                for (String j : connections.cities().keySet()) {
                    int ij = connections.queryDistanceBetween(i, j); 
                    Optional<Integer> ik = connections.queryDistanceBetweenIfExists(i, k); 
                    Optional<Integer> kj = connections.queryDistanceBetweenIfExists(k, j); 
                    
                    if (ik.isPresent() && kj.isPresent()) {
                        int sum = ik.get() + kj.get();
                        if (ij > sum) {
                            connections.addConnection(i, j, sum);
                            int ancestor = precedences.queryDistanceBetween(k, j);
                            precedences.addConnection(i, j, ancestor);
                        }
                    }
                }
            }
        }
    }

    public static String backTrackPrecedencesFloyd(
        ConnectionMatrix precedences,
        String origin,
        String destination) {
        
        String output = destination;
        if (!origin.equals(destination)) {
            LinkedHashMap<String, Integer> row = precedences.connectionMatrix().get(origin);
            Set<String> keys = row.keySet();
            List<String> listKeys = new ArrayList<String>(keys);
            int i = listKeys.indexOf(origin);
            int j = listKeys.indexOf(destination);

            output = Algorithm.backTrackPrecedencesFloyd(
                precedences, 
                origin, 
                precedences.elementAt(i, (precedences.elementAt(i, j).getValue())).getKey()
            ) + ":" + output;
        }
        return output;
    }


}
