package org.example;

import org.example.panels.ConfigPanel;
import org.example.panels.ControlPanel;
import org.example.panels.DotsGame;
import org.example.panels.DrawingPanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        DotsGame game = new DotsGame();
        SwingUtilities.invokeLater(() -> game.setVisible(true));
    }
}