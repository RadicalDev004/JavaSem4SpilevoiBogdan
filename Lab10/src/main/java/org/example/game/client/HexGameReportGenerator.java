package org.example.game.client;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.example.game.database.Database;

import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class HexGameReportGenerator {

    public static void generateReport(int gameId, String outputPath) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        try {
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

            Template template = cfg.getTemplate("hex_report.ftl");

            Map<String, Object> data = fetchGameData(gameId);
            if (data == null) {
                System.err.println("Game ID not found.");
                return;
            }

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

    private static Map<String, Object> fetchGameData(int gameId) {
        String sql = """
            SELECT g.board, u1.username AS user1, u2.username AS user2, g.result
            FROM games g
            LEFT JOIN users u1 ON g.user1_id = u1.id
            LEFT JOIN users u2 ON g.user2_id = u2.id
            WHERE g.id = ?
        """;

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            String boardRaw = rs.getString("board");
            String[] rows = boardRaw.split("\n");
            String user1 = rs.getString("user1");
            String user2 = rs.getString("user2");
            int result = rs.getInt("result");
            String winner = result == 1 ? user1 : user2;

            List<Map<String, Object>> hexes = computeHexes(rows);

            Map<String, Object> data = new HashMap<>();
            data.put("gameId", gameId);
            data.put("user1", user1);
            data.put("user2", user2);
            data.put("winner", winner);
            data.put("hexes", hexes);

            return data;

        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
            return null;
        }
    }

    private static List<Map<String, Object>> computeHexes(String[] rows) {
        List<Map<String, Object>> hexes = new ArrayList<>();

        double r = 25;
        double w = Math.sqrt(3) * r;
        double h = 2 * r;

        double dx = w;
        double dy = 0.75 * h;

        double[] cos = {
                Math.cos(Math.toRadians(30)),
                Math.cos(Math.toRadians(90)),
                Math.cos(Math.toRadians(150)),
                Math.cos(Math.toRadians(210)),
                Math.cos(Math.toRadians(270)),
                Math.cos(Math.toRadians(330))
        };

        double[] sin = {
                Math.sin(Math.toRadians(30)),
                Math.sin(Math.toRadians(90)),
                Math.sin(Math.toRadians(150)),
                Math.sin(Math.toRadians(210)),
                Math.sin(Math.toRadians(270)),
                Math.sin(Math.toRadians(330))
        };

        for (int row = 0; row < rows.length; row++) {
            char[] cols = rows[row].toCharArray();
            for (int col = 0; col < cols.length; col++) {
                char cell = cols[col];
                String color = switch (cell) {
                    case 'X' -> "red";
                    case 'O' -> "blue";
                    default -> "white";
                };

                double cx = col * dx + (row * dx / 2) + 50;
                double cy = row * dy + 50;

                StringBuilder points = new StringBuilder();
                for (int i = 0; i < 6; i++) {
                    double px = cx + r * cos[i];
                    double py = cy + r * sin[i];
                    points.append((int) px).append(",").append((int) py).append(" ");
                }

                Map<String, Object> hex = new HashMap<>();
                hex.put("points", points.toString().trim());
                hex.put("fill", color);
                hexes.add(hex);
            }
        }

        return hexes;
    }




    private static void openInBrowser(File file) {
        try {
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            System.err.println("Could not open the report: " + e.getMessage());
        }
    }
}

