package org.example.DAOs;

import org.example.helper.Pair;
import org.example.database.Database;

import java.sql.*;

public class CityDAO {

    public Integer cityLength() throws SQLException {
        String sql = "SELECT count(*) FROM cities";

        try (Connection con = Database.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // or rs.getInt("count") if you use alias
            }
        } catch (SQLException e) {
            System.out.println("Error in cityLength: " + e.getMessage());
        }
        return null;
    }


    public void create (String name, int code, String continent) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement( "insert into cities (name, code, continent) values (?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setInt(2, code);
            pstmt.setString(3, continent);
            pstmt.executeUpdate();
            //con.commit();

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Integer findByName (String name) throws SQLException {

        try (Connection con = Database.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery( "select id from cities where name='" + name + "'")) {
            return rs.next() ? rs.getInt(1) : null;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public Pair<Double, Double> getLocationByName (String name) throws SQLException {

        try (Connection con = Database.getConnection();  Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery( "select latitude, longitude from cities where name='" + name + "'")) {
            if (rs.next()) {
                double lat = rs.getDouble("latitude");
                double lon = rs.getDouble("longitude");
                return new Pair<>(lon, lat);
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Pair<Double, Double> getLocationById (int id) throws SQLException {

        try (Connection con = Database.getConnection();  Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery( "select latitude, longitude from cities where id='" + id + "'")) {
            if (rs.next()) {
                double lat = rs.getDouble("latitude");
                double lon = rs.getDouble("longitude");
                return new Pair<>(lat, lon);
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static double distance(Pair<Double, Double> loc1, Pair<Double, Double> loc2) {
        if (loc1 == null || loc2 == null) return -1;

        double lat1 = Math.toRadians(loc1.getFirst());
        double lon1 = Math.toRadians(loc1.getSecond());
        double lat2 = Math.toRadians(loc2.getFirst());
        double lon2 = Math.toRadians(loc2.getSecond());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371.0 * c;
    }

    public String findById (int id) throws SQLException {
        Connection con = Database.getConnection();
        String sql = "SELECT name FROM cities WHERE id = ?";
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
