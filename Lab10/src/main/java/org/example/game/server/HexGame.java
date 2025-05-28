package org.example.game.server;

import org.example.game.client.dao.UserDao;
import org.example.game.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

public class HexGame {
    private final String gameId;
    private final int size = 11;
    private final char[][] board = new char[size][size];
    private boolean ai = false;
    private final Player[] players = new Player[2];
    private int currentPlayerIndex = 0;
    private boolean full = false;
    private boolean finished = false;
    private Instant lastMoveTime;

    public boolean isFull() {
        return full;
    }

    public Player[] getPlayers() {
        return players;
    }

    public HexGame(String gameId, Player creator, boolean ai) {
        this.gameId = gameId;
        this.players[0] = creator;
        this.players[1] = null;
        this.ai = ai;
        for (char[] row : board) Arrays.fill(row, '.');
        this.lastMoveTime = Instant.now();
    }

    public boolean join(Player player) {
        if (players[1] == null) {
            players[1] = player;
            full = true;
            return true;
        }
        return false;
    }

    public String getStatus() {
        return finished ? "Finished" : full ? "In progress" : "Waiting for player";
    }

    public synchronized String makeMove(String playerName, int x, int y) {
        System.out.println("AI" + ai);
        if (!ai && (finished || !full)) return "Game not ready or already finished.";
        if (x < 0 || x >= size || y < 0 || y >= size) return "Invalid move.";

        Player current = players[currentPlayerIndex];
        if (!ai && !current.getName().equals(playerName)) return "Not your turn.";

        if (board[x][y] != '.') return "Cell already occupied.";

        long now = Instant.now().toEpochMilli();
        long elapsed = now - lastMoveTime.toEpochMilli();
        current.deductTime(elapsed);
        if (current.getTimeLeftMillis() <= 0) {
            finished = true;
            return current.getName() + " ran out of time. Game over.";
        }

        board[x][y] = current.getSymbol();
        lastMoveTime = Instant.now();

        String onWin = "";
        if (hasPlayerWon(current.getSymbol())) {
            onWin = "WON";
            finished = true;
            if(!ai)
            {
                saveGameToDatabase(players[0].getName(), players[1].getName(), Objects.equals(playerName, players[0].getName()) ? 1 : 0);
            }
        }

        currentPlayerIndex = 1 - currentPlayerIndex;
        return "Move accepted." + onWin;
    }

    public String printBoard() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            sb.append(new String(row)).append("\n");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String setAiMove(String playerName, int x, int y)
    {
        var rez = findAiMove();
        String re = makeMove(playerName, x, y);
        if(rez != null)
        {
            System.out.println(Arrays.toString(rez));
            if(re.contains("Move accepted."))
                board[rez[0]][rez[1]] = 'O';
        }
        return re;
    }

    private int[] findAiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestI = -1, bestJ = -1;
        int center = size / 2;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == '.') {
                    int score = - (Math.abs(i - center) + Math.abs(j - center));
                    if (score > bestScore) {
                        bestScore = score;
                        bestI = i;
                        bestJ = j;
                    }
                }
            }
        }

        return (bestI != -1) ? new int[]{bestI, bestJ} : null;
    }


    public String getGameId() { return gameId; }

    public boolean hasPlayerWon(char player) {
        boolean[][] visited = new boolean[board.length][board[0].length];

        if (player == 'X') {
            for (int col = 0; col < board.length; col++) {
                if (board[0][col] == 'X') {
                    if (dfs(0, col, player, visited)) return true;
                }
            }
        } else if (player == 'O') {
            for (int row = 0; row < board.length; row++) {
                if (board[row][0] == 'O') {
                    if (dfs(row, 0, player, visited)) return true;
                }
            }
        }

        return false;
    }

    private boolean dfs(int row, int col, char player, boolean[][] visited) {
        int n = board.length;
        if (row < 0 || col < 0 || row >= n || col >= n) return false;
        if (board[row][col] != player || visited[row][col]) return false;

        visited[row][col] = true;

        if (player == 'X' && row == n - 1) return true;
        if (player == 'O' && col == n - 1) return true;

        int[] dr = {-1, -1, 0, 0, 1, 1};
        int[] dc = {0, 1, -1, 1, -1, 0};

        for (int d = 0; d < 6; d++) {
            if (dfs(row + dr[d], col + dc[d], player, visited)) return true;
        }

        return false;
    }

    public void saveGameToDatabase(String user1, String user2, int result) {
        String sql = "INSERT INTO games (user1_id, user2_id, result) VALUES (?, ?, ?)";


        Integer user1Id = null, user2Id = null;
        try {
            user1Id = UserDao.getUserIdByUsername(user1);
            user2Id = UserDao.getUserIdByUsername(user2);

            if (user1Id == null) user1Id = -1;
            if (user2Id == null) user2Id = -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (Connection con = Database.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, user1Id);
            stmt.setInt(2, user2Id);
            stmt.setInt(3, result);
            stmt.executeUpdate();

            System.out.println("Game saved to database.");
        } catch (SQLException e) {
            System.out.println("Error in saveGameToDatabase: " + e.getMessage());
        }
    }


}

