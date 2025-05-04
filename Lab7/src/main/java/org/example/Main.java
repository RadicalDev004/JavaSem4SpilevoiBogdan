package org.example;

import org.example.game.Dictionary;
import org.example.game.Game;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Dictionary dict = new Dictionary("C:/Users/Bogdan S/OneDrive/Documents/GitHub/Lab7/src/main/java/org/example/game/words_alpha.txt");
        long startTime = System.nanoTime();

        Game game = new Game(1000, 30, dict, false);
        boolean running = true;
        game.play(false,"pl1", "pl2", "pl3");

        while (running)
        {
            synchronized (game)
            {
                if(game.ended) {
                    running = false;
                    break;
                }
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("Elapsed time: " + elapsed / 1000 + "s");

                if (elapsed >= 1000 * 100) {
                    System.out.println("Time limit reached. Ending game.");
                    game.endGame();
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(game.getBoard());

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Execution time: " + (duration / 1_000_000.0) + " ms");
    }
}