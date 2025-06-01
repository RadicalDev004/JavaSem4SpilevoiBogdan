package org.example.analyzer;

import java.io.File;
import java.nio.file.Path;

public class ClassNameUtil {

    public static String resolve(File classFile, File rootDir) {
        Path full = classFile.getAbsoluteFile().toPath();
        Path root = rootDir.getAbsoluteFile().toPath();

        Path relative = root.relativize(full);

        String name = relative.toString()
                .replace(File.separator, ".")
                .replaceAll("\\.class$", "");
        System.out.println(name);
        return name;
    }
}