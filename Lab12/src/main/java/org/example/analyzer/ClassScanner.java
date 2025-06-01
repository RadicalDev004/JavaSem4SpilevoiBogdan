package org.example.analyzer;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;

public class ClassScanner {

    public static void scanDirectory(File rootDir) throws Exception {
        Files.walk(rootDir.toPath())
                .filter(p -> p.toString().endsWith(".class"))
                .forEach(path -> {
                    try {
                        scanClassFile(path.toFile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public static void scanJar(File jarFile) throws Exception {
        try (JarFile jar = new JarFile(jarFile)) {
            URL[] urls = { new URL("jar:file:" + jarFile.getAbsolutePath() + "!/") };
            try (URLClassLoader loader = new URLClassLoader(urls)) {
                jar.stream()
                        .filter(e -> e.getName().endsWith(".class"))
                        .forEach(e -> {
                            String className = e.getName().replace('/', '.').replace(".class", "");
                            try {
                                Class<?> clazz = loader.loadClass(className);
                                ClassAnalyzer.analyze(clazz);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
            }
        }
    }

    public static void scanClassFile(File classFile) throws Exception {
        File root = classFile.getParentFile();
        String fqcn = ClassNameUtil.resolve(classFile, root);
        try (URLClassLoader loader = new URLClassLoader(new URL[]{root.toURI().toURL()})) {
            Class<?> clazz = loader.loadClass(fqcn);
            ClassAnalyzer.analyze(clazz);
        }
    }

    public static void findAndProcessBySimpleName(String simpleName, File rootDir) throws Exception {
        Files.walk(rootDir.toPath())
                .filter(p -> p.toString().endsWith(simpleName + ".class"))
                .findFirst()
                .ifPresent(path -> {
                    try {
                        File classFile = path.toFile();
                        File baseDir = findBaseDir(classFile, simpleName);
                        File classpathRoot = getClassRootDir(classFile);
                        String fqcn = ClassNameUtil.resolve(classFile, classpathRoot);
                        try (URLClassLoader loader = new URLClassLoader(new URL[]{baseDir.toURI().toURL()})) {
                            Class<?> clazz = loader.loadClass(fqcn);
                            ClassAnalyzer.analyze(clazz);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private static File findBaseDir(File classFile, String simpleName) {
        File current = classFile.getParentFile();
        while (current != null && !new File(current, simpleName + ".java").exists()) {
            current = current.getParentFile();
        }
        return current != null ? current : classFile.getParentFile();
    }

    private static File getClassRootDir(File classFile) {

        File current = classFile.getParentFile();
        while (current != null && !current.getName().equals("classes")) {
            current = current.getParentFile();
        }
        return current != null ? current : classFile.getParentFile(); 
    }

}
