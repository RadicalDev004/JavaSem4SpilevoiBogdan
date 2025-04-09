package org.example.panels;

import org.example.helper.GameManager;

import javax.swing.*;
import java.awt.*;

public class MenuLoadGame extends JPanel{
    JButton loadGameButton;
    JTextField gamePath;

    public MenuLoadGame() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

       loadGameButton = new JButton("Load Game");
       gamePath = new JTextField("C:/",10);

       loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       gamePath.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(loadGameButton);
        panel.add(gamePath);

        add(panel, BorderLayout.CENTER);
    }
}
