package org.example.game;

public class Timekeeper implements Runnable {
    private final Game game;
    private final long timeLimitMillis;

    public Timekeeper(Game game, long timeLimitSeconds) {
        this.game = game;
        this.timeLimitMillis = timeLimitSeconds * 1000;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        synchronized (game) {
            while (!game.ended) {
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("Elapsed time: " + elapsed / 1000 + "s");

                if (elapsed >= timeLimitMillis) {
                    System.out.println("Time limit reached. Ending game.");
                    game.endGame();
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

    }
}

