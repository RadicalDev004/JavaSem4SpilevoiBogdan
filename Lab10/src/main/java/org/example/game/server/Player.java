package org.example.game.server;

public class Player {
    private final String name;
    private final char symbol; // 'X' or 'O'
    private long timeLeftMillis;

    public Player(String name, char symbol, int timeSeconds) {
        this.name = name;
        this.symbol = symbol;
        this.timeLeftMillis = timeSeconds * 1000L;
    }

    public String getName() { return name; }
    public char getSymbol() { return symbol; }
    public long getTimeLeftMillis() { return timeLeftMillis; }
    public void deductTime(long elapsed) { timeLeftMillis -= elapsed; }
}
