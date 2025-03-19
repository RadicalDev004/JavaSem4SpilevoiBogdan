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

    public void balancedScheduling() {
        int extraNeeded = 0;
        List<Runway> runways = airport.getRunways();
        if (runways.isEmpty()) {
            System.out.println("No runways available.");
            return;
        }

        flights.sort(Comparator.comparing(Flight::getArrivalStart));

        PriorityQueue<Runway> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.getArrivals().size()));

        pq.addAll(runways);

        for (Flight flight : flights) {
            Runway bestRunway = null;

            List<Runway> tempList = new ArrayList<>();
            while (!pq.isEmpty()) {
                Runway r = pq.poll();
                if (!r.conflict(flight)) {
                    bestRunway = r;
                    break;
                }
                tempList.add(r);
            }

            pq.addAll(tempList);

            if (bestRunway == null) {
                extraNeeded++;
                Runway newRunway = new Runway("extra" + extraNeeded);
                newRunway.addArrival(flight);
                pq.offer(newRunway);
            }

            bestRunway.addArrival(flight);
            pq.offer(bestRunway);
        }

        for (Runway r : runways) {
            System.out.print(r.getName() + " : [");
            for (Flight fl : r.getArrivals()) {
                System.out.print("(" + fl.getAircraft().getName() + " - " + fl.getArrivalStart() + "), ");
            }
            System.out.println("]");
        }
    }
}
