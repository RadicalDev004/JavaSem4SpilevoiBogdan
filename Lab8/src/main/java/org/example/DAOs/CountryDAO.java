package org.example.DAOs;

import org.example.database.Database;

import java.sql.*;

public class CountryDAO {
    public void create (String name, int code, String continent) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement( "insert into countries (name, code, continent) values (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, code);
            pstmt.setString(3, continent);
            pstmt.executeUpdate();
            con.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer findByName (String name) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery( "select id from countries where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public String findById (int id) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "SELECT name FROM countries WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Error in findById: " + e.getMessage());
        }
        return null;
    }
}
