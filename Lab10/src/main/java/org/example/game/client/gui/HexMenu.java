package org.example.game.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HexMenu extends JFrame {
    private final JButton playButton = new JButton("Play");
    private String username;

    private CardLayout cardLayout = new CardLayout();
    private JPanel centerPanel = new JPanel(cardLayout);

    private int currentPanel = 0; // 0: main, 1: settings, 2: leaderboard

    // Panels
    private JPanel mainPanel;
    private JPanel settingsPanel;
    private JPanel leaderboardPanel;

    public HexMenu(String username) {
        this(username, null);
    }

    public HexMenu(String username, ActionListener onPlay) {
        this.username = username;

        setTitle("Hex Game Menu");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JButton settingsButton = new JButton("⚙");
        settingsButton.setPreferredSize(new Dimension(60, 40));
        settingsButton.addActionListener(e -> {
            String currentCard = getCurrentCard();
            if (currentPanel != 1) {
                cardLayout.show(centerPanel, "settings");
                currentPanel = 1;
            } else {
                cardLayout.show(centerPanel, "main");
                currentPanel = 0;
            }
        });
        topPanel.add(settingsButton, BorderLayout.WEST);

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        topPanel.add(userLabel, BorderLayout.CENTER);

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setPreferredSize(new Dimension(120, 40));
        leaderboardButton.addActionListener(e -> {
            String currentCard = getCurrentCard();
            if (currentPanel != 2) {
                cardLayout.show(centerPanel, "leaderboard");
                currentPanel = 2;
            } else {
                cardLayout.show(centerPanel, "main");
                currentPanel = 0;
            }
        });
        topPanel.add(leaderboardButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Main
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 100, 20));

        JLabel title = new JLabel("Hex Game");
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        playButton.setPreferredSize(new Dimension(250, 50));
        playButton.setMaximumSize(new Dimension(250, 50));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        if (onPlay != null) {
            playButton.addActionListener(onPlay);
        }

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(playButton);

        // Settings
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        JLabel settingsTitle = new JLabel(username);
        settingsTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        settingsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel winsLabel = new JLabel("Wins: 0");
        winsLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        winsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lossesLabel = new JLabel("Losses: 0");
        lossesLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        lossesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        logoutButton.setPreferredSize(new Dimension(250, 50));
        logoutButton.setMaximumSize(new Dimension(250, 50));
        logoutButton.addActionListener(e -> logout());

        settingsPanel.add(settingsTitle);
        settingsPanel.add(Box.createVerticalStrut(30));
        settingsPanel.add(winsLabel);
        settingsPanel.add(Box.createVerticalStrut(10));
        settingsPanel.add(lossesLabel);
        settingsPanel.add(Box.createVerticalStrut(10));
        settingsPanel.add(pointsLabel);
        settingsPanel.add(Box.createVerticalStrut(50));
        settingsPanel.add(logoutButton);

        //Leaderboard
        leaderboardPanel = new JPanel(new BorderLayout());
        JLabel leaderboardTitle = new JLabel("Leaderboard", SwingConstants.CENTER);
        leaderboardTitle.setFont(new Font("SansSerif", Font.BOLD, 32));
        leaderboardPanel.add(leaderboardTitle, BorderLayout.NORTH);


        //TODO Add players
        String[] players = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        int[] points = {1500, 1400, 1300, 1200, 1100};

        JPanel playersList = new JPanel();
        playersList.setLayout(new BoxLayout(playersList, BoxLayout.Y_AXIS));
        for (int i = 0; i < players.length; i++) {
            JPanel playerPanel = new JPanel(new GridLayout(1, 3));
            playerPanel.setMaximumSize(new Dimension(600, 30));
            playerPanel.add(new JLabel((i + 1) + ".", SwingConstants.LEFT));
            playerPanel.add(new JLabel(players[i], SwingConstants.CENTER));
            playerPanel.add(new JLabel(points[i] + " pts", SwingConstants.RIGHT));
            playersList.add(playerPanel);
        }

        JScrollPane scrollPane = new JScrollPane(playersList);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(mainPanel, "main");
        centerPanel.add(settingsPanel, "settings");
        centerPanel.add(leaderboardPanel, "leaderboard");
        add(centerPanel, BorderLayout.CENTER);

        cardLayout.show(centerPanel, "main");

        setVisible(true);
    }

    public String getPlayerName() {
        return username;
    }

    private void logout() {
        JOptionPane.showMessageDialog(this, "Logging out...");
        dispose();
        new LoginScreen();
    }

    private String getCurrentCard() {
        for (Component comp : centerPanel.getComponents()) {
            if (comp.isVisible()) {
                if (comp == mainPanel) {
                    return "main";
                } else if (comp == settingsPanel) {
                    return "settings";
                } else if (comp == leaderboardPanel) {
                    return "leaderboard";
                }
            }
        }
        return "main";
    }
}
