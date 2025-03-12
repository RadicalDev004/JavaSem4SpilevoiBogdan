package Aircrafts;

public class Airliner extends Aircraft implements PassengerCapable {

    public Airliner(String model, String name, Integer PassangerCapacity) {
        super(model, name);
        this.PassangerCapacity = PassangerCapacity;
    }

    public Integer getPassangerCapacity() {
        return PassangerCapacity;
    }

    public void setPassangerCapacity(Integer passangerCapacity) {
        PassangerCapacity = passangerCapacity;
    }

    private Integer PassangerCapacity;

}
