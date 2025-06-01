package org.example.analyzer;


import java.io.File;

public class ClassExplorer {

    public static void explore(String input) {
        File f = new File(input);

        try {
            if (f.exists()) {
                if (f.isDirectory()) {
                    ClassScanner.scanDirectory(f);
                } else if (input.endsWith(".jar")) {
                    ClassScanner.scanJar(f);
                } else if (input.endsWith(".class")) {
                    ClassScanner.scanClassFile(f);
                } else {
                    System.err.println("Unsupported file type.");
                }
            } else {
                System.out.println("Class not found: " + input);
                ClassScanner.findAndProcessBySimpleName(input, new File(System.getProperty("user.dir")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
