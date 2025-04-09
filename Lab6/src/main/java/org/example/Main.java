package org.example;

import org.example.helper.GameManager;
import org.example.panels.*;

import javax.swing.*;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        GameManager.savePath = Path.of("C:/Users/Bogdan S/OneDrive/Desktop/javaGame");

        MenuWindow game = new MenuWindow();
        SwingUtilities.invokeLater(() -> game.setVisible(true));
    }
}