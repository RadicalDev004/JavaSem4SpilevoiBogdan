package org.example.panels;

import org.example.helper.GameManager;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class ConfigPanel extends JPanel {
    public JTextField dotsField;
    public JButton startButton;
    public JLabel player1, player2, compare;
    public JLabel label;

    public ConfigPanel() {
        setLayout(new BorderLayout());

        label = new JLabel("Number of dots:");
        dotsField = new JTextField("10", 5);
        startButton = new JButton("New Game");

        player1 = new JLabel("P1: 0");
        player2 = new JLabel("P2: 0");
        compare = new JLabel("Score: Best:");

        player1.setBorder(BorderFactory.createEmptyBorder(0, 75, 0, 0)); // top, left, bottom, right
        player2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 125));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(label);
        centerPanel.add(dotsField);
        centerPanel.add(startButton);
        centerPanel.add(compare);

        add(player1, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(player2, BorderLayout.EAST);
    }

    public int getNumberOfDots() {
        try {
            return Integer.parseInt(dotsField.getText());
        } catch (NumberFormatException e) {
            return 10;
        }
    }
    public void ChangeScore(boolean player, double playerScore, double score, double best)
    {
        (player ? player1 : player2).setText((player ? "P1: " : "P2: ") + String.format("%.2f", playerScore));
        compare.setText("Score: " + String.format("%.2f", score) + " Best: " + String.format("%.2f", best));
    }

    public void endGame()
    {
        String winner = GameManager.score1 < GameManager.score2 ? "Player 1 wins!" : "Player 2 wins!";
        compare.setText(winner);
    }
}
