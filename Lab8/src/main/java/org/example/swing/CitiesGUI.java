package org.example.swing;


import org.example.helper.Pair;

import javax.swing.*;
import java.awt.*;

public class CitiesGUI extends JFrame {
    private final DrawingPanel drawingPanel;

    public CitiesGUI() {
        this.drawingPanel = new DrawingPanel();
        setTitle("Cities");
        setSize(850, 650);
        drawingPanel.setBounds(17, 15, 800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(drawingPanel);
    }

    public void addDot(int x, int y) {
        drawingPanel.addDot(x, y);
    }
    public void addDot(Pair<Integer, Integer> pr) {
        drawingPanel.addDot(pr.getFirst(), pr.getSecond());
        System.out.println("Added dot: " + pr.getFirst() + " " + pr.getSecond());
    }
    public void addLine(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2)
    {
        drawingPanel.connectDots(p1, p2);
    }

}
