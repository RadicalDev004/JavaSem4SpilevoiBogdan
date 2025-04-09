package org.example.helper;

import javax.swing.*;
import java.awt.*;

public class CircleButton extends JButton {

    private int diameter;


    public int getDiameter() {
        return diameter;
    }

    public CircleButton(int x, int y, int diameter) {
        this.diameter = diameter;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter, diameter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillOval(0, 0, diameter - 1, diameter - 1);

        g.setColor(getForeground());
        g.drawOval(0, 0, diameter - 1, diameter - 1);

        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = diameter / 2;
        int centerX = radius;
        int centerY = radius;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= radius * radius;
    }
}
