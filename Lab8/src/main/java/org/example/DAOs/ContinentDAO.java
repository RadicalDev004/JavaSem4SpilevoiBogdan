package org.example.DAOs;
import org.example.database.Database;

import java.sql.*;

public class ContinentDAO {
    public void create (String name) throws SQLException {
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement( "insert into continents (name) values (?)")) {
            pstmt.setString(1, name); pstmt.executeUpdate();
            //con.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer findByName (String name) throws SQLException {
        try (Connection con = Database.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery( "select id from continents where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public String findById (int id) throws SQLException {
        String sql = "SELECT name FROM continents WHERE id = ?";
        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
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
