package org.example;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class ImageRepository {
    private final List<ImageItem> images = new ArrayList<>();

    public void addImage(ImageItem image) {
        images.add(image);
    }

    public void displayImage(ImageItem image) {
        File file = image.location().toFile();
        if (!file.exists()) {
            System.out.println("Image file not found: " + file.getAbsolutePath());
            return;
        }

        try {
            System.out.println("Displaying image " + image.name());
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.println("Unable to open image: " + e.getMessage());
        }
    }

    public List<ImageItem> getImages() {
        return images;
    }
}