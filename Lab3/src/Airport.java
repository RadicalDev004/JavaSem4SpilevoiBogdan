
import java.util.*;

public class Airport {
    private String airportName;
    private List<Runway> runways = new ArrayList<>();

    public  void addRunway(Runway runway) {
        runways.add(runway);
    }
    private void removeRunway(Runway runway) {
        runways.remove(runway);
    }

    public Airport(String airportName, Runway... runways) {
        this.airportName = airportName;
        for(Runway runway : runways) {
            addRunway(runway);
        }
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public List<Runway> getRunways() {
        return runways;
    }
}
