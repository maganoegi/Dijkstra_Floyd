
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;

public class ConnectionMatrix implements Cloneable {

	static final int inf = Integer.MAX_VALUE;

    private LinkedHashMap<String, City> cities;
    private LinkedHashMap<String, LinkedHashMap<String, Integer>> connectionMatrix;

    public ConnectionMatrix(
        LinkedHashMap<String, City> cities,
        LinkedHashMap<String, LinkedHashMap<String, Integer>> mapping) {

        this.connectionMatrix = mapping;
        this.cities = cities;
    }

    public ConnectionMatrix(
        LinkedHashMap<String, City> cities) {

        this.connectionMatrix = ConnectionMatrix.initUsing(cities, 0, inf);
        this.cities = cities;
    }


    /**
    * Custom named constructor, @see initUsing()
    */
    public static ConnectionMatrix of(
        LinkedHashMap<String, City> cities, 
        int intersectionDefaultVal, 
        int nullDefaultVal) {

        return new ConnectionMatrix(
            cities, 
            ConnectionMatrix.initUsing(
                cities, 
                intersectionDefaultVal, 
                nullDefaultVal
            )
        );
    }

    /**
    * An equivalent to a deep copy of the object.
    */
    public ConnectionMatrix duplicate() {
        ConnectionMatrix fresh = ConnectionMatrix.of(this.cities(), inf, inf);
        for (String origin : cities.keySet()) {
            for (String destination : cities.keySet()) {
                int d = this.queryDistanceBetween(origin, destination);
                fresh.addConnection(origin, destination, d);
            }   
        }
        return fresh;
    }

    /**
    * A deep copy with precedence in mind - the values are not copied, but are instead set to -1 
    * if they are different to the default value in the original matrix.
    *
    * This is used for creation of the matrix template for the Floyd precedence matrix.
    */
    public ConnectionMatrix duplicateWithPrecedence() {
        ConnectionMatrix fresh = ConnectionMatrix.of(this.cities(), -1, -1);
        int i = 0;
        for (String origin : cities.keySet()) {
            for (String destination : cities.keySet()) {
                Optional<Integer> d = this.queryDistanceBetweenIfExists(origin, destination);
                if (d.isPresent() && d.get() != 0) {
                    fresh.addConnection(origin, destination, i);
                }
            }   
            i++;
        }
        return fresh;
    }

    /**
    * A solution allowing me to int index into my 2D LinkedHashMaps.
    *
    * @param    i       origin index    
    * @param    j       destination index  
    * @return   val     Map.Entry instance. This in order to be able to access both Key and Value!  
    */
    public Map.Entry<String, Integer> elementAt(int i, int j) {
        Set<Map.Entry<String, LinkedHashMap<String, Integer>>> mapSet = this.connectionMatrix.entrySet();
        Map.Entry<String, LinkedHashMap<String, Integer>> rowEntry = (
                new ArrayList<Map.Entry<String, 
                LinkedHashMap<String, 
                Integer>>>(mapSet)
            ).get(i);

        LinkedHashMap<String, Integer> rowValue = rowEntry.getValue();
        Set<Map.Entry<String, Integer>> rowSet = rowValue.entrySet();
        Map.Entry<String, Integer> val = (new ArrayList<Map.Entry<String, Integer>>(rowSet)).get(j);
        return val;
    }

    /**
    * Matrix generator with custom value control.
    *
    * @param    cities       HashMap with cities, for reference    
    * @param    intersectionDefaultVal  value to be put into the elements that intersect (i == j)
    * @param    nullDefaultVal  value to be put for elements that have no direct contact between them
    * @return   mapping     2D LinkedHashMap, allowing for String indexation.
    */
    public static LinkedHashMap<String, LinkedHashMap<String, Integer>> initUsing(
        LinkedHashMap<String, City> cities, 
        int intersectionDefaultVal, 
        int nullDefaultVal) {

            LinkedHashMap<String, LinkedHashMap<String, Integer>> mapping = new LinkedHashMap<>();
            for (String origin : cities.keySet()) {
                LinkedHashMap<String, Integer> col = new LinkedHashMap<>();
                for (String destination : cities.keySet()) {
                    int v = (origin.equals(destination)) ? intersectionDefaultVal : nullDefaultVal;
                    col.put(destination, v);
                }
                mapping.put(origin, col);
            }

            return mapping;
    }
    
    public LinkedHashMap<String, City> cities() { return this.cities; }
    public LinkedHashMap<String, LinkedHashMap<String, Integer>> connectionMatrix() {
        return this.connectionMatrix;
    }

    public void addConnectionSymmetrically(String origin, String destination, int distance) {
        this.addConnection(origin, destination, distance);
        this.addConnection(destination, origin, distance);
    }

    public void addConnection(String origin, String destination, int distance) {
        LinkedHashMap<String, Integer> col = this.connectionMatrix.get(origin);
        col.put(destination, distance);
        this.connectionMatrix.put(origin, col);
    }

    public Optional<Integer> queryDistanceBetweenIfExists(String origin, String destination) {
        int v = this.queryDistanceBetween(origin, destination);
        return (v == inf) ? Optional.ofNullable(null) : Optional.of(v);
    }

    public int queryDistanceBetween(String origin, String destination) {
        return this.connectionMatrix.get(origin).get(destination);
    }

    @Override
    public String toString() {
        String output = "";
        for (String origin : this.cities.keySet()) {
            for (String destination : this.cities.keySet()) {
                Optional<Integer> v = queryDistanceBetweenIfExists(origin, destination);
                output += (v.isPresent()) ? String.valueOf(v.get()) : "inf";
                output += " ";
            }
            output += "\n";
        }
        return output;
    }
}
