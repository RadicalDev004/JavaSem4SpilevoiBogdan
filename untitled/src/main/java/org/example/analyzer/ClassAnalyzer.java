package org.example.analyzer;

import org.example.annotations.Test;

import java.lang.reflect.*;

public class ClassAnalyzer {

    public static void analyzeClass(String fullClassName) {
        try {
            Class<?> clazz = Class.forName(fullClassName);

            System.out.println("Class: " + clazz.getName());
            System.out.println("\nMethods:");

            for (Method method : clazz.getDeclaredMethods()) {
                System.out.println(" - " + Modifier.toString(method.getModifiers()) + " " +
                        method.getReturnType().getSimpleName() + " " +
                        method.getName() + getParametersAsString(method));

                if (method.isAnnotationPresent(Test.class) &&
                        Modifier.isStatic(method.getModifiers()) &&
                        method.getParameterCount() == 0) {
                    System.out.println("   -> @Test annotated. Invoking...");
                    method.setAccessible(true);
                    method.invoke(null);
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + fullClassName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getParametersAsString(Method method) {
        StringBuilder sb = new StringBuilder("(");
        Parameter[] params = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            sb.append(params[i].getType().getSimpleName());
            if (i < params.length - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}