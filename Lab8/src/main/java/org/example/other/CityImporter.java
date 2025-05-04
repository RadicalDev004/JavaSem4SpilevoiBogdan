package org.example.other;

import com.opencsv.CSVReader;
import org.example.DAOs.CountryDAO;
import org.example.database.Database;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CityImporter {
    public void importCities(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath));
             Connection con = Database.getConnection()) {

            String[] nextLine;
            reader.readNext();

            String sql = "INSERT INTO cities (name, capital, latitude, longitude, country_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            while ((nextLine = reader.readNext()) != null) {
                String cityName = nextLine[0];
                String country = nextLine[4];
                String capital = nextLine[6];
                double lat = Double.parseDouble(nextLine[2]);
                double lon = Double.parseDouble(nextLine[3]);

                CountryDAO countryDAO = new CountryDAO();
                int countryId = countryDAO.findByName(country);

                pstmt.setString(1, cityName);
                pstmt.setBoolean(2, "primary".equalsIgnoreCase(capital));
                pstmt.setDouble(3, lat);
                pstmt.setDouble(4, lon);
                pstmt.setInt(5, countryId);

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
