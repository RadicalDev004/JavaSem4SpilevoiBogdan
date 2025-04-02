package org.example.repository;

import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ImageRepository {
    private List<ImageItem> images = new ArrayList<>();
    private String saveName;

    public ImageRepository(String saveName) {
        this.saveName = saveName;
    }

    public String getSaveName() {
        return saveName;
    }

    public void addImage(ImageItem image) {
        images.add(image);
    }
    public void removeImage(ImageItem image) {
        images.remove(image);
    }
    public  void removeImage(String imageName) {
        images.removeIf(image -> image.name().equals(imageName));
    }
    public void changeImage(String imageName, String newName) {
        images = images.stream()
                .map(image -> image.name().equals(imageName)
                        ? new ImageItem(newName, image.tags(), image.date(), image.location())
                        : image)
                .collect(Collectors.toList());

    }

    public boolean contains(ImageItem image) {
        return images.contains(image);
    }
    public boolean contains(String imageName) {
        return images.stream().anyMatch(img -> img.name().equals(imageName));
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

    public void displayAllImages() {
        for (ImageItem image : images) {
            displayImage(image);
        }
    }

    @Override
    public String toString() {
        return "ImageRepository{" +
                "images=" + images +
                '}';
    }
}