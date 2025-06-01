package org.example.analyzer;
import org.example.annotations.Test;

import java.lang.reflect.*;

public class ClassAnalyzer {

    public static void analyze(Class<?> clazz) {
        System.out.println("\nCompiled from \"" + clazz.getSimpleName() + ".java\"");
        System.out.print(Modifier.toString(clazz.getModifiers()) + " class " + clazz.getSimpleName());

        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            System.out.print(" extends " + clazz.getSuperclass().getName());
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            System.out.print(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                System.out.print(interfaces[i].getName());
                if (i < interfaces.length - 1) System.out.print(", ");
            }
        }

        System.out.println(" {\n");

        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("  " + Modifier.toString(field.getModifiers()) + " " +
                    field.getType().getSimpleName() + " " + field.getName() + ";");
        }

        for (Constructor<?> ctor : clazz.getDeclaredConstructors()) {
            System.out.println("  " + Modifier.toString(ctor.getModifiers()) + " " +
                    clazz.getSimpleName() + getParamList(ctor.getParameterTypes()) + ";");
        }

        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("  " + Modifier.toString(method.getModifiers()) + " " +
                    method.getReturnType().getSimpleName() + " " + method.getName() +
                    getParamList(method.getParameterTypes()) + ";");
        }

        System.out.println("}");
        TestRunner.run(clazz);
    }

    private static String getParamList(Class<?>[] paramTypes) {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < paramTypes.length; i++) {
            sb.append(paramTypes[i].getSimpleName());
            if (i < paramTypes.length - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

}