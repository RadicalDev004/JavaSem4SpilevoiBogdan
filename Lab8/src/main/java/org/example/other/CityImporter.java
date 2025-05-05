package org.example.other;

import com.opencsv.CSVReader;
import org.example.DAOs.CityDAO;
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
            CountryDAO countryDAO = new CountryDAO();

            String sql = "INSERT INTO cities (name, country, capital, latitude, longitude) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            while ((nextLine = reader.readNext()) != null) {
                String cityName = nextLine[1];
                String country = nextLine[0];
                boolean capital = true;
                double lat = Double.parseDouble(nextLine[2]);
                double lon = Double.parseDouble(nextLine[3]);

                System.out.println(cityName + " " + country + " " + lat + " " + lon);

                pstmt.setString(1, cityName);
                pstmt.setString(2, country);
                pstmt.setBoolean(3, capital);
                pstmt.setDouble(4, lat);
                pstmt.setDouble(5, lon);

                pstmt.addBatch();
            }
            pstmt.executeBatch();

            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
