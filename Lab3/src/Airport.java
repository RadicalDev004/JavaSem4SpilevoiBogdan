
import java.util.*;

public class Airport {
    private String airportName;
    private List<Runway> runways = new ArrayList<>();

    public  void AddRunway(Runway runway) {
        runways.add(runway);
    }
    private void RemoveRunway(Runway runway) {
        runways.remove(runway);
    }

    public Airport(String airportName, Runway... runways) {
        this.airportName = airportName;
        for(Runway runway : runways) {
            AddRunway(runway);
        }
    }

    public List<Runway> getRunways() {
        return runways;
    }
}
