//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import Aircrafts.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Aircraft> Aircrafts = new ArrayList<>();

        Aircraft a1 = new Drone("D1", "Drone1", 24);
        Aircraft a2 = new Airliner("Ar1", "Aircraft1", 200);
        Aircraft a3 = new Freighter("Fr1", "Freighter1", 50);

        Aircrafts.add(a1);
        Aircrafts.add(a2);
        Aircrafts.add(a3);

        List<CargoCapable> Cargo = new ArrayList<>();
        for(var a : Aircrafts) {
            if(a instanceof CargoCapable) {
                Cargo.add((CargoCapable)a);
            }
        }

        for(var c : Cargo) {
            System.out.println(((Aircraft)c).getName());
        }

    }
}