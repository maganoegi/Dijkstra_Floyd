



public class City {

    private String name;
    private int longitude;
    private int latitude;

    
    public City(String name, int longitude, int latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String name() { return this.name; }

    public int longitude() { return this.longitude; }

    public int latitude() { return this.latitude; }


    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() { 
        return this.name.hashCode(); 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        City that = (City)o;
        return this.name.equals(that.name());
    }
}
