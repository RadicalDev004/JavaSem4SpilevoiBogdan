//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Aircrafts.*;
import Helper.Pair;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Aircraft a1 = new Drone("D1", "Drone1", 24);
        Aircraft a2 = new Airliner("Ar1", "Aircraft1", 200);
        Aircraft a3 = new Freighter("Fr1", "Freighter1", 50);

        Flight f1 = new Flight(a1, new Pair<>(LocalTime.of(14, 5), LocalTime.of(15, 15)));
        Flight f2 = new Flight(a2, new Pair<>(LocalTime.of(13, 15), LocalTime.of(14, 30)));
        Flight f3 = new Flight(a3, new Pair<>(LocalTime.of(16, 25), LocalTime.of(17, 5)));

        Airport airport = new Airport("airport1", new Runway("r1"), new Runway("r2"));

        Schedule schedule = new Schedule(airport, f1, f2, f3);
        schedule.scheduleFlights();
    }
}