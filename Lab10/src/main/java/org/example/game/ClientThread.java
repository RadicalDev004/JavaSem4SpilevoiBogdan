package org.example.game;

import org.example.game.server.GameManager;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ClientThread extends Thread {
    private final Socket clientSocket;
    private String gameId, playerName;

    public ClientThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Received: " + request);

                /*if (request.equalsIgnoreCase("stop")) {
                    out.println("Server stopped");
                    System.exit(0);
                } else {
                    out.println("Server received the request: " + request);
                }*/
                String[] tokens = request.split(" ");
                String command = tokens[0];

                switch (command) {
                    case "create":
                        if (tokens.length != 4) {
                            out.println("Usage: create gameId playerName timeSeconds");
                        } else {
                            out.println(GameManager.createGame(tokens[1], tokens[2], Integer.parseInt(tokens[3]), out));
                            gameId = tokens[1];
                            playerName = tokens[2];
                        }
                        break;
                    case "join":
                        if (tokens.length != 4) {
                            out.println("Usage: join gameId playerName timeSeconds");
                        } else {
                            out.println(GameManager.joinGame(tokens[1], tokens[2], Integer.parseInt(tokens[3]), out));
                            gameId = tokens[1];
                            playerName = tokens[2];
                        }
                        break;
                    case "move":
                        if (tokens.length != 5) {
                            out.println("Usage: move gameId playerName x y");
                        } else {
                            int x = Integer.parseInt(tokens[3]);
                            int y = Integer.parseInt(tokens[4]);
                            String result = GameManager.submitMove(tokens[1], tokens[2], x, y);
                            System.out.println(result);
                            if(result.contains("Move accepted."))
                            {
                                GameManager.propagateBoardToAllObservers(gameId);
                                if(result.contains("WON"))
                                {
                                    GameManager.propagateInfoToAllObservers(gameId, "END");
                                    GameManager.propagateInfoToAllObservers(gameId, "trivial");
                                    GameManager.propagateInfoToAllObservers(gameId, playerName);
                                }
                            }
                            else
                            {
                                out.println(result);
                            }

                        }
                        break;
                    case "list":
                        out.println(GameManager.listGames());
                        break;
                    case "exited":
                        GameManager.propagateInfoToAllObservers(gameId, "END");
                        GameManager.propagateInfoToAllObservers(gameId, "exited");
                        GameManager.propagateInfoToAllObservers(gameId, playerName);
                        break;
                    case "ai":
                        out.println(GameManager.createAIGame(tokens[1], tokens[2], Integer.parseInt(tokens[3]), out));
                        gameId = tokens[1];
                        playerName = tokens[2];
                        break;
                    case "room":
                        out.println("ROOM");
                        out.println(GameManager.getEmptyRoomForJoin());
                        break;
                    case "timeout":
                        GameManager.propagateInfoToAllObservers(gameId, "END");
                        GameManager.propagateInfoToAllObservers(gameId, "timeout");
                        GameManager.propagateInfoToAllObservers(gameId, playerName);
                        break;
                    default:
                        out.println("Unknown command.");
                }

            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
