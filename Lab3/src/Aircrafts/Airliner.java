package Aircrafts;

public class Airliner extends Aircraft implements PassengerCapable {

    private int passangerCapacity;

    public Airliner(String model, String name, int passangerCapacity) {
        super(model, name);
        this.passangerCapacity = passangerCapacity;
    }

    public int getPassangerCapacity() {
        return passangerCapacity;
    }

    public void setPassangerCapacity(int passangerCapacity) {
        this.passangerCapacity = passangerCapacity;
    }



}
