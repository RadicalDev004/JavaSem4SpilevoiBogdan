package org.example.swing;


import org.example.helper.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DrawingPanel extends JPanel {

    private final List<Point> dots = new ArrayList<>();
    private final List<Line> lines = new ArrayList<>();

    private final int radius = 5;

    public void addDot(int x, int y) {
        dots.add(new Point(x, y));
        repaint();
    }

    public DrawingPanel() {
    }

    public void connectDots(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {

            lines.add(new Line(new Pair<>(new Point(p1.getFirst(), p1.getSecond()), new Point(p2.getFirst(), p2.getSecond()))));
            repaint();
    }

    public int getIndexOfDot(Point p) {
        return dots.indexOf(p);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for (Point dot : dots) {
            g.fillOval(dot.x - radius, dot.y - radius, 2 * radius, 2 * radius);
        }
        g.setColor(Color.blue);
        for (Line line : lines) {
            g.drawLine(line.getInfo().getFirst().x, line.getInfo().getFirst().y, line.getInfo().getSecond().x, line.getInfo().getSecond().y);
        }
    }


}
