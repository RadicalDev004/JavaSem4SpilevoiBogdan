package org.example;

import org.example.DAOs.ContinentDAO;
import org.example.DAOs.CountryDAO;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ContinentDAO continentDAO = new ContinentDAO();
        CountryDAO countryDAO = new CountryDAO();

        try {
            countryDAO.create("USA", 7, "America");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}