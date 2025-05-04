package org.example;
import java.sql.*;

public class ContinentDAO {
    public void create (String name)  {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement( "insert into continents (name) values (?)")) {
            pstmt.setString(1, name); pstmt.executeUpdate();
            pstmt.executeUpdate();
            con.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer findByName (String name){
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery( "select id from continents where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public String findById (int id) {
        Connection con = Database.getConnection();
        String sql = "SELECT name FROM continents WHERE id = ?";
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
