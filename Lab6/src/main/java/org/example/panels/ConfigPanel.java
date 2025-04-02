package org.example.panels;

import javax.swing.*;

public class ConfigPanel extends JPanel {
    JTextField dotsField;
    JButton startButton;

    public ConfigPanel() {
        JLabel label = new JLabel("Number of dots:");
        dotsField = new JTextField("10", 5);
        startButton = new JButton("New Game");

        add(label);
        add(dotsField);
        add(startButton);
    }

    public int getNumberOfDots() {
        try {
            return Integer.parseInt(dotsField.getText());
        } catch (NumberFormatException e) {
            return 10;
        }
    }
}
