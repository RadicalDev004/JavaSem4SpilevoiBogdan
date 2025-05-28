package org.example.game.client.gui;

import org.example.game.client.GameClient;
import org.example.game.client.dao.UserDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class LoginScreen extends JFrame {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton submitButton = new JButton("Login");
    private final JLabel infoLabel = new JLabel("", SwingConstants.CENTER);
    private Timer clearInfoTimer;
    public static Integer userId;

    public LoginScreen() {
        setTitle("Hex Game Login");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));

        JLabel title = new JLabel("Hex Game Login");
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField.setMaximumSize(new Dimension(200, 30));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField.setMaximumSize(new Dimension(200, 30));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener((ActionEvent e) -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                showInfo("Username or password cannot be empty.");
                return;
            }

            UserDao userDao = new UserDao();
            try {
                if(!userDao.getAllUsernames().contains(username))
                {
                    userDao.create(username, password);
                    return;
                }
                else
                {
                    if(!userDao.getPasswordByUsername(username).equals(password))
                    {
                        showInfo("Username or password does not match.");
                        return;
                    }
                }
                userId = UserDao.getUserIdByUsername(username);
                if(userId == null)
                {
                    showInfo("User not found.");
                    return;
                }
            } catch (SQLException ex) {
                showInfo("Something went wrong. Try again?");
                return;
            }


            GameClient.hexMenu = new HexMenu(username, el -> GameClient.connectToServer());
            dispose();
        });

        container.add(title);
        container.add(Box.createVerticalStrut(20));
        container.add(new JLabel("Username:"));
        container.add(usernameField);
        container.add(Box.createVerticalStrut(10));
        container.add(new JLabel("Password:"));
        container.add(passwordField);
        container.add(Box.createVerticalStrut(20));
        container.add(submitButton);

        add(container, BorderLayout.CENTER);

        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoLabel.setForeground(Color.RED);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(infoLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showInfo(String message) {
        infoLabel.setText(message);
        if (clearInfoTimer != null && clearInfoTimer.isRunning()) {
            clearInfoTimer.stop();
        }
        clearInfoTimer = new Timer(3000, e -> infoLabel.setText(""));
        clearInfoTimer.setRepeats(false);
        clearInfoTimer.start();
    }
}
