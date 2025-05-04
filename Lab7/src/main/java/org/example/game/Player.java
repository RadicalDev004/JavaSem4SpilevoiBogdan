package org.example.game;

import java.util.*;
import java.util.stream.Collectors;

public class Player implements Runnable {

    private String name;
    private Game game;
    private boolean running;
    private int score;
    private int maxWordScore;
    private List<Letter> currentLetters = new ArrayList<>();
    private List<Letter> afterMaxWord = new ArrayList<>();
    private String bestWord = "";
    private boolean naiveSearch = false;

    public int getScore() {
        return score;
    }

    public Player(String name, Game game, boolean naiveSearch) {
        this.name = name;
        this.game = game;
        this.naiveSearch = naiveSearch;
    }

    public boolean isRunning() {
        return running;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {

        running = true;
        if(Game.debug) System.out.println("Player " + name + " started");
        while (running) {
            synchronized (game.getTurnLock()) {
                while (!this.equals(game.getPlayers().get(game.getCurrentTurn()))) {
                    try {
                        game.getTurnLock().wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (!game.isRunning()) {
                    running = false;
                    game.getTurnLock().notifyAll();
                    return;
                }


                List<Letter> w = game.getBag().extractLetters(game.getLettersPerTurn() - currentLetters.size());
                currentLetters.addAll(w);
                if(Game.debug) System.out.println(name + ": " + currentLetters);
                if (w.isEmpty()) {
                    game.endGame();
                    running = false;
                    game.getTurnLock().notifyAll();
                    return;
                }

                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/

                int scr = 0;
                StringBuilder sb = new StringBuilder();

                maxWordScore = 0;
                String wrd = "";
                bestWord = "";

                if(!naiveSearch)
                    getMaxWord(new ArrayList<>(currentLetters), wrd, 0);
                else
                    getMaxWordNaive(new ArrayList<>(currentLetters), wrd, 0);

                if(bestWord.isEmpty())
                {
                    currentLetters.clear();
                    if(Game.debug) System.out.println("No word found, skipping turn");
                }
                else
                {
                    score += maxWordScore;
                    currentLetters = afterMaxWord;
                    if(Game.debug) System.out.println("Max word score: " + bestWord + " " + maxWordScore + ", " + score);
                    game.getBoard().addWord(this, bestWord);
                }

                game.nextTurn();
                game.getTurnLock().notifyAll();

            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getMaxWord(List<Letter> l, String wrd, int score)
    {
        int status = game.isWordPartValid(wrd);
        if(!Objects.equals(wrd, "") && status == -1) return;
        if(score > maxWordScore && status == 1) {
            maxWordScore = score;
            afterMaxWord = l.stream()
                    .filter(letter -> !letter.isUsed())
                    .collect(Collectors.toList());
            bestWord = wrd;
        }
        for(var c : l)
        {
            if(c.isUsed()) continue;
            c.setUsed(true);
            getMaxWord(l, wrd + c.getLetter(), score + c.getValue());
            c.setUsed(false);
        }
    }
    private void getMaxWordNaive(List<Letter> l, String wrd, int score)
    {
        if(score > maxWordScore && game.isWordValid(wrd)) {
            maxWordScore = score;
            afterMaxWord = l.stream()
                    .filter(letter -> !letter.isUsed())
                    .collect(Collectors.toList());
            bestWord = wrd;
        }
        for(var c : l)
        {
            if(c.isUsed()) continue;
            c.setUsed(true);
            getMaxWord(l, wrd + c.getLetter(), score + c.getValue());
            c.setUsed(false);
        }
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }
}
