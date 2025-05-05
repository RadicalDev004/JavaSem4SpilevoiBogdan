package org.example.DAOs;

import org.example.database.Database;

import java.sql.*;

public class SisterDAO {
    public void create (int code1, int code2) throws SQLException {

        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement( "insert into sister_cities (city1_id, city2_id) values (?, ?)")) {
            pstmt.setInt(1, code1);
            pstmt.setInt(2, code2);
            pstmt.executeUpdate();
            //con.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
