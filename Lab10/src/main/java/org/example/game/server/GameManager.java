package org.example.game.server;

import java.io.PrintWriter;
import java.util.*;

public class GameManager {
    private static final Map<String, HexGame> games = new HashMap<>();
    private static final Map<String, List<PrintWriter>> gameObservers = new HashMap<>();

    private static final Map<String, HexGame> aiGames = new HashMap<>();

    public static synchronized String createGame(String gameId, String playerName, int timeSeconds, PrintWriter out) {
        if (games.containsKey(gameId)) return "Game already exists.";
        gameObservers.computeIfAbsent(gameId, k -> new ArrayList<>()).add(out);
        Player p = new Player(playerName, 'X', timeSeconds);
        games.put(gameId, new HexGame(gameId, p, false));
        return "Game created: " + gameId;
    }

    public static synchronized String getEmptyRoomForJoin()
    {
        System.out.println(games.values());
        for(var game: games.values())
        {
            System.out.println(game.getGameId() + " " + game.isFull());
            if(!game.isFull()) return game.getGameId();
        }
        return "-";
    }

    public static synchronized String createAIGame(String gameId, String playerName, int timeSeconds, PrintWriter out) {
        if (aiGames.containsKey(gameId)) return "Game already exists.";
        gameObservers.computeIfAbsent(gameId, k -> new ArrayList<>()).add(out);
        Player p = new Player(playerName, 'X', timeSeconds);
        aiGames.put(gameId, new HexGame(gameId, p, true));
        return "Game created: " + gameId;
    }

    public static synchronized String joinGame(String gameId, String playerName, int timeSeconds, PrintWriter out) {
        HexGame game = games.get(gameId);
        gameObservers.computeIfAbsent(gameId, k -> new ArrayList<>()).add(out);
        if (game == null) return "Game not found.";
        Player p = new Player(playerName, 'O', timeSeconds);
        propagateInfoToAllObservers(gameId, "START");
        Player other = game.getPlayers()[0];
        System.out.println(other.getName() + "|" + playerName);
        propagateInfoToAllObservers(gameId, other.getName() + "|" + playerName);
        return game.join(p) ? "Joined game: " + gameId : "Game full.";
    }

    public static synchronized String submitMove(String gameId, String playerName, int x, int y) {
        HexGame game = games.get(gameId);
        if (game == null) {
            if(aiGames.containsKey(gameId))
            {
                HexGame aiGame = aiGames.get(gameId);
                System.out.println("is in ai game: " + gameId);
                return aiGame.setAiMove(playerName, x, y);
            }
            return "Game not found.";
        }
        return game.makeMove(playerName, x, y);
    }

    public static synchronized void propagateBoardToAllObservers(String gameId) {
        HexGame game = games.get(gameId);
        System.out.println("propagating");
        if(game != null)
        {
            for (PrintWriter observer : gameObservers.get(gameId)) {
                observer.println("BOARD");
                for (String line : game.printBoard().split("\n")) {
                    observer.println(line);
                }
            }
        }
        else {
            game = aiGames.get(gameId);
            if(game == null) return;
            System.out.println("Reaching to ai player");
            for (PrintWriter observer : gameObservers.get(gameId)) {
                observer.println("BOARD");
                for (String line : game.printBoard().split("\n")) {
                    observer.println(line);
                }
            }
        }

    }

    public  static synchronized void propagateInfoToAllObservers(String gameId, String info) {
        HexGame game = games.get(gameId);
        if(game != null) {
            for (PrintWriter observer : gameObservers.get(gameId)) {
                observer.println(info);
            }
        }
        else{
            game = aiGames.get(gameId);
            if(game == null) return;
            for (PrintWriter observer : gameObservers.get(gameId)) {
                observer.println(info);
            }
        }
    }

    public  static synchronized void propagateInfoToAllObserversExcept(String gameId, String info, PrintWriter exclude) {
        HexGame game = games.get(gameId);
        if(game != null) {
            for (PrintWriter observer : gameObservers.get(gameId)) {
                if(exclude == observer) continue;
                observer.println(info);
            }
        }
        else{
            game = aiGames.get(gameId);
            if(game == null) return;
            for (PrintWriter observer : gameObservers.get(gameId)) {
                if(exclude == observer) continue;
                observer.println(info);
            }
        }
    }

    public static synchronized String listGames() {
        if (games.isEmpty()) return "No games available.";
        StringBuilder sb = new StringBuilder();
        for (HexGame game : games.values()) {
            sb.append(game.getGameId()).append(" - ").append(game.getStatus()).append("\n");
        }
        return sb.toString();
    }
    public static synchronized String getBoard(String gameId) {
        HexGame game = games.get(gameId);
        if (game == null) {
            return "Invalid game ID.";
        }
        return game.printBoard();
    }

}

