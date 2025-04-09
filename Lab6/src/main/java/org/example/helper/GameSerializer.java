package org.example.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameSerializer {

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private int turn;
    private double score1, score2;

    public List<ConnectingLine> getAllLines() {
        return allLines;
    }

    private List<ConnectingLine> allLines = new ArrayList<>();

    public List<CircleButtonSerialized> getAllCircleButtons() {
        return allCircleButtons;
    }

    private  List<CircleButtonSerialized> allCircleButtons = new ArrayList<>();
    private boolean ai;

    public  GameSerializer()
    {

    }

    public GameSerializer(int turn, double score1, double score2, List<ConnectingLine> allLines, List<CircleButton> allCircleButtons, boolean ai) {
        this.turn  = turn;
        this.score1 = score1;
        this.score2 = score2;
        this.allLines = allLines;
        for(CircleButton cb : allCircleButtons) {
            this.allCircleButtons.add(new CircleButtonSerialized(cb));
        }
        this.ai = ai;
    }
    public void copy(GameSerializer gameSerializer) {
        this.turn  = gameSerializer.turn;
        this.score1 = gameSerializer.score1;
        this.score2 = gameSerializer.score2;
        this.allLines = gameSerializer.allLines;

        this.allCircleButtons.addAll(gameSerializer.allCircleButtons);
    }

    public void saveGame() throws JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(this);

        File file = new File(GameManager.savePath + "/" + GameManager.gameName + ".json");
        if(!file.exists()) {
            boolean created = file.createNewFile();
        }
        Files.write(file.toPath(), json.getBytes());
    }

    public void loadGame(String path) throws JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path);
        GameSerializer gs = objectMapper.readValue(file, GameSerializer.class);

        GameManager.gameName = file.getName().substring(0, file.getName().lastIndexOf("."));
        GameManager.savePath = Path.of(path.substring(0, path.length() - file.getName().length()));
        copy(gs);
        GameManager.ai = gs.ai;
        System.out.println(gs.ai);
    }



}
