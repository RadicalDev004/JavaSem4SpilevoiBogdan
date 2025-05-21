package org.example.game.client;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class HexClientGUI extends JFrame {
    private final int SIZE = 11;
    private final JButton[][] buttons = new JButton[SIZE][SIZE];
    private final JTextArea logArea = new JTextArea();

    private String gameId;
    private String playerName;
    private PrintWriter out;
    private BufferedReader in;

    public void setConnectionInfo(String gameId, String playerName, PrintWriter out, BufferedReader in) {
        this.gameId = gameId;
        this.playerName = playerName;
        this.out = out;
        this.in = in;
    }


    public HexClientGUI() {
        setTitle("Hex GUI Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(null);  // absolute positioning
        boardPanel.setPreferredSize(new Dimension(1200, 1000));
        boardPanel.setBackground(Color.LIGHT_GRAY); // just for visibility
        add(boardPanel, BorderLayout.CENTER);

        int radius = 30;
        int hexHeight = (int) (Math.sqrt(3) * radius);
        int hexWidth = 2 * radius;
        int horizontalSpacing = (int) (1.5 * radius);
        int verticalSpacing = hexHeight;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                HexButton button = new HexButton("" + i + "," + j);
                button.setFont(new Font("Monospaced", Font.BOLD, 14));
                button.setMargin(new Insets(5, 5, 5, 5));
                button.setEnabled(true);

                int x = 25 + j * horizontalSpacing + (i * horizontalSpacing) / 2;
                int y = 50 + i * (int)(0.866 * hexWidth);

                int finalI = i;
                int finalJ = j;

                button.addActionListener(e -> {
                    if (out != null && gameId != null && playerName != null) {
                        String moveCommand = "move " + gameId + " " + playerName + " " + finalI + " " + finalJ;
                        out.println(moveCommand);
                        log("Clicked: " + moveCommand);
                    }
                });

                button.setBounds(x, y, hexWidth, hexHeight);
                boardPanel.add(button);
                buttons[i][j] = button;
            }
        }


        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(800, 120));

        add(boardPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);


        setVisible(true);
    }

    public void updateBoard(char[][] board) {
        System.out.println("new Board" + Arrays.deepToString(board));
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
            {
                buttons[i][j].setText(Character.toString(board[i][j]));
                if (board[i][j] == 'X') {
                    buttons[i][j].setBackground(Color.RED);
                } else if (board[i][j] == 'O') {
                    buttons[i][j].setBackground(Color.BLUE);
                } else {
                    buttons[i][j].setBackground(Color.WHITE);
                }
            }
        System.out.println("Board updated."  + Arrays.deepToString(board));
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }
}

