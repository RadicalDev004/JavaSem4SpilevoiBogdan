package org.example.game.client.gui;

import javax.swing.*;
import java.awt.*;

public class ResultsOverlay extends JPanel {
    private String message;
    private String reason;
    private Color backgroundColor;

    public ResultsOverlay(String message, String reason, Color backgroundColor) {
        this.message = message;
        this.reason = reason;
        this.backgroundColor = backgroundColor;
        setOpaque(false); // Transparent background
    }

    public void setResult(String message, String reason) {
        this.message = message;
        this.reason = reason;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        // Semi-transparent overlay
        g2.setColor(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 128));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Main text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 64));
        int textWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, (getWidth() - textWidth) / 2, getHeight() / 2);

        // Reason text
        g2.setFont(new Font("SansSerif", Font.PLAIN, 24));
        textWidth = g2.getFontMetrics().stringWidth(reason);
        g2.drawString(reason, (getWidth() - textWidth) / 2, (getHeight() / 2) + 50);

        g2.dispose();
    }
}
