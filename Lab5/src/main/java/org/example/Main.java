package org.example;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageRepository repository = new ImageRepository();
        
        ImageItem img1 = new ImageItem("Screenshot90", LocalDate.of(2025, 3, 26), Arrays.asList("show", "old"), Path.of("C:/Users/User/Desktop/WoodenStake_DIFF.png"));
        repository.addImage(img1);

        repository.displayImage(img1);
    }
}