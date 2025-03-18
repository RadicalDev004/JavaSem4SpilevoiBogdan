package Aircrafts;

public class Freighter extends  Aircraft implements CargoCapable {
    private int maximumPayload;

    public Freighter(String model, String name, int maximumPayload) {
        super(model, name);
        this.maximumPayload = maximumPayload;
    }

    public int getMaximumPayload() {
        return maximumPayload;
    }

    public void setMaximumPayload(int maximumPayload) {
        this.maximumPayload = maximumPayload;
    }
}
