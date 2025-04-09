package org.example.panels;

import org.example.helper.GameManager;

import javax.swing.*;
import java.awt.*;

public class DotsGame extends JFrame {
    private final ConfigPanel configPanel;
    private final DrawingPanel drawingPanel;
    private final ControlPanel controlPanel;
    private final StaticPanel staticPanel;
    private final JLayeredPane layeredPane;

    public DotsGame() {
        setTitle("Dots Connection Game");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel();
        controlPanel = new ControlPanel();
        staticPanel = new StaticPanel();
        drawingPanel = new DrawingPanel(staticPanel);

        staticPanel.setBounds(17, 15, 800, 500); // leave room for controlPanel
        drawingPanel.setBounds(17, 15, 800, 500);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 500));
        layeredPane.add(staticPanel, Integer.valueOf(0));
        layeredPane.add(drawingPanel, Integer.valueOf(1));

        add(configPanel, BorderLayout.NORTH);
        add(layeredPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        configPanel.startButton.addActionListener(e -> {
            int numDots = configPanel.getNumberOfDots();

            drawingPanel.generateDots(numDots);

            configPanel.startButton.setVisible(false);
            configPanel.dotsField.setVisible(false);
            configPanel.label.setVisible(false);
        });


        GameManager.ConfigPanel = configPanel;
    }

    public ConfigPanel getConfigPanel() {
        return configPanel;
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public StaticPanel getStaticPanel() {
        return staticPanel;
    }

    @Override
    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }
}
