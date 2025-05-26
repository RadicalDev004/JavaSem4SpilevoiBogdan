package org.example.game.client;

import org.example.game.client.gui.HexClientGUI;
import org.example.game.client.gui.HexMenu;
import org.example.game.client.gui.LoginScreen;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class GameClient {
    public static HexMenu hexMenu;
    public static LoginScreen loginScreen;
    private static String gameId;
    private static String playerName;
    private static PrintWriter out;
    private static int player = 0;
    private  static int turn;
    public static boolean started;
    public static void main(String[] args) {
        loginScreen = new LoginScreen();
        //hexMenu = new HexMenu(e -> connectToServer());
    }

    public static void connectToServer()
    {
        if(hexMenu.getPlayerName().isEmpty())
        {
            return;
        }

        playerName = hexMenu.getPlayerName();
        String host = "localhost";
        int port = 1234;


        HexClientGUI gui = new HexClientGUI();
        hexMenu.dispose();
        gui.setPlayerName(player, "Waiting for player to join...", -1);

        new Thread(() -> {
            try (
                    Socket socket = new Socket("localhost", 1234);
                    PrintWriter out1 = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                gui.log("Created game.", 5000);
                out = out1;
                out.println("room");

                String response;
                while ((response = in.readLine()) != null) {
                    switch (response) {
                        case "BOARD" -> {
                            char[][] board = new char[11][11];
                            for (int i = 0; i < 11; i++) {
                                board[i] = in.readLine().toCharArray();
                            }
                            gui.updateBoard(board);
                            //gui.log("Board updated.");

                            turn++;
                            gui.log(turn%2==0 ? "Your move." : "Your opponents move.", 5000);
                        }
                        case "ROOM" -> {
                            response = in.readLine();
                            gameId = response.equals("-") ? makeId(10) : response;
                            out.println((response.equals("-") ? "create " : "join ") + gameId + " " + playerName + " 120");
                            player = response.equals("-") ? 0 : 1;
                            turn = response.equals("-") ? 0 : 1;
                        }
                        case "START" -> {
                            gui.setConnectionInfo(gameId, playerName, out, in);
                            gui.log("Game started.", 5000);
                            started = true;
                            response = in.readLine();
                            gui.setPlayerName(getOpposed(), response.split("\\|")[getOpposed()], player);
                            gui.setPlayerName(player, playerName, player);
                            gui.log(turn % 2 == 0 ? "Your move." : "Your opponents move.", 5000);
                        }
                        case "END" -> {
                            started = false;
                            String cause = in.readLine();
                            String playerSent = in.readLine();
                            System.out.println("END");
                            boolean playerWon = isWinner(playerSent, cause);

                            gui.showResult(playerWon ? "YOU WIN!" : "YOU LOSE.", reasonToCause(playerWon, cause));
                        }
                        default -> {
                            //gui.log("Server: " + response);
                        }
                    }
                }

            } catch (IOException e) {
                gui.log("Connection error: " + e.getMessage(), 5000);
            }

            //gui.log("Client disconnected.");
        }).start();

        new Thread(() -> {
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                if(gui.hasPlayerDepletedTime(player))
                {
                    loseOnTime();
                    break;
                }
                if(started)
                    gui.depleteSeconds(turn % 2 == 0 ? player : getOpposed(), 1);
            }
        }).start();

        //gui.log("Client disconnected.");
        System.out.println("Client disconnected.");
    }

    public static String makeId(int cnt)
    {
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        for(int i = cnt; i >= 0; i--)
        {
            builder.append((char)('0' +rnd.nextInt(0, 10)));
        }
        return builder.toString();
    }
    public static int getOpposed()
    {
        return player == 0 ? 1 : 0;
    }

    public static String reasonToCause(boolean playerWon, String reason)
    {
        return switch (reason)
        {
            case "trivial" -> playerWon ? "You Connected the 2 sides." : "Opponent connected the 2 sides.";
            case "timeout" -> playerWon ? "Opponent has timed out." : "You have timed out." ;
            case "exited" -> playerWon ? "Opponent has exited the game." : "You have exited the game.";
            default -> "-";
        };
    }

    public static boolean isWinner(String playerSent, String reason)
    {
        return switch (reason)
        {
            case "trivial" -> playerSent.equals(playerName);
            case "timeout", "exited" -> !playerSent.equals(playerName);
            default -> false;
        };
    }

    public static void loseOnTime()
    {
        out.println("timeout");
    }
}
