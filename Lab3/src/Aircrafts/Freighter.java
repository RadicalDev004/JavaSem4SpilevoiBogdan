package Aircrafts;

public class Freighter extends  Aircraft implements CargoCapable {
    private Integer MaximumPayload;

    public Freighter(String model, String name, Integer MaximumPayload) {
        super(model, name);
        this.MaximumPayload = MaximumPayload;
    }

    public Integer getMaximumPayload() {
        return MaximumPayload;
    }

    public void setMaximumPayload(Integer maximumPayload) {
        MaximumPayload = maximumPayload;
    }
}
