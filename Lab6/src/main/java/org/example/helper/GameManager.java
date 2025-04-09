package org.example.helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.panels.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class GameManager {
    public static Path savePath;
    public static String gameName;
    public static int turn = 0;
    public static double score1 = 0, score2 = 0;
    public static List<ConnectingLine> allLines = new ArrayList<>();
    public static List<CircleButton> allCircleButtons = new ArrayList<>();
    public static ConfigPanel ConfigPanel;
    public static DotsGame dotsGame;
    public static DrawingPanel drawingPanel;
    public static StaticPanel staticPanel;
    public static ConfigPanel configPanel;
    public static ControlPanel controlPanel;
    public static boolean ai;
    public static int aiLevel;
    public static boolean gameOver;

    public static boolean hasLine(CircleButton b1, CircleButton b2) {
        for(ConnectingLine line : allLines) {
            if(line.getStartX() == b1.getX() && line.getStartY() == b1.getY() && line.getEndX() == b2.getX() && line.getEndY() == b2.getY()) {
                return true;
            }
            if(line.getStartX() == b2.getX() && line.getStartY() == b2.getY() && line.getEndX() == b1.getX() && line.getEndY() == b1.getY()) {
                return true;
            }
            //System.out.println(line.getStartX() + " " + line.getStartY() + " " + line.getEndX() + " " + line.getEndY() + b1.getX() + " " + b1.getY() + " " + b2.getX() + " " + b2.getY());
        }
        return false;
    }

    public static boolean hasLine(ConnectingLine line) {
        for(ConnectingLine line1 : allLines) {
            if(line1.getStartX() == line.getStartX() && line1.getStartY() == line.getStartY() && line1.getEndX() == line.getEndX() && line1.getEndY() == line.getEndY()) return true;
            if(line1.getStartX() == line.getEndX() && line1.getStartY() == line.getEndY() && line1.getEndX() == line.getStartX() && line1.getEndY() == line.getStartY()) return true;
        }
        return false;
    }

    public  static void NextTurn() {
        turn++;
    }

    public static void addLine(ConnectingLine line, boolean isFromMemory) {
        double lineScore = getScore(line);

        if(turn % 2 == 0) score1 += lineScore;
        else score2 += lineScore;

        ConfigPanel.ChangeScore(turn % 2 == 0, turn % 2 == 0 ? score1 : score2, lineScore, bestScore());

        allLines.add(line);
        staticPanel.imprintLine(line);
        NextTurn();

        if(hasAllConnected())
        {
            gameOver = true;
            configPanel.endGame();
            return;
        }

        if(turn % 2 == 1 && ai && !isFromMemory)
        {
            makeAiMove();
        }
    }

    public static double getScore(ConnectingLine line)
    {
        return Math.sqrt(
                (line.getEndX() - line.getStartX()) * (line.getEndX() - line.getStartX()) +
                        (line.getEndY() - line.getStartY()) * (line.getEndY() - line.getStartY())
        );
    }
    public static double getScore(CircleButton b1, CircleButton b2)
    {
        return Math.sqrt(
                (b2.getX() - b1.getX()) * (b2.getX() - b1.getX()) +
                (b2.getY() - b1.getY()) * (b2.getY() - b1.getY())
        );
    }


    public static double bestScore()
    {
        double min = 99999;
        for(var but1 : allCircleButtons) {
            for(var but2 : allCircleButtons) {
                if(but1.equals(but2) || hasLine(but1, but2)) continue;
                if(getScore(but1, but2) < min) min = getScore(but1, but2);
            }
        }
        return min;
    }

    public static void saveGame() throws JsonProcessingException, IOException {
        GameSerializer gs = new GameSerializer(turn, score1, score2, allLines, allCircleButtons, ai);
        gs.saveGame();
    }

    public static void loadGame(String path) throws IOException {
        GameSerializer gs = new GameSerializer();
        gs.loadGame(path);

        startGame();
        drawingPanel.generateDots(gs.getAllCircleButtons());
        for(ConnectingLine line : gs.getAllLines()) {
            addLine(line, true);
        }
        turn = gs.getTurn();
        //System.out.println("IS LOADED AI " + ai);

        configPanel.startButton.setVisible(false);
        configPanel.dotsField.setVisible(false);
        configPanel.label.setVisible(false);
    }


    public  static void startGame() {
        DotsGame game = new DotsGame();
        dotsGame = game;
        drawingPanel = game.getDrawingPanel();
        configPanel = game.getConfigPanel();
        SwingUtilities.invokeLater(() -> game.setVisible(true));
    }

    public static void exportGamePng()
    {
        staticPanel.exportPng();
    }

    public static void makeAiMove()
    {
        MSTCompute m = new MSTCompute();
        m.computeNextEdge();
    }

    public static boolean hasAllConnected()
    {
        List<CircleButton> connectedCircleButtons = new ArrayList<>();
        connectedCircleButtons.add(allCircleButtons.get(0));
        for(int i = 1; i < allCircleButtons.size(); i++)
        {
            for(var but : connectedCircleButtons)
            {
                if(hasLine(allCircleButtons.get(i), but) && !connectedCircleButtons.contains(allCircleButtons.get(i)))
                {
                    connectedCircleButtons.add(allCircleButtons.get(i));
                    i = 0;
                    break;
                }
            }
        }
        //System.out.println(connectedCircleButtons.size());
        return connectedCircleButtons.size() == GameManager.allCircleButtons.size();
    }
}
