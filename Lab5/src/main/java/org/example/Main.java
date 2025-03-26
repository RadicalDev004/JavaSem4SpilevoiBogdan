package org.example;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();
        
        ImageItem img1 = new ImageItem("Sunset", LocalDate.of(2025, 3, 26), Path.of("C:/Users/Bogdan S/OneDrive/Desktop/Screenshot_90.png"));
        repository.addImage(img1);

        repository.displayImage(img1);
    }
}