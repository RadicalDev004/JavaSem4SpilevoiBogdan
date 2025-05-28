package org.example.game.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final HikariDataSource dataSource;

    public static void initSchema() {
        String sqlUsers = """
        CREATE TABLE IF NOT EXISTS users (
            id SERIAL PRIMARY KEY,
            username VARCHAR(50) UNIQUE NOT NULL,
            password VARCHAR(255) NOT NULL
        );
    """;

        String sqlGames = """
        CREATE TABLE IF NOT EXISTS games (
            id SERIAL PRIMARY KEY,
            user1_id INTEGER NOT NULL,
            user2_id INTEGER NOT NULL,
            result INTEGER NOT NULL CHECK (result IN (0, 1)),
            FOREIGN KEY (user1_id) REFERENCES users(id) ON DELETE CASCADE,
            FOREIGN KEY (user2_id) REFERENCES users(id) ON DELETE CASCADE
        );
    """;

        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            stmt.execute(sqlUsers);
            stmt.execute(sqlGames);
            System.out.println("Schema initialized.");
        } catch (SQLException e) {
            System.out.println("Error initializing schema: " + e.getMessage());
        }
    }


    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5555/postgres");
        config.setUsername("postgres");
        config.setPassword("postgres");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(10000);

        dataSource = new HikariDataSource(config);
        System.out.println("Database created");
    }

    private Database() {
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Trying to get connection");
        return dataSource.getConnection();
    }

    public static void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    public static void resetTable(String sqlScript) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            String sql = Files.readString(Path.of(sqlScript));

            Statement stmt = con.createStatement();
            for (String s : sql.split(";")) {
                if (!s.trim().isEmpty()) {
                    stmt.execute(s.trim());
                }
            }
            //con.commit();
        } catch (IOException e) {
            System.out.println("Failed to read SQL script: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL execution error: " + e.getMessage());
            //if (con != null) con.rollback();
        }
    }
}
