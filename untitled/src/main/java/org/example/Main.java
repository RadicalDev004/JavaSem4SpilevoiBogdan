package org.example;

import org.example.analyzer.ClassAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <full.class.Name>");
            return;
        }

        String fullClassName = args[0];
        ClassAnalyzer.analyzeClass(fullClassName);
    }
}