package org.example.game.client.dao;

import org.example.game.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public void create(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in create: " + e.getMessage());
        }
    }

    public String getPasswordByUsername(String username) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            System.out.println("Error in getPasswordByUsername: " + e.getMessage());
        }
        return null;
    }

    public static Integer getUserIdByUsername(String username) throws SQLException {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return null;
    }

    public static Integer getUsernameById(int id) throws SQLException {
        String sql = "SELECT username FROM users WHERE id = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return null;
    }

    public List<String> getAllUsernames() throws SQLException {
        String sql = "SELECT username FROM users";
        List<String> usernames = new ArrayList<>();
        try (Connection con = Database.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error in getAllUsernames: " + e.getMessage());
        }
        return usernames;
    }

    public void updatePassword(String username, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updatePassword: " + e.getMessage());
        }
    }

    public void delete(String username) throws SQLException {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in delete: " + e.getMessage());
        }
    }

    public Integer userCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error in userCount: " + e.getMessage());
        }
        return null;
    }
}
