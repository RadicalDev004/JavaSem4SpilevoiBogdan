package org.example.commands;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.repository.ImageRepository;

import java.awt.*;
import java.io.*;
import java.util.*;

public class ReportGenerator {
    public static void generateHtmlReport(ImageRepository repo, String outputPath) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

        try {
            Template template = cfg.getTemplate("report.ftl");

            Map<String, Object> data = new HashMap<>();
            data.put("images", repo.getImages());

            File outputFile = new File(outputPath);
            try (Writer writer = new FileWriter(outputFile)) {
                template.process(data, writer);
            }

            System.out.println("Report generated at: " + outputFile.getAbsolutePath());

            openInBrowser(outputFile);

        } catch (IOException | TemplateException e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    private static void openInBrowser(File file) {
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            System.err.println("Could not open the report: " + e.getMessage());
        }
    }
}
