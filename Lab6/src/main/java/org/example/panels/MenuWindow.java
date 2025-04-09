package org.example.panels;

import org.example.helper.GameManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MenuWindow extends JFrame {
    MenuNewGame newGame;
    MenuLoadGame loadGame;
    private int aiLevel;
    public MenuWindow() {
        setTitle("Dots Connection Game");
        setSize(850, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        newGame = new MenuNewGame();
        loadGame = new MenuLoadGame();
        add(newGame, BorderLayout.WEST);
        add(loadGame, BorderLayout.EAST);


        newGame.newGameButton.addActionListener(e -> {
            if(!Files.isDirectory(Path.of(newGame.savePath.getText()))) return;

            try {
                aiLevel = Math.min(Integer.parseInt(newGame.aiLevel.getText()), 10);
            } catch (NumberFormatException ex) {
                aiLevel = 0;
            }

            GameManager.ai = newGame.type.isSelected();
            GameManager.savePath = Path.of(newGame.savePath.getText());
            GameManager.gameName = newGame.gameName.getText();
            GameManager.aiLevel = aiLevel;
            GameManager.startGame();

            setVisible(false);
        });

        loadGame.loadGameButton.addActionListener(e -> {
            File gameFile = new File(loadGame.gamePath.getText());
            if(!gameFile.isFile())
            {
                System.out.println(gameFile.getAbsolutePath());
                return;
            }

            try
            {
                GameManager.loadGame(loadGame.gamePath.getText());
                setVisible(false);
            }
            catch (IOException e1)
            {
                System.out.println(e1.getMessage() + " " + e1.getCause() + " " + loadGame.gamePath.getText());
            }
        });

    }
}
