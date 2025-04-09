package org.example.panels;

import org.example.helper.CircleButton;
import org.example.helper.CircleButtonSerialized;
import org.example.helper.ConnectingLine;
import org.example.helper.GameManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class StaticPanel extends JPanel {
    private BufferedImage staticLayer;

    public StaticPanel() {
        setBackground(Color.WHITE);
        staticLayer = new BufferedImage(800, 500, BufferedImage.TYPE_INT_ARGB);
        GameManager.staticPanel = this;
    }

    public void imprintLine(ConnectingLine line)
    {
        Graphics2D g2d = staticLayer.createGraphics();

        g2d.setColor(line.isTurn() ? Color.BLUE : Color.RED);
        g2d.drawLine(line.getStartX() + line.getOffset(), line.getStartY()+ line.getOffset(), line.getEndX()+ line.getOffset(), line.getEndY()+ line.getOffset());


        g2d.dispose();
        repaint();
    }
    public void imprintButton(CircleButton but)
    {
        Graphics2D g2d = staticLayer.createGraphics();

        g2d.setColor(Color.GRAY);
        g2d.fillOval(but.getX(), but.getY(), but.getDiameter(), but.getDiameter());
        repaint();
    }
    public void imprintButton(CircleButtonSerialized but)
    {
        Graphics2D g2d = staticLayer.createGraphics();

        g2d.setColor(Color.GRAY);
        g2d.fillOval(but.getPosX(), but.getPosY(), but.getDiameter(), but.getDiameter());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(staticLayer, 0, 0, null);
    }

    public void exportPng()
    {
        Graphics2D g2d = staticLayer.createGraphics();
        repaint();

        g2d.dispose();

        try {
            ImageIO.write(staticLayer, "png", new File(GameManager.savePath + "/" + GameManager.gameName + ".png"));
            System.out.println("Panel saved as: " + GameManager.savePath + "/" + GameManager.gameName);
        } catch (Exception e) {
            System.out.println("Error saving file " + e.getMessage());
        }
    }
}
