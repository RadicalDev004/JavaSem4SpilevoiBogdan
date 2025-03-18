import java.time.LocalDate;
import Helper.*;

import java.time.LocalTime;
import java.util.*;

public class Runway {
    private String name;
    private  List<Flight> arrivals = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Runway(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addArrival(Flight flight) {

        arrivals.add(flight);
    }

    public List<Flight> getArrivals() {
        return arrivals;
    }

    public boolean conflict(Flight pair) {
        LocalTime start = pair.getArrivalTime().getFirst();
        LocalTime end = pair.getArrivalTime().getSecond();

        for (var arrival : arrivals) {
            LocalTime arrivalStart = arrival.getArrivalTime().getFirst();
            LocalTime arrivalEnd = arrival.getArrivalTime().getSecond();

            if (!(end.isBefore(arrivalStart) || arrivalEnd.isBefore(start))) {
                return true;
            }
        }
        return false;
    }
}
