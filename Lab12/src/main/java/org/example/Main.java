package org.example;

import org.example.analyzer.ClassAnalyzer;
import org.example.analyzer.ClassExplorer;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <full.class.Name>");
            return;
        }

        String fullClassName = args[0];
        ClassExplorer.explore(fullClassName);
    }
}