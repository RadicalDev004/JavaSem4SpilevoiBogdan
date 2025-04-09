package org.example.helper;

public class CircleButtonSerialized {
    private int posX, posY, diameter;

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public CircleButtonSerialized(CircleButton cb) {
        posX = cb.getX();
        posY = cb.getY();
        diameter = cb.getWidth();
    }
    public CircleButtonSerialized()
    {

    }
}
