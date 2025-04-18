package Aircrafts;

public abstract class Aircraft {

    private String model;
    private String name;

    public Aircraft(String model, String name) {
        this.model = model;
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setName(String name) {
        this.name = name;
    }
}
