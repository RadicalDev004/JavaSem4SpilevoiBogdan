package org.example;

public class LocationEdge {
    private int time,probability;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "LocationEdge{" +
                "time=" + time +
                ", probability=" + probability +
                '}';
    }

    public LocationEdge(int time, int probability) {
        this.time = time;
        this.probability = probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }
}
