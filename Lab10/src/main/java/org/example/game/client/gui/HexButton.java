package org.example.game.client.gui;

import javax.swing.*;
import java.awt.*;

public class HexButton extends JButton {
    private Polygon hex;


    public HexButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setMargin(new Insets(5, 5, 5, 5));
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        int radius = w / 2 - 4;
        int centerX = w / 2;
        int centerY = h / 2;

        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30);
            xPoints[i] = centerX + (int) (radius * Math.cos(angle));
            yPoints[i] = centerY + (int) (radius * Math.sin(angle));
        }

        hex = new Polygon(xPoints, yPoints, 6);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillPolygon(hex);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(hex);

        g2.setColor(Color.BLACK);
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int tx = (w - fm.stringWidth(text)) / 2;
        int ty = (h + fm.getAscent()) / 2 - 3;
        g2.drawString(text, tx, ty);

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        return hex != null && hex.contains(x, y);
    }
}
