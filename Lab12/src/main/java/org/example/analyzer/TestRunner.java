package org.example.analyzer;

import org.example.annotations.Test;

import java.lang.reflect.*;

public class TestRunner {

    public static void run(Class<?> clazz) {
        boolean classHasTestAnnotation = clazz.isAnnotationPresent(Test.class);

        for (Method method : clazz.getDeclaredMethods()) {
            boolean methodIsTest = method.isAnnotationPresent(Test.class);

            if (classHasTestAnnotation || methodIsTest) {
                Object instance = null;

                try {
                    if (!Modifier.isStatic(method.getModifiers())) {
                        Constructor<?> ctor = clazz.getDeclaredConstructor();
                        ctor.setAccessible(true);
                        instance = ctor.newInstance();
                    }

                    Object[] args = generateMockArgs(method.getParameterTypes());

                    method.setAccessible(true);
                    System.out.println("Running test: " + method.getName());
                    method.invoke(instance, args);

                } catch (Exception e) {
                    System.err.println("Failed to run test " + method.getName() + ": " + e);
                }
            }
        }
    }

    private static Object[] generateMockArgs(Class<?>[] paramTypes) {
        Object[] args = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];

            if (type == int.class || type == Integer.class) {
                args[i] = 0;
            } else if (type == boolean.class || type == Boolean.class) {
                args[i] = true;
            } else if (type == double.class || type == Double.class) {
                args[i] = 1.0;
            } else if (type == float.class || type == Float.class) {
                args[i] = 1.0f;
            } else if (type == long.class || type == Long.class) {
                args[i] = 1L;
            } else if (type == short.class || type == Short.class) {
                args[i] = (short) 1;
            } else if (type == byte.class || type == Byte.class) {
                args[i] = (byte) 1;
            } else if (type == char.class || type == Character.class) {
                args[i] = 'A';
            } else if (type == String.class) {
                args[i] = "test";
            } else {
                args[i] = null;
            }
        }

        return args;
    }
}