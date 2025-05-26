package org.example.game.client.gui;

import javax.swing.*;
import java.awt.*;

class TimeCircle extends JPanel {
    private int remainingSeconds;
    private int totalSeconds;
    private Color color;
    private boolean expired = false;

    public TimeCircle(int totalSeconds, Color color) {
        this.totalSeconds = Math.max(1, totalSeconds);
        this.remainingSeconds = this.totalSeconds;
        this.color = color;
        setOpaque(false);
    }

    public void setTime(int seconds) {
        if(expired) return;
        this.remainingSeconds = Math.max(0, Math.min(seconds, totalSeconds));
        repaint();
    }

    public boolean isExpired() {
        return expired;
    }

    public void setTimeMinus(int seconds) {
        if(expired) return;
        this.remainingSeconds = this.remainingSeconds - seconds;
        if(remainingSeconds == 0) {
            expired = true;
        }
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(40, 40);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int size = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the background circle
        g2.setColor(Color.DARK_GRAY);
        g2.fillOval(x, y, size, size);

        // Draw the time arc
        double angle = 360.0 * remainingSeconds / totalSeconds;
        g2.setColor(color);
        g2.fillArc(x, y, size, size, 90, -(int) angle); // Clockwise from top

        g2.dispose();
    }
}
