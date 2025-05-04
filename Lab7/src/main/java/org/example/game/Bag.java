package org.example.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Bag {
    private List<Letter> allLetters = new ArrayList<>();

    public Bag(int letterCount) {
        Random rand = new Random();
        for(int i = 0; i < letterCount; i++) {
            allLetters.add(new Letter(getRandomLetter(), Math.abs(rand.nextInt() % 101)));
        }
    }

    public Letter extractLetter() {
        Letter l = allLetters.get(allLetters.size()-1);
        allLetters.remove(allLetters.size()-1);
        return l;
    }

    public List<Letter> extractLetters(int cnt) {
        List<Letter> l = new ArrayList<>();
        if(cnt > allLetters.size()) cnt = allLetters.size() - 1;
        while(cnt > 0) {
            l.add(extractLetter());
            cnt--;
        }
        return l;
    }

    private char getRandomLetter() {
        int min = 97;
        int max = 123;
        int random = ThreadLocalRandom.current().nextInt(min, max);
        return (char)random;
    }
}
