package org.example.game.client.gui;

import org.example.game.client.GameClient;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class HexClientGUI extends JFrame {
    private final int SIZE = 11;
    private final JButton[][] buttons = new JButton[SIZE][SIZE];
    //private final JTextArea logArea = new JTextArea();

    private final JLabel playerXLabel = new JLabel("-");
    private final JLabel playerOLabel = new JLabel("-");

    private JPanel boardPanel;

    private final TimeCircle playerXTimer = new TimeCircle(120, Color.RED);
    private final TimeCircle playerOTimer = new TimeCircle(120, Color.BLUE);

    private ResultsOverlay resultOverlay;

    private final JLabel logLabel = new JLabel("");

    private Timer clearLogTimer;

    public void depleteSeconds(int player, int seconds) {
        if(player == 0) {
            playerXTimer.setTimeMinus(seconds);
        }
        else {
            playerOTimer.setTimeMinus(seconds);
        }
    }

    public boolean hasPlayerDepletedTime(int player) {
        if(player == 0) return playerXTimer.isExpired();
        else return playerOTimer.isExpired();
    }

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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if(GameClient.started)
                    out.println("exited");
                dispose();
            }
        });
        setSize(800, 800);
        setLayout(new BorderLayout());

        boardPanel = new JPanel(null) {
            @Override
            protected void paintChildren(Graphics g) {
                super.paintChildren(g);

                int offset = 20; // consistent for all 4 sides

                Polygon redTop = new Polygon();
                Polygon redBottom = new Polygon();
                Polygon blueLeft = new Polygon();
                Polygon blueRight = new Polygon();

                int hexW = buttons[0][0].getWidth();
                int hexH = buttons[0][0].getHeight();

                for (int j = 0; j < SIZE; j++) {
                    Rectangle b = buttons[0][j].getBounds();
                    redTop.addPoint(b.x, b.y - offset -5);
                }
                for (int j = SIZE - 1; j >= 0; j--) {
                    Rectangle b = buttons[0][j].getBounds();
                    redTop.addPoint(b.x + hexW, b.y - 5);
                }


                for (int j = 0; j < SIZE; j++) {
                    Rectangle b = buttons[SIZE - 1][j].getBounds();
                    redBottom.addPoint(b.x, b.y + hexH + 5);
                }
                for (int j = SIZE - 1; j >= 0; j--) {
                    Rectangle b = buttons[SIZE - 1][j].getBounds();
                    redBottom.addPoint(b.x + hexW, b.y + hexH + offset + 5);
                }

                for (int i = 0; i < SIZE; i++) {
                    Rectangle b = buttons[i][0].getBounds();
                    blueLeft.addPoint(b.x - offset, b.y);
                }
                for (int i = SIZE - 1; i >= 0; i--) {
                    Rectangle b = buttons[i][0].getBounds();
                    blueLeft.addPoint(b.x - offset, b.y + hexH);
                }

                for (int i = 0; i < SIZE; i++) {
                    Rectangle b = buttons[i][SIZE - 1].getBounds();
                    blueRight.addPoint(b.x + hexW + offset, b.y);
                }
                for (int i = SIZE - 1; i >= 0; i--) {
                    Rectangle b = buttons[i][SIZE - 1].getBounds();
                    blueRight.addPoint(b.x + hexW + offset, b.y + hexH);
                }

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(new Color(255, 0, 0, 255));
                g2.fillPolygon(redTop);
                g2.fillPolygon(redBottom);

                g2.setColor(new Color(0, 0, 255, 255));
                g2.fillPolygon(blueLeft);
                g2.fillPolygon(blueRight);

                g2.dispose();

            }
        };

        playerXTimer.setBounds(0, 10, 40, 40);
        boardPanel.add(playerXTimer);

        playerOTimer.setBounds(745, 10, 40, 40); // adjust based on layout
        boardPanel.add(playerOTimer);

        playerXLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        playerXLabel.setForeground(Color.WHITE);
        playerXLabel.setBounds(50, 15, 200, 30);
        boardPanel.add(playerXLabel);

        playerOLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        playerOLabel.setForeground(Color.WHITE);
        playerOLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerOLabel.setBounds(525, 10, 200, 30);
        boardPanel.add(playerOLabel);

        resultOverlay = new ResultsOverlay("", "", Color.BLACK);
        resultOverlay.setVisible(false);

        resultOverlay.setBounds(0, 0, boardPanel.getWidth(), boardPanel.getHeight());

        boardPanel.add(resultOverlay);
        boardPanel.setComponentZOrder(resultOverlay, 0);

        boardPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                resultOverlay.setBounds(0, 0, boardPanel.getWidth(), boardPanel.getHeight());
            }
        });

        boardPanel.setPreferredSize(new Dimension(1200, 1000));
        boardPanel.setBackground(Color.LIGHT_GRAY);
        add(boardPanel, BorderLayout.CENTER);

        int radius = 30;
        int hexHeight = (int) (Math.sqrt(3) * radius);
        int hexWidth = 2 * radius;
        int horizontalSpacing = (int) (1.5 * radius);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                HexButton button = new HexButton("");
                button.setFont(new Font("Monospaced", Font.BOLD, 14));
                button.setMargin(new Insets(5, 5, 5, 5));
                button.setEnabled(true);

                int x = 25 + j * horizontalSpacing + (i * horizontalSpacing) / 2;
                int y = 75 + i * (int)(0.64 * hexWidth);

                int finalI = i;
                int finalJ = j;

                button.addActionListener(e -> {
                    if (out != null && gameId != null && playerName != null) {
                        String moveCommand = "move " + gameId + " " + playerName + " " + finalI + " " + finalJ;
                        out.println(moveCommand);
                        //log("Clicked: " + moveCommand);
                    }
                });

                button.setBounds(x, y, hexWidth, hexHeight);
                boardPanel.add(button);
                buttons[i][j] = button;
            }
        }


        logLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        logLabel.setForeground(Color.WHITE);
        logLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logLabel.setOpaque(true);
        logLabel.setBackground(Color.DARK_GRAY);
        logLabel.setPreferredSize(new Dimension(800, 120));

        add(logLabel, BorderLayout.SOUTH);

        add(boardPanel, BorderLayout.CENTER);
        add(logLabel, BorderLayout.SOUTH);


        setVisible(true);
    }

    public void updateBoard(char[][] board) {
        System.out.println("new Board" + Arrays.deepToString(board));
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
            {
                //buttons[i][j].setText(!Character.toString(board[i][j]).equals(".") ? Character.toString(board[i][j]) : "");
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

    public void log(String message, int delay) {
        SwingUtilities.invokeLater(() -> {
            logLabel.setText(message);

            if (clearLogTimer != null && clearLogTimer.isRunning()) {
                clearLogTimer.stop();
            }
            if(delay == -1) return;

            clearLogTimer = new Timer(delay, e -> logLabel.setText(""));
            clearLogTimer.setRepeats(false);
            clearLogTimer.start();
        });
    }

    public void setPlayerXName(String name) {
        playerXLabel.setText(name);
    }

    public void setPlayerName(int player, String name, int currentPlayer)
    {
        if(currentPlayer >= 0)
        {
            if(player == currentPlayer) name = "You: " + name;
            else name = "Opponent: " + name;
        }



        if(player == 1) setPlayerOName(name);
        if(player == 0) setPlayerXName(name);
    }
    public void setPlayerOName(String name) {
        playerOLabel.setText(name);
    }

    public void showResult(String message, String reason) {
        resultOverlay.setResult(message, reason);
        resultOverlay.setVisible(true);
        boardPanel.repaint();
        boardPanel.revalidate();
    }

    public void hideResult() {
        resultOverlay.setVisible(false);
        boardPanel.repaint();
        boardPanel.revalidate();
    }

}

