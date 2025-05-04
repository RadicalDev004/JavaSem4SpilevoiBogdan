package org.example.game;

import java.util.*;

public class Board {
    private List<String> words = new ArrayList<>();

    public  synchronized void addWord(Player player, String word) {
        words.add(word);
        if(Game.debug) System.out.println(player.getName() + ": " + word);
    }

    @Override public String toString() {
        return words.toString();
    }
}
