package org.example.swing;

import org.example.helper.Pair;

import java.awt.*;

public class Line {
    private Pair<Point, Point> info;

    public Line(Pair<Point, Point> info) {
        this.info = info;
    }

    public Pair<Point, Point> getInfo() {
        return info;
    }

    public void setInfo(Pair<Point, Point> info) {
        this.info = info;
    }
}
