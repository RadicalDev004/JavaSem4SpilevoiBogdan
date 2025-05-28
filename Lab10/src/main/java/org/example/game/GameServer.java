package org.example.game;

import org.example.game.database.Database;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private boolean running = true;

    public static void main(String[] args) {
        int port = 1234;
        Database.initSchema();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientThread(clientSocket).start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
}

