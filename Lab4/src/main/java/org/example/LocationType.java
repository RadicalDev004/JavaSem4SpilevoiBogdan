package org.example;

public enum LocationType {
    friendly(0),
    neutral (1),
    enemy(2);
    private final int value;

    LocationType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static LocationType fromValue(int value) {
        return switch (value) {
            case 1 -> neutral;
            case 2 -> enemy;
            default -> friendly;
        };
    }
}
