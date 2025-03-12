package Aircrafts;

public abstract class Aircraft {

    private String Model;
    private String Name;

    public Aircraft(String model, String name) {
        Model = model;
        Name = name;
    }

    public String getModel() {
        return Model;
    }

    public String getName() {
        return Name;
    }

}
