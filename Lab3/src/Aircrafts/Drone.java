package Aircrafts;

public class Drone extends Aircraft implements CargoCapable {
    private int batteryLife;

    public Drone(String model, String name, int batteryLife) {
        super(model, name);
        this.batteryLife = batteryLife;
    }


    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public int getBatteryLife() {
        return batteryLife;
    }
}
