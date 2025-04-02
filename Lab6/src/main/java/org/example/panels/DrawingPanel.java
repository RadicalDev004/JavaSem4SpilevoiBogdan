package org.example.panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private final ArrayList<Point> dots = new ArrayList<>();
    private final Random random = new Random();

    public DrawingPanel() {
        setBackground(Color.WHITE);
    }

    public void generateDots(int num) {
        dots.clear();
        for (int i = 0; i < num; i++) {
            int x = random.nextInt(getWidth() - 20) + 10;
            int y = random.nextInt(getHeight() - 20) + 10;
            dots.add(new Point(x, y));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Point dot : dots) {
            g.fillOval(dot.x - 5, dot.y - 5, 10, 10);
        }
    }
}
