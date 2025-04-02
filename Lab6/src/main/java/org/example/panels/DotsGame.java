package org.example.panels;

import javax.swing.*;
import java.awt.*;

public class DotsGame extends JFrame {
    private final ConfigPanel configPanel;
    private final DrawingPanel drawingPanel;
    private final ControlPanel controlPanel;

    public DotsGame() {
        setTitle("Dots Connection Game");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel();
        drawingPanel = new DrawingPanel();
        controlPanel = new ControlPanel();

        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        configPanel.startButton.addActionListener(e -> {
            int numDots = configPanel.getNumberOfDots();
            drawingPanel.generateDots(numDots);
        });


    }
}
