package org.example.panels;

import javax.swing.*;

public class ControlPanel extends JPanel {
    public ControlPanel() {
        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        add(loadButton);
        add(saveButton);
        add(exitButton);
    }
}