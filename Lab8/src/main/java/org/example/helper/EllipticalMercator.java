package org.example.helper;

public class EllipticalMercator {
    private static final double R_MAJOR = 6378137.0;
    private static final double R_MINOR = 6356752.3142;
    private static final double ECCENTRICITY = Math.sqrt(1 - (R_MINOR * R_MINOR) / (R_MAJOR * R_MAJOR));


    public static double lonToX(double lon) {
        return R_MAJOR * Math.toRadians(lon);
    }

    public static Pair<Integer, Integer> worldToPos(Pair<Double, Double> world) {
        System.out.println("Converting world: " + world.getFirst() + " " + world.getSecond());
        double R = 6378137;
        double x = R * Math.toRadians(world.getFirst());
        double y = R * Math.log(Math.tan(Math.PI / 4 + Math.toRadians(world.getSecond()) / 2));

        double xMin = -20037508.34;
        double xMax =  20037508.34;
        double yMin = -20037508.34;
        double yMax =  20037508.34;

        int screenX = (int) ((x - xMin) / (xMax - xMin) * 800);
        int screenY = (int) ((yMax - y) / (yMax - yMin) * 650);
        return new Pair<>(screenX, screenY);
    }

    public static double latToY(double lat) {
        if (lat > 89.5) lat = 89.5;
        if (lat < -89.5) lat = -89.5;
        double phi = Math.toRadians(lat);
        double sinPhi = Math.sin(phi);
        double con = ECCENTRICITY * sinPhi;
        double ts = Math.tan(Math.PI / 4 - phi / 2) /
                Math.pow((1 - con) / (1 + con), ECCENTRICITY / 2);
        return -R_MAJOR * Math.log(ts);
    }

    public static double xToLon(double x) {
        return Math.toDegrees(x / R_MAJOR);
    }

    public static double yToLat(double y) {
        double ts = Math.exp(-y / R_MAJOR);
        double phi = Math.PI / 2 - 2 * Math.atan(ts);
        double delta = 1.0;
        double lastPhi;
        int i = 0;
        do {
            lastPhi = phi;
            double sinPhi = Math.sin(phi);
            double con = ECCENTRICITY * sinPhi;
            phi = Math.PI / 2 - 2 * Math.atan(ts *
                    Math.pow((1 - con) / (1 + con), ECCENTRICITY / 2));
            delta = Math.abs(phi - lastPhi);
        } while (delta > 1e-10 && ++i < 15);

        return Math.toDegrees(phi);
    }
}

