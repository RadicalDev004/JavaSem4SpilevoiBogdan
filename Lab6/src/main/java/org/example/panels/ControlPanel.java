package org.example.panels;

import org.example.helper.GameManager;

import javax.swing.*;
import java.io.IOException;

public class ControlPanel extends JPanel {
    public ControlPanel() {
        //JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");
        JButton exportButton = new JButton("Export");
        exitButton.addActionListener(e -> System.exit(0));

        add(exportButton);
        add(saveButton);
        add(exitButton);

        GameManager.controlPanel = this;

        saveButton.addActionListener(e -> {
            try {
                GameManager.saveGame();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
        exitButton.addActionListener(e -> {
            GameManager.dotsGame.setVisible(false);
        });
        exportButton.addActionListener(e -> {
            GameManager.exportGamePng();
        });
    }
}