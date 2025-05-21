package org.example.game.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        HexClientGUI gui = new HexClientGUI();

        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)
        ) {
            gui.log("Connected to server.");
            System.out.println("Connected to server. Type commands (type 'exit' to quit):");

            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        if (response.equals("BOARD")) {
                            char[][] board = new char[11][11];
                            for (int i = 0; i < 11; i++) {
                                board[i] = in.readLine().toCharArray();
                            }
                            gui.updateBoard(board);
                            gui.log("Board updated.");
                        }
                        else {
                            gui.log("Server: " + response);
                        }
                    }
                } catch (IOException e) {
                    gui.log("Connection error: " + e.getMessage());
                }
            }).start();

            while (true) {
                System.out.print(">> ");
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command)) break;

                out.println(command);

                if (command.startsWith("create ") || command.startsWith("join ") || command.startsWith("ai")) {
                    String[] parts = command.split(" ");
                    if (parts.length >= 3) {
                        String gameId = parts[1];
                        String playerName = parts[2];
                        gui.setConnectionInfo(gameId, playerName, out, in);
                    }
                }
            }

        } catch (IOException e) {
            gui.log("Error: " + e.getMessage());
            System.err.println("Error: " + e.getMessage());
        }

        gui.log("Client disconnected.");
        System.out.println("Client disconnected.");
    }
}
