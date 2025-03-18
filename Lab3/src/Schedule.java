import Aircrafts.Aircraft;

import java.util.*;

public class Schedule {
    private  Airport airport;
    private  List<Flight> flights = new ArrayList<>();

    public Schedule(Airport airport, Flight... flights) {
        this.airport = airport;
        Collections.addAll(this.flights, flights);
    }

    public void scheduleFlights()
    {
        flights.sort(Comparator.comparing(Flight::getArrivalEnd));

        for(var rnw : airport.getRunways())
        {
            for(int i = 0; i < flights.size(); i++)
            {

                if(rnw.conflict(flights.get(i)))
                    continue;

                rnw.addArrival(flights.get(i));
                flights.remove(flights.get(i));
                i--;
            }
        }

        for(var rnw : airport.getRunways())
        {
            System.out.print(rnw.getName() + " : [");
            for (var fl : rnw.getArrivals())
            {
                System.out.print("(" +fl.getAircraft().getName() + " - " + fl.getArrivalTime() + "),");
            }
            System.out.println("]");
        }
    }

    public void equitableScheduling() {
        List<Runway> runways = airport.getRunways();
        int runwayCount = runways.size();
        if (runwayCount == 0) {
            System.out.println("No runways available.");
            return;
        }

        flights.sort(Comparator.comparing(Flight::getArrivalStart));

        Queue<Runway> queue = new LinkedList<>(runways);
        Map<Runway, List<Flight>> schedule = new HashMap<>();
        for (Runway r : runways) schedule.put(r, new ArrayList<>());

        for (Flight flight : flights) {
            Runway selectedRunway = queue.poll();

            boolean assigned = false;
            for (Runway r : runways) {
                if (!r.conflict(flight)) {
                    schedule.get(r).add(flight);
                    queue.offer(r);
                    assigned = true;
                    break;
                }
            }


            if (!assigned) {
                System.out.println("Not enough runways! Need at least " + (flights.size() / 2 + 1) + " runways.");
                return;
            }
        }

        // Print schedule
        for (Runway r : runways) {
            System.out.print(r.getName() + " : [");
            for (Flight fl : schedule.get(r)) {
                System.out.print("(" + fl.getAircraft().getName() + " - " + fl.getArrivalStart() + "), ");
            }
            System.out.println("]");
        }
    }
}
