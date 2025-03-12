package Aircrafts;

public class Drone extends Aircraft implements CargoCapable {
    private Integer BatteryLife;

    public Drone(String model, String name, Integer BatteryLife) {
        super(model, name);
        this.BatteryLife = BatteryLife;
    }


    public void setBatteryLife(Integer batteryLife) {
        BatteryLife = batteryLife;
    }

    public Integer getBatteryLife() {
        return BatteryLife;
    }
}
