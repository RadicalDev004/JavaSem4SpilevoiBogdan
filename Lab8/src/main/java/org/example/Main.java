package org.example;

public class Main {
    public static void main(String[] args) {
        ContinentDAO continentDAO = new ContinentDAO();
        CountryDAO countryDAO = new CountryDAO();
        countryDAO.create("USA", 7, "America");
    }
}