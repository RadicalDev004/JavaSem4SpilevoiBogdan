import Aircrafts.Aircraft;
import Helper.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Flight {
    private Aircraft aircraft;
    Pair<LocalTime, LocalTime> arrivalTime;

    public Flight(Aircraft aircraft, Pair<LocalTime, LocalTime> arrivalTime) {
        this.aircraft = aircraft;
        this.arrivalTime = arrivalTime;
    }

    public Pair<LocalTime, LocalTime> getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getArrivalStart() {
        return arrivalTime.getFirst();
    }

    public LocalTime getArrivalEnd() {
        return arrivalTime.getSecond();
    }

    public Aircraft getAircraft() {
        return aircraft;
    }


}
