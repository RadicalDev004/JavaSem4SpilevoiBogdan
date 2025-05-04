package org.example.game;

import java.util.*;

public class Game {
    private Board board;
    private Bag bag;
    private  Dictionary dict;
    private final Object turnLock = new Object();
    private boolean running = true;
    public boolean ended = false;
    private int currentTurn = 0;
    private int lettersPerTurn;
    public static boolean debug;

    public List<Player> getPlayers() {
        return players;
    }

    private final List<Player> players = new ArrayList<>();

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Object getTurnLock() {
        return turnLock;
    }

    public int getLettersPerTurn() {
        return lettersPerTurn;
    }

    public Game(int bagCount, int lettersPerTurn, Dictionary dict, boolean debug) {
        board = new Board();
        bag = new Bag(bagCount);
        this.dict = dict;
        this.lettersPerTurn = lettersPerTurn;
        Game.debug = debug;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void play(boolean naiveSearch, String ... names) {
        for(var name : names) {
            addPlayer(new Player(name, this, naiveSearch));
        }
        for (var player : players) {
            Thread thread = new Thread(player);
            thread.start();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public Bag getBag() {
        return bag;
    }

    public synchronized void endGame()
    {
        running = false;
        ended = true;
        Player winner = null;
        int max = 0;
        for (var player : players) {
            if(player.getScore() > max)
            {
                max = player.getScore();
                winner = player;
            }
        }
        System.out.println("Game ended: " + players + ". " + winner.getName() + " won with score " + winner.getScore());
    }

    public synchronized int getCurrentTurn() {
        return currentTurn;
    }

    public synchronized void nextTurn() {
        currentTurn = (currentTurn + 1) % players.size();
    }

    public synchronized boolean isWordValid(String word) {
        return dict.containsWord(word);
    }
    public synchronized int isWordPartValid(String word) {
        return dict.containsWordPart(word);
    }
}
