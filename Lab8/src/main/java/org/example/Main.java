package org.example;

import org.example.DAOs.CityDAO;
import org.example.DAOs.ContinentDAO;
import org.example.DAOs.CountryDAO;
import org.example.DAOs.SisterDAO;
import org.example.database.Database;
import org.example.helper.EllipticalMercator;
import org.example.other.CityImporter;
import org.example.swing.CitiesGUI;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import org.graph4j.connectivity.TarjanBiconnectivity;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ContinentDAO continentDAO = new ContinentDAO();
        CountryDAO countryDAO = new CountryDAO();
        CityDAO cityDAO = new CityDAO();
        SisterDAO sisterDAO = new SisterDAO();

        try {
            Database.resetTable("C:/Users/Bogdan S/OneDrive/Documents/GitHub/JavaSem4/Lab8/src/main/java/org/example/other/lab8sql.sql");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            continentDAO.create("Asia");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        CityImporter cityImporter = new CityImporter();
        cityImporter.importCities("C:/Users/Bogdan S/OneDrive/Documents/GitHub/JavaSem4/Lab8/src/main/java/org/example/other/concap.csv");

        try {
            var l1 = cityDAO.getLocationByName("Kabul");
            var l2 = cityDAO.getLocationByName("Tirana");
            System.out.println(CityDAO.distance(l1, l2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Random rand = new Random();
        Integer cnt = 0;
        try {
            cnt = cityDAO.cityLength();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(cnt);

        Graph graph = GraphBuilder.empty().buildGraph();
        CitiesGUI citiesGUI = new CitiesGUI();
        citiesGUI.setVisible(true);

        for(int i = 0; i < cnt / 50; i++)
        {
            int i1 = rand.nextInt(1, cnt);
            int i2 = rand.nextInt(1, cnt);
            while(i1 == i2) i2 = rand.nextInt(cnt);

            try {
                sisterDAO.create(i1, i2);
                graph.addLabeledVertex((Object)i1);
                graph.addLabeledVertex((Object)i2);
                graph.addEdge((Object) i1, (Object)i2);


                var p1=EllipticalMercator.worldToPos(cityDAO.getLocationById(i1));
                var p2 =EllipticalMercator.worldToPos(cityDAO.getLocationById(i2));
                citiesGUI.addDot(p1);
                citiesGUI.addDot(p2);
                citiesGUI.addLine(p1, p2);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(graph);
        TarjanBiconnectivity tb = new TarjanBiconnectivity(graph);
        var bl = tb.getBlocks();

        for(var b : bl)
        {
            System.out.println(b);
        }
    }
}