package org.example;

public class Location implements Comparable<Location> {
    private  LocationType locationType;
    private String name;

    public Location(String name, LocationType locationType) {
        this.locationType = locationType;
        this.name = name;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    @Override
    public int compareTo(Location o) {
        return (this.name + this.locationType).compareTo(o.name + o.getLocationType());
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + locationType + ")";
    }
}
