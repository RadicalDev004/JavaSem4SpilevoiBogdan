package org.example.panels;

import javax.swing.*;

public class MenuNewGame extends JPanel {
    JButton newGameButton;
    JTextField gameName;
    JTextField savePath;
    JToggleButton type;
    JTextField aiLevel;
    public MenuNewGame() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        newGameButton   = new JButton("New Game");
        gameName = new JTextField("My Game", 5);
        savePath = new JTextField("C:/", 5);
        type = new JToggleButton("AI");
        aiLevel = new JTextField("0");

        newGameButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameName.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        savePath.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        type.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        aiLevel.setAlignmentX(JComponent.CENTER_ALIGNMENT);


        panel.add(newGameButton);
        panel.add(gameName);
        panel.add(savePath);
        panel.add(type);
        panel.add(aiLevel);

        add(panel);

    }
}
