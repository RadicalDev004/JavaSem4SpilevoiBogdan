package org.example.game;

public class Letter {
    private char letter;
    private int value;
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Letter(char letter, int value) {
        this.letter = letter;
        this.value = value;
        used = false;
    }

    public char getLetter() {
        return letter;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return letter + ": " + value;
    }
}
