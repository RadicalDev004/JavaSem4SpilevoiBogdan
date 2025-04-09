package org.example.helper;

public class ConnectingLine {
    private int startX, startY, endX, endY, offset;
    private boolean turn;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public boolean isTurn() {
        return turn;
    }

    public int getOffset() {
        return offset;
    }

    public ConnectingLine()
    {

    }

    public ConnectingLine(int startX, int startY, int endX, int endY, int offset, boolean turn) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.turn = turn;
        this.offset = offset;
    }

    public boolean hasCircleButtonAtOneEnd(CircleButton cb)
    {
        if(startX == cb.getX() && startY == cb.getY()) return true;
        if(endX == cb.getX() && endY == cb.getY()) return true;
        return false;
    }
}
