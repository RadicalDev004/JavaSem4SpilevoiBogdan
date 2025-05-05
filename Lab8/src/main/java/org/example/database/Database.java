package org.example.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class Database {

    private static final HikariDataSource dataSource;

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
